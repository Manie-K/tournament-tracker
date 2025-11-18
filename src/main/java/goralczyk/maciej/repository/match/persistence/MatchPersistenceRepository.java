package goralczyk.maciej.repository.match.persistence;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class MatchPersistenceRepository implements MatchRepository
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
    public List<Match> findAllByUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Match> findAllByTournament(Tournament tournament) {
        return em.createQuery("SELECT m FROM Match m WHERE m.tournament = :tournament", Match.class)
                .setParameter("tournament", tournament)
                .getResultList();
    }

    @Override
    public Optional<Match> find(UUID id) {
        return Optional.ofNullable(em.find(Match.class, id));
    }

    @Override
    public List<Match> findAll() {
        return em.createQuery("select m from Match m", Match.class).getResultList();
    }

    @Override
    public void create(Match entity) {
        System.out.println("[Repo]: Create match - persisting:" +  entity);
        em.persist(entity);
    }

    @Override
    public void delete(Match entity) {
        em.remove(em.find(Match.class, entity.getId()));
    }

    @Override
    public void update(Match entity) {
        em.merge(entity);
    }
}
