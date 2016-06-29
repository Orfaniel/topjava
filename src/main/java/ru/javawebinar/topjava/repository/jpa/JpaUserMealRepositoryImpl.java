package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        User user = em.getReference(User.class, userId);
        userMeal.setUser(user);
        if (userMeal.isNew()) {
            em.persist(userMeal);
        } else {
            if (get(userMeal.getId(), userId) == null) return null;
            em.merge(userMeal);
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = em.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getSingleResult();
        return userMeal == null ? null : userMeal;
    }

    @Override
    @Transactional
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.GET_BETWEEN, UserMeal.class)
                .setParameter("userId", userId)
                .setParameter("after", startDate)
                .setParameter("before", endDate)
                .getResultList();
    }
}