package ru.sabah.struktura.graphql;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class Something {
    @Query
    public String zoz() {
        return "zoz";
    }
}
