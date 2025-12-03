package goralczyk.maciej.repository.user.persistence;

import goralczyk.maciej.entity.Match;
import goralczyk.maciej.entity.Tournament;
import goralczyk.maciej.entity.User;
import goralczyk.maciej.entity.User_;
import goralczyk.maciej.repository.user.api.UserRepository;
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
public class UserPersistenceRepository implements UserRepository
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
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> findByName(String name) {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(cb.equal(root.get(User_.name), name));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(cb.equal(root.get(User_.login), login));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        /*List<Match> matchesToDelete = matchService.findAllByUser(entity.getId());

        for (Match match : matchesToDelete) {
            em.remove(match);
        }*/

        em.remove(em.find(User.class, entity.getId()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }
}
