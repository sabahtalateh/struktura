package ru.sabah.struktura.handlers.projects;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.sabah.struktura.handlers.Response;
import ru.sabah.struktura.services.cards.Cards;

import java.util.List;
import java.util.stream.Collectors;

@Path("/v1")
public class CardsHandler {

    @Inject
    private Cards cards;

    @GET()
    @Path("/projects/{project}/cards")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<Card>> cards(@PathParam("project") String project) {
        var res = cards.projectCards(project);

        return Response.ok(
                res.stream()
                        .map(x -> new Card()
                                .setTitle(x.getTitle())
                                .setDescription(x.getDescription())
                        )
                        .collect(Collectors.toList())
        );
    }

    @POST()
    @Path("/projects/{project}/card")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Card> createCard(
            @PathParam("project") String project,
            @NotNull @Valid Card card
    ) {
        var t = cards.createCard(
                project,
                new ru.sabah.struktura.models.Card()
                        .setTitle(card.getTitle())
                        .setDescription(card.getDescription())
        );

        return Response.ok(new Card()
                .setTitle(t.getTitle())
                .setDescription(t.getDescription())
        );
    }
}

@Getter
@Setter
@Accessors(chain = true)
class Card {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
}
