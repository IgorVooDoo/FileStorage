package com.ivd.example.service;

import com.ivd.example.dao.UserDao;
import com.ivd.example.entity.Role;
import com.ivd.example.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final CodeActivationSender codeActivationSender;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserDao userDao, CodeActivationSender codeActivationSender) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.codeActivationSender = codeActivationSender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUser(User user) throws RuntimeException {

        user.setDateRegistration(new Date());
        user.setActive(true);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        sendActivateCodeToEmail(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Long userCount() {
        return userDao.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveUser(User user) {
        if (user == null) {
            throw new RuntimeException("Пустое значение пользователя");
        }
        userDao.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveUser(User user, Set<String> keySet) {
        if (user == null || keySet.isEmpty()) {
            throw new RuntimeException("Пустое значение пользователя или ролей");
        }
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : keySet) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userDao.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(User user) {
        if (user != null) {
            userDao.deleteById(user.getId());
        } else {
            throw new RuntimeException("Пустое значение пользователя");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateUser(String code) throws RuntimeException {
        User user = userDao.findByActivationCode(code);

        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }

        Date now = new Date();
        Date activeDate = user.getDateRegistration();

        int days = Days.daysBetween(new DateTime(activeDate), new DateTime(now)).getDays();
        if (days > 1) {
            throw new RuntimeException("Истек срок активации");
        }
        user.setActivationCode(null);
        userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        } else if (user.getActivationCode() != null) {
            throw new UsernameNotFoundException("Пользователь не активирован");
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<User> findAllNotMember(User user) {
        return userDao.findAllNotMember(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<User> findAllNotMemberForQuery(User user) {
        return userDao.findAllNotMemberForQuery(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkEmail(String email) {
        User user = userDao.findByEmail(email);
        return user != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkUsername(String username) {
        User entity = userDao.findByUsername(username);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendCode(String email) {
        User user = userDao.findByEmail(email);
        user.setDateRegistration(new Date());
        user.setActivationCode(UUID.randomUUID().toString());
        userDao.save(user);
        sendActivateCodeToEmail(user);
    }

    private void sendActivateCodeToEmail(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Добро пожаловать, %s! \n" +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            codeActivationSender.sendMail(
                    user.getEmail(),
                    "Код активации",
                    message
            );
        }
    }
}
