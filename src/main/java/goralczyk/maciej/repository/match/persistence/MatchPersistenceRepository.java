package goralczyk.maciej.repository.match.persistence;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Match_;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.repository.match.api.MatchRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
    public List<Match> findAllByUser(User user)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Match> query = cb.createQuery(Match.class);
        Root<Match> root = query.from(Match.class);
        query.select(root).where(cb.equal(root.get(Match_.participantA), user));
        return em.createQuery(query).getResultList();

    }

    @Override
    public List<Match> findAllByTournament(Tournament tournament)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Match> query = cb.createQuery(Match.class);
        Root<Match> root = query.from(Match.class);
        query.select(root)
                .where(cb.equal(root.get(Match_.tournament), tournament));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Match> find(UUID id) {
        return Optional.ofNullable(em.find(Match.class, id));
    }

    @Override
    public List<Match> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Match> query = cb.createQuery(Match.class);
        Root<Match> root = query.from(Match.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Match> findByIdAndUser(UUID id, User user)
    {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Match> query = cb.createQuery(Match.class);
            Root<Match> root = query.from(Match.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Match_.participantA), user),
                            cb.equal(root.get(Match_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        }
        catch (NoResultException ex)
        {
            return Optional.empty();
        }
    }

    @Override
    public List<Match> findAllByTournamentAndUser(Tournament tournament, User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Match> query = cb.createQuery(Match.class);
        Root<Match> root = query.from(Match.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Match_.tournament), tournament),
                        cb.equal(root.get(Match_.participantA), user)
                ));
        return em.createQuery(query).getResultList();
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
