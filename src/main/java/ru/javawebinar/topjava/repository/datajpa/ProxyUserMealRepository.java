package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Жека on 06.07.2016.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Override
    UserMeal save(UserMeal userMeal);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId")
    UserMeal findOne(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Query("SELECT um FROM UserMeal um WHERE um.user.id=:userId")
    List<UserMeal> findAll(Sort sort);

    @Query("SELECT um FROM UserMeal um WHERE um.id=:id AND um.dateTime>=:after AND um.dateTime<=:before")
    UserMeal getBetween(@Param("after") LocalDateTime startDate, @Param("before") LocalDateTime endDate, int userId);
}
