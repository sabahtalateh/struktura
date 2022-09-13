package ru.sabah.struktura.services.security;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import ru.sabah.struktura.models.Token;
import ru.sabah.struktura.models.User;
import ru.sabah.struktura.services.crypt.Passwords;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class Security {

    @PersistenceContext(unitName = "master")
    private EntityManager em;

    @Inject
    private Passwords passwords;

    private final Duration accessLifetime;
    private final Duration refreshLifetime;

    @Inject
    public Security(
            @ConfigProperty(name = "security.accessLifetimeSeconds") int accessLifetime,
            @ConfigProperty(name = "security.refreshLifetimeSeconds") int refreshLifetime
    ) {
        this.accessLifetime = Duration.ofSeconds(accessLifetime);
        this.refreshLifetime = Duration.ofSeconds(refreshLifetime);
    }

    @Transactional
    public User register(User user) throws UsernameInUseError {
        if (usernameOccupied(user.getUsername())) {
            throw new UsernameInUseError();
        }

        if (!passwordIsStrong(user.getPassword())) {
            throw new WeakPasswordError();
        }

        user.setPassword(passwords.hash(user.getPassword()));

        em.persist(user);

        return user;
    }

    @Transactional
    public Token login(String username, String password) throws CanNotLoginError {
        List<?> res = em.createQuery("select u from User u where u.username = :username")
                .setParameter("username", username)
                .getResultList();
        if (res.size() == 0) {
            throw new CanNotLoginError();
        }

        var user = (User) res.get(0);

        if (!passwords.verify(user.getPassword(), password)) {
            throw new CanNotLoginError();
        }


        em.createQuery("delete from Token t where t.user = :user")
                .setParameter("user", user)
                .executeUpdate();

        var t = new Token()
                .setUser(user)
                .setAccess(UUID.randomUUID().toString())
                .setRefresh(UUID.randomUUID().toString())
                .setExpiresAt(LocalDateTime.now().plus(accessLifetime))
                .setRefreshExpiresAt(LocalDateTime.now().plus(refreshLifetime));
        em.persist(t);
        em.flush();

        return t;
    }

    @Transactional
    public Token refreshUserToken(String username, String refresh, Date now) throws CanNotLoginError {
        List<?> res = em.createQuery("select u from User u where u.username = :username")
                .setParameter("username", username)
                .getResultList();
        if (res.size() == 0) {
            throw new CanNotRefreshError();
        }
        var user = (User) res.get(0);

        List<?> tokens = em.createQuery("""
                        select t from Token t
                        where t.user = :user
                            and t.refresh = :refresh
                            and t.refreshExpiresAt > :now""")
                .setParameter("user", user)
                .setParameter("refresh", refresh)
                .setParameter("now", now)
                .getResultList();

        if (tokens.size() == 0) {
            throw new CanNotRefreshError();
        }

        em.createQuery("delete from Token where id = :id")
                .setParameter("id", ((Token) tokens.get(0)).getId())
                .executeUpdate();

        var t = new Token()
                .setUser(user)
                .setAccess(UUID.randomUUID().toString())
                .setRefresh(UUID.randomUUID().toString())
                .setExpiresAt(LocalDateTime.now().plus(accessLifetime))
                .setRefreshExpiresAt(LocalDateTime.now().plus(refreshLifetime));
        em.persist(t);
        em.flush();

        return t;
    }

    public User findUserByAccess(String access, LocalDateTime now) {
        List<?> res = em.createQuery("""
                        select t.user from Token t
                        where t.access = :access
                              and t.expiresAt >= :now""")
                .setParameter("access", access)
                .setParameter("now", now)
                .getResultList();

        if (res.size() == 0) {
            return null;
        }

        return (User) res.get(0);
    }

    private boolean usernameOccupied(String username) {
        var query = em.createQuery("select u from User u where u.username = :username");
        query.setParameter("username", username);
        var accounts = query.getResultList();

        return accounts.size() > 0;
    }

    private boolean passwordIsStrong(String password) {
        return password.length() > 5;
    }
}
