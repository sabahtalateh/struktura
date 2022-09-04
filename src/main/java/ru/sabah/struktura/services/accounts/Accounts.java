package ru.sabah.struktura.services.accounts;

import ru.sabah.struktura.entities.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class Accounts {

    public static void main(String[] args) {
        float x = 1045;
        System.out.println(x / 1000);
    }

    @PersistenceContext(unitName = "master")
    private EntityManager entityManager;

    @Transactional
    public Account register(Account account) throws EmailOccupiedException {
        if (emailOccupied(account.getEmail())) {
            throw new EmailOccupiedException();
        }

        if (!passwordIsStrong(account.getPassword())) {
            throw new WeakPasswordException();
        }

        entityManager.persist(account);

        return account;
    }

    private boolean emailOccupied(String email) {
        var query = entityManager.createQuery("select a from Account a where a.email = :email");
        query.setParameter("email", email);
        var accounts = query.getResultList();

        return accounts.size() > 0;
    }

    private boolean passwordIsStrong(String password) {
        return password.length() > 5;
    }
}
