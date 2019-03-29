package com.ivd.example.service;

import com.ivd.example.entity.TypeAccess;
import com.ivd.example.entity.User;

/**
 * Сервис для предоставления доступа пользователям
 */
public interface AccessService {
    /**
     * Сохранение доступа
     *
     * @param user       Пользователь
     * @param member     Участник
     * @param typeAccess Тип доступа
     * @param isQuery    Запрос??
     */
    void save(User user, User member, TypeAccess typeAccess, boolean isQuery);

    /**
     * Удаление доступа
     *
     * @param user   Пользователь
     * @param member Участник
     */
    void deleteById(User user, User member);

    /**
     * Список пользователей предоставивших доступы по типам
     *
     * @param user Пользователь
     * @param read Тип доступа
     * @return Список
     */
    Iterable<User> findByUserAndType(User user, TypeAccess read);

    /**
     * Список пользователей у которых есть доступы по типам
     *
     * @param user Пользователь
     * @param read Тип доступа
     * @param b    Запрос или нет
     * @return Список
     */
    Iterable<User> findByMemberAndTypeAndQuery(User user, TypeAccess read, boolean b);

    /**
     * Обновление доступа
     *
     * @param user   Пользователь
     * @param member Участник
     */
    void updateQueryById(User user, User member);
}
