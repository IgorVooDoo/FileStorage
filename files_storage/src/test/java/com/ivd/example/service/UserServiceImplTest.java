package com.ivd.example.service;

import com.ivd.example.dao.UserDao;
import com.ivd.example.entity.Role;
import com.ivd.example.entity.User;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserDao userDao;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private CodeActivationSender codeActivationSender;

    @Test
    public void addUser() {
        User user = new User();
        userService.addUser(user);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setUsername("Smith");

        userService.addUser(user);

        Mockito.verify(userDao, Mockito.times(1)).save(user);

        userService.deleteUser(user);

        Assert.assertNull(userDao.findByUsername("Smith"));
    }

    @Test
    public void activateUser() {
        User user = new User();
        user.setActivationCode("activate");
        Mockito.doReturn(user)
                .when(userDao)
                .findByActivationCode("activate");

        userService.activateUser("activate");
        Assert.assertNull(user.getActivationCode());

        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    public void loadUserByUsername() {
        User user = new User();
        user.setUsername("Smith");
        user.setActivationCode("activate");
        userService.addUser(user);
        Mockito.doReturn(user)
                .when(userDao)
                .findByActivationCode("activate");

        userService.activateUser("activate");

        Mockito.doReturn(user)
                .when(userDao)
                .findByUsername("Smith");

        Assert.assertNotNull(userService.loadUserByUsername("Smith"));
    }
}