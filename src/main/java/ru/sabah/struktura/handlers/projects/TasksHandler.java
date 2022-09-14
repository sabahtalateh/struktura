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
import ru.sabah.struktura.services.tasks.Tasks;

import java.util.List;
import java.util.stream.Collectors;

@Path("/v1")
public class TasksHandler {

    @Inject
    private Tasks tasks;

    @GET()
    @Path("/projects/{project}/tasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<Task>> tasks(@PathParam("project") String project) {
        var res = tasks.projectTasks(project);

        return Response.ok(
                res.stream()
                        .map(x -> new Task()
                                .setTitle(x.getTitle())
                                .setDescription(x.getDescription())
                        )
                        .collect(Collectors.toList())
        );
    }

    @POST()
    @Path("/projects/{project}/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Task> createTask(
            @PathParam("project") String project,
            @NotNull @Valid Task task
    ) {
        var t = tasks.createTask(
                project,
                new ru.sabah.struktura.models.Task()
                        .setTitle(task.getTitle())
                        .setDescription(task.getDescription())
        );

        return Response.ok(new Task()
                .setTitle(t.getTitle())
                .setDescription(t.getDescription())
        );
    }
}

@Getter
@Setter
@Accessors(chain = true)
class Task {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
}
