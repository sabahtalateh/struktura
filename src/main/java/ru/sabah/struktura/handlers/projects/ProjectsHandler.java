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
import ru.sabah.struktura.services.tasks.Projects;

import java.util.List;
import java.util.stream.Collectors;

@Path("/v1")
public class ProjectsHandler {

    @Inject
    Projects projects;

    @POST()
    @Path("/project")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Project> createProject(@NotNull @Valid Project p) {
        var project = projects.create(new ru.sabah.struktura.models.Project()
                .setId(p.getSlug())
                .setName(p.getName())
        );

        return Response.ok(new Project()
                .setSlug(project.getId())
                .setName(project.getName())
        );
    }

    @GET()
    @Path("/projects")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<Project>> projects() {
        var list = projects.projects();
        return Response.ok(list.stream().map(x -> new Project()
                .setSlug(x.getId())
                .setName(x.getName())
        ).collect(Collectors.toList()));
    }
}

@Getter
@Setter
@Accessors(chain = true)
class Project {
    @NotEmpty
    private String slug;

    @NotEmpty
    private String name;
}
