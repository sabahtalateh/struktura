package ru.sabah.struktura.services.tasks;

import ru.sabah.struktura.services.ServiceError;

public class ProjectNotFoundError extends ServiceError {
    public ProjectNotFoundError() {
        super("project_not_found");
    }
}
