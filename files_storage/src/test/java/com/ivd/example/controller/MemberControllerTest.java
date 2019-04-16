package com.ivd.example.controller;

import com.ivd.example.dao.UserDao;
import com.ivd.example.entity.TypeAccess;
import com.ivd.example.entity.User;
import com.ivd.example.service.AccessService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MemberControllerTest {
    @Autowired
    private AccessService accessService;

    @Autowired
    private UserDao userDao;

    /**
     * Добавление доступа для чтения
     */
    @Test
    public void addMemberRead() {
        //Пользователь предоставляющий доступ
        User user = userDao.findByUsername("user");
        //Пользователь, которому предоставляется доступ
        User member = userDao.findByUsername("memberTestRead");
        //Сохранение доступа для чтения
        accessService.save(member, user, TypeAccess.READ, false);
        //Список пользователей, которым доступ предоставлен
        Iterable<User> list = accessService.findByUserAndType(user, TypeAccess.READ);
        Assert.assertTrue(getContainsMember(list, "memberTestRead"));
    }

    /**
     * Добавление доступа из запроса
     */
    @Test
    public void addMemberFromQuery() {
        User user = userDao.findByUsername("user");
        User member = userDao.findByUsername("memberQueryRead");
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, true);
        Assert.assertTrue(getContainsMember(list, "memberQueryRead"));
        accessService.updateQueryById(user, member);
        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, true);
        Assert.assertFalse(getContainsMember(list, "memberQueryRead"));
        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, false);
        Assert.assertTrue(getContainsMember(list, "memberQueryRead"));
    }

    /**
     * Добавление запроса на доступ для чтения
     */
    @Test
    public void queryRead() {
        User user = userDao.findByUsername("user");
        User member = userDao.findByUsername("memberTestRead");
        accessService.save(user, member, TypeAccess.READ, true);
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, true);
        Assert.assertTrue(getContainsMember(list, "memberTestRead"));
    }
    /**
     * Добавление доступа для скачивания
     */
    @Test
    public void addMemberLoad() {
        User user = userDao.findByUsername("user");
        User member = userDao.findByUsername("memberTestLoad");
        accessService.save(member, user, TypeAccess.LOAD, false);
        Iterable<User> list = accessService.findByUserAndType(user, TypeAccess.LOAD);
        Assert.assertTrue(getContainsMember(list, "memberTestLoad"));
    }
    /**
     * Добавление запроса на доступ для скачивания
     */
    @Test
    public void queryLoad() {
        User user = userDao.findByUsername("user");
        User member = userDao.findByUsername("memberTestLoad");
        accessService.save(user, member, TypeAccess.LOAD, true);
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.LOAD, true);
        Assert.assertTrue(getContainsMember(list, "memberTestLoad"));
    }

    /**
     * Удаление доступа
     */
    @Test
    public void delMemberRead() {
        User user = userDao.findByUsername("user");
        User member = userDao.findByUsername("userRead");
        Iterable<User> list = accessService.findByUserAndType(user, TypeAccess.READ);
        Assert.assertTrue(getContainsMember(list, "userRead"));
        accessService.deleteById(member, user);
        list = accessService.findByUserAndType(user, TypeAccess.READ);
        Assert.assertFalse(getContainsMember(list, "userRead"));
    }


    private boolean getContainsMember(Iterable<User> list, String member) {
        for (User item :
                list) {
            if (item.getUsername().equals(member)) {
                return true;
            }
        }
        return false;
    }
}