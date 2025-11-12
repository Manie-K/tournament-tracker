package goralczyk.maciej.repository.tournament.persistence;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class TournamentPersistenceRepository implements TournamentRepository
{
    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Tournament> findByName(String name) {
        List<Tournament> results = em.createQuery("SELECT t FROM Tournament t WHERE t.name = :name", Tournament.class)
                .setParameter("name", name)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<Tournament> find(UUID id) {
        return Optional.ofNullable(em.find(Tournament.class, id));
    }

    @Override
    public List<Tournament> findAll() {
        return em.createQuery("select t from Tournament t", Tournament.class).getResultList();
    }

    @Override
    public void create(Tournament entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Tournament entity) {
        em.remove(em.find(Tournament.class, entity.getId()));
    }

    @Override
    public void update(Tournament entity) {
        em.merge(entity);
    }
}
