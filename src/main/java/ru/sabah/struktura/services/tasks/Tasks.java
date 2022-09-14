package ru.sabah.struktura.services.tasks;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import ru.sabah.struktura.models.Project;
import ru.sabah.struktura.models.Task;

import java.util.List;

@ApplicationScoped
public class Tasks {

    @PersistenceContext(unitName = "master")
    private EntityManager em;

    public List<Task> projectTasks(String project) {
        List<?> l = em.createQuery("select t from Task t where t.project.id = :project")
                .setParameter("project", project)
                .getResultList();

        return (List<Task>) l;
    }

    @Transactional
    public Task createTask(String project, Task task) {
        if (em.find(Project.class, project) == null) {
            throw new ProjectNotFoundError();
        }
        task.setProject(new Project().setId(project));
        em.persist(task);
        em.flush();

        return task;
    }
}
