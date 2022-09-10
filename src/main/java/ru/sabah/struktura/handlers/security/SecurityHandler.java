package ru.sabah.struktura.handlers.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.sabah.struktura.handlers.IsoOffset;
import ru.sabah.struktura.handlers.Response;
import ru.sabah.struktura.models.User;
import ru.sabah.struktura.services.security.Security;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Date;

@Path("/v1")
public class SecurityHandler {

    @Inject
    private Security security;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Registered> register(@NotNull @Valid Register user) {
        var u = security.register(
                new User()
                        .setUsername(user.getUsername())
                        .setPassword(user.getPassword())
                        .setRegisteredAt(new Date())
        );
        return Response.ok(new Registered()
                .setUsername(u.getUsername())
                .setRegisteredAt(u.getRegisteredAt())
        );
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Token> login(@NotNull @Valid Login login) {
        var t = security.login(login.getUsername(), login.getPassword());

        return Response.ok(
                new Token()
                        .setAccess(t.getAccess())
                        .setRefresh(t.getRefresh())
                        .setExpires(t.calcExpires().toSeconds())
        );
    }

    @POST
    @Path("/refresh")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Token> refresh(
            @Context SecurityContext sec,
            @NotNull @Valid Refresh refresh
    ) {
        var t = security.refreshUserToken(
                sec.getUserPrincipal().getName(),
                refresh.getRefresh(),
                new Date()
        );

        return Response.ok(
                new Token()
                        .setAccess(t.getAccess())
                        .setRefresh(t.getRefresh())
                        .setExpires(t.calcExpires().toSeconds())
        );
    }

    @GET
    @Path("/secure")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<String> secure(@Context SecurityContext sec) {
        var p = sec.getUserPrincipal();
        System.out.println(p.getName());
        sec.isSecure();
        return Response.<String>ok().data("kuku");
    }
}

@Getter
@Setter
@Accessors(chain = true)
class Register {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}

@Getter
@Setter
@Accessors(chain = true)
class Registered {
    @NotEmpty
    private String username;
    @NotEmpty
    @JsonSerialize(using = IsoOffset.class)
    private Date registeredAt;
}

@Getter
@Setter
@Accessors(chain = true)
class Login {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}

@Getter
@Setter
@Accessors(chain = true)
class Token {
    @NotEmpty
    private String access;
    @NotEmpty
    private String refresh;
    @NotEmpty
    private long expires;
}

@Getter
@Setter
@Accessors(chain = true)
class Refresh {
    @NotEmpty
    private String refresh;
}
