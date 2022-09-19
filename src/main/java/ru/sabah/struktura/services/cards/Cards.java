package ru.sabah.struktura.services.cards;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ru.sabah.struktura.models.Project;
import ru.sabah.struktura.models.Card;

import java.util.List;

@ApplicationScoped
public class Cards {

    @PersistenceContext(unitName = "master")
    private EntityManager em;

    public List<Card> projectCards(String project) {
        List<?> l = em.createQuery("select t from Card t where t.project.id = :project")
                .setParameter("project", project)
                .getResultList();

        return (List<Card>) l;
    }

    @Transactional
    public Card createCard(String project, Card card) {
        if (em.find(Project.class, project) == null) {
            throw new ProjectNotFoundError();
        }
        card.setProject(new Project().setId(project));
        em.persist(card);
        em.flush();

        return card;
    }
}
