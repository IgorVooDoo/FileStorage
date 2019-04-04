package com.ivd.example.service;

import com.ivd.example.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

/**
 * Сервис для авторизации пользователя
 */
public interface UserService extends UserDetailsService {

    /**
     * Регистрация пользователя
     *
     * @param user Пользователь
     */
    void addUser(User user) throws RuntimeException;

    /**
     * Сохранение данных пользователя Администратором
     *
     * @param user   Пользователь
     * @param keySet Список ролей
     */
    void saveUser(User user, Set<String> keySet);

    /**
     * Сохранение данных пользователя
     *
     * @param user Пользователь
     */
    void saveUser(User user);

    /**
     * Удаление пользователя Администратором
     *
     * @param user Пользователь
     */
    void deleteUser(User user);

    /**
     * Возвращает список пользователей
     *
     * @return Список пользователей
     */
    Iterable<User> findAll();

    /**
     * Возвращает кол-во пользователей в системе
     *
     * @return Long
     */
    Long userCount();

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Активация пользователя
     *
     * @param code Код активации
     * @return boolean
     */
    void activateUser(String code) throws RuntimeException;

    /**
     * Список пользователей
     *
     * @return Iterable<User>
     */
    Iterable<User> findAllNotMember(User user);

    /**
     * Список пользователей, которым можно отправить запрос на подписку
     *
     * @param user Пользователь
     * @return Список пользователей
     */
    Iterable<User> findAllNotMemberForQuery(User user);

    /**
     * Проверка наличия пользователя с таким адресом
     *
     * @param email адрес
     * @return есть/нет
     */
    boolean checkEmail(String email);

    /**
     * Проверка наличия пользователя с таким username
     *
     * @param username адрес
     * @return есть/нет
     */
    boolean checkUsername(String username);

    /**
     * Для повторной отправки кода активации
     *
     * @param email адрес
     */
    void sendCode(String email);
}
