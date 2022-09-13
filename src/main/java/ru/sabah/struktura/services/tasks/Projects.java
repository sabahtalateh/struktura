package ru.sabah.struktura.services.tasks;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.sabah.struktura.models.Project;

import java.util.List;

@ApplicationScoped
public class Projects {
    @PersistenceContext(unitName = "master")
    private EntityManager em;

    @Transactional
    public Project create(Project p) {
        p.setArchived(false);

        em.persist(p);
        em.flush();

        return p;
    }

    public List<Project> projects() {
        List<?> l = em.createQuery("select p from Project p where p.archived = false").getResultList();
        return (List<Project>) l;
    }
}
