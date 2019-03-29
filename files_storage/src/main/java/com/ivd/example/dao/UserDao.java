package com.ivd.example.dao;

import com.ivd.example.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий обеспечивающий связь с таблицей базы данных
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {
    /**
     * Поиск пользователя по имени
     *
     * @param username Имя пользователя
     * @return Пользователь
     */
    User findByUsername(String username);

    /**
     * Поиск пользователя по коду активации
     *
     * @param code Код активации
     * @return Пользователь
     */
    User findByActivationCode(String code);

    /**
     * Список пользователей не являющихся участниками
     *
     * @return Iterable<User>
     */
    @Query("select u from User u "+
            "inner join u.roles r "+
            "where u<>:user and " +
            "u not in (select a.member from Access a where a.user=:user) and 'USER' in elements(u.roles)")
    Iterable<User> findAllNotMember(@Param("user")User user);

    /**
     * Список пользователей не являющихся участниками для запроса
     *
     * @return Iterable<User>
     */
    @Query("select u from User u "+
            "inner join u.roles r "+
            "where u<>:user and " +
            "u not in (select a.user from Access a where a.member=:user) and 'USER' in elements(u.roles)")
    Iterable<User> findAllNotMemberForQuery(@Param("user")User user);


    User findByEmail(String email);
}
