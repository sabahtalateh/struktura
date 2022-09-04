package ru.sabah.struktura.graphql.accounts;

import lombok.extern.java.Log;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import ru.sabah.struktura.entities.Account;
import ru.sabah.struktura.services.ServiceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.logging.Level;

@ApplicationScoped
@GraphQLApi
@Log
public class Accounts {

    @Inject
    ru.sabah.struktura.services.accounts.Accounts accounts;

    @Query
    public String hello() {
        return "Hello";
    }

    @Mutation
    public AccountG register(@Name("email") String email, @Name("password") String password) {
        try {
            var acc = accounts.register(new Account().setEmail(email).setPassword(password).setRegisteredAt(new Date()));
            return new AccountG().setEmail(acc.getEmail()).setRegisteredAt(acc.getRegisteredAt());
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.WARNING, e, e::getMessage);
            throw e;
        }
    }
}
