package ru.sabah.struktura.graphql;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.hibernate.type.StringRepresentableType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@GraphQLApi
public class Auth {
    @Query
    public String register(
            @Name("email") String email,
            @Name("password") String password
    ) {
        System.out.println(email);
        System.out.println(password);

        return "Zhopa";
    }
}
