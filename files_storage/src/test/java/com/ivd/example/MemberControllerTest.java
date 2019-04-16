package com.ivd.example;

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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("user")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MemberControllerTest {
    @Autowired
    private AccessService accessService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;


    /**
     * Добавление доступа для чтения
     */
    @Test
    public void addMemberReadTest() throws Exception {
        User user = userDao.findByUsername("user");
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, false);
        Assert.assertFalse(getContainsMember(list, "memberTestRead"));

        MockHttpServletRequestBuilder multipart = multipart("/member/addRead")
                .param("mem", "12")
                .with(csrf());

        mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/accessMember"));

        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, false);
        Assert.assertTrue(getContainsMember(list, "memberTestRead"));
    }

    /**
     * Добавление доступа из запроса
     */
    @Test
    public void addMemberFromQuery() throws Exception {
        User user = userDao.findByUsername("user");
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, true);
        Assert.assertTrue(getContainsMember(list, "memberQueryRead"));

        mockMvc.perform(get("/member/add/8"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/accessMember"));

        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, false);
        Assert.assertTrue(getContainsMember(list, "memberQueryRead"));
    }

    /**
     * Добавление запроса на доступ для чтения
     */
    @Test
    public void queryRead() throws Exception {
        User user = userDao.findByUsername("memberTestRead");
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, true);
        Assert.assertFalse(getContainsMember(list, "user"));

        MockHttpServletRequestBuilder multipart = multipart("/member/queryRead")
                .param("mem", "12")
                .with(csrf());

        mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/accessMember"));

        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, true);
        Assert.assertTrue(getContainsMember(list, "user"));
    }

    /**
     * Добавление доступа для скачивания
     */
    @Test
    public void addMemberLoad() throws Exception{
        User user = userDao.findByUsername("user");
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.LOAD, false);
        Assert.assertFalse(getContainsMember(list, "memberTestLoad"));

        MockHttpServletRequestBuilder multipart = multipart("/member/addLoad")
                .param("mem", "13")
                .with(csrf());

        mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/accessMember"));

        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.LOAD, false);
        Assert.assertTrue(getContainsMember(list, "memberTestLoad"));
    }

    /**
     * Добавление запроса на доступ для скачивания
     */
    @Test
    public void queryLoad() throws Exception{
        User user = userDao.findByUsername("memberTestLoad");
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.LOAD, true);
        Assert.assertFalse(getContainsMember(list, "user"));

        MockHttpServletRequestBuilder multipart = multipart("/member/queryLoad")
                .param("mem", "13")
                .with(csrf());

        mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/accessMember"));

        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.LOAD, true);
        Assert.assertTrue(getContainsMember(list, "user"));
    }

    /**
     * Удаление доступа
     */
    @Test
    public void delMemberRead() throws Exception{
        User user = userDao.findByUsername("user");
        Iterable<User> list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ,false);
        Assert.assertTrue(getContainsMember(list, "memberRead"));

        mockMvc.perform(get("/member/del/6"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/accessMember"));

        list = accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ,false);
        Assert.assertFalse(getContainsMember(list, "memberRead"));
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