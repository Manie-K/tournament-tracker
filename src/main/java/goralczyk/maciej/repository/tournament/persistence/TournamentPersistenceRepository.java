package goralczyk.maciej.repository.tournament.persistence;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.Tournament_;
import goralczyk.maciej.repository.tournament.api.TournamentRepository;
import goralczyk.maciej.service.match.MatchService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class TournamentPersistenceRepository implements TournamentRepository
{
    @Inject
    MatchService matchService;

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
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tournament> query = cb.createQuery(Tournament.class);
            Root<Tournament> root = query.from(Tournament.class);
            query.select(root)
                    .where(cb.equal(root.get(Tournament_.name), name));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tournament> find(UUID id) {
        return Optional.ofNullable(em.find(Tournament.class, id));
    }

    @Override
    public List<Tournament> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tournament> query = cb.createQuery(Tournament.class);
        Root<Tournament> root = query.from(Tournament.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Tournament entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Tournament entity) {
        List<Match> matchesToDelete = matchService.findAllByTournament(entity.getId()).orElse(List.of());

        for (Match match : matchesToDelete) {
            em.remove(match);
        }

        em.remove(em.find(Tournament.class, entity.getId()));
    }

    @Override
    public void update(Tournament entity) {
        em.merge(entity);
    }
}
