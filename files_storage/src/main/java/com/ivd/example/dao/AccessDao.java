package com.ivd.example.dao;

import com.ivd.example.entity.Access;
import com.ivd.example.entity.AccessKey;
import com.ivd.example.entity.TypeAccess;
import com.ivd.example.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий обеспечивающий связь с таблицей базы данных
 */
@Repository
public interface AccessDao extends CrudRepository<Access, AccessKey> {

    /**
     * Запрос для удаления доступа
     *
     * @param user   Пользователь
     * @param member Участник
     */
    @Modifying
    @Query("delete from Access a where a.user=:user and a.member=:member")
    void deleteByUserAndMember(@Param("user") User user, @Param("member") User member);

    /**
     * Список пользователей которые предоставили участнику доступ
     *
     * @param user Участник
     * @param type Тип доступа
     * @return Список
     */
    @Query("select a.user from Access a where a.member=:user and a.typeAccess=:type and a.isQuery=false")
    Iterable<User> findByUserAndType(@Param("user") User user, @Param("type") TypeAccess type);

    /**
     * Список участников которым предоставлен доступ
     *
     * @param user Пользователь
     * @param type Тип доступа
     * @param b    Запрос или нет
     * @return Список
     */
    @Query("select a.member from Access a where a.user=:user and a.typeAccess=:type and a.isQuery=:b")
    Iterable<User> findByMemberAndTypeAndQuery(@Param("user") User user, @Param("type") TypeAccess type, @Param("b") boolean b);

    /**
     * Обновление типа доступа
     *
     * @param user   Пользователь
     * @param member Участник
     */
    @Modifying
    @Query("update Access a set a.isQuery=false where a.user=:user and a.member=:member")
    void updateQueryById(User user, User member);
}
