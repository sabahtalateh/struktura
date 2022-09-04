package ru.sabah.struktura.graphql.accounts;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.eclipse.microprofile.graphql.DateFormat;
import org.eclipse.microprofile.graphql.Type;

import java.util.Date;

import static ru.sabah.struktura.graphql.Consts.ISO_DT;

@Getter
@Setter
@Accessors(chain = true)
@Type(value = "account")
class AccountG {
    private String email;

    @DateFormat(value = ISO_DT)
    private Date registeredAt;
}
