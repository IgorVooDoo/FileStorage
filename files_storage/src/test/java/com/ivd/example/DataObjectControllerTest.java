package com.ivd.example;

import com.ivd.example.dao.DataObjectDao;
import com.ivd.example.dao.UserDao;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("user")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DataObjectControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DataObjectDao dataObjectDao;
    @Autowired
    private UserDao userDao;

    /**
     * Добавление файла
     *
     * @throws Exception Исключение
     */
    @Test
    public void addFileTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/addData")
                .file("file", "123".getBytes())
                .param("name", "addFileTest")
                .with(csrf());
        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(3))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@id=100]").exists());
    }

    /**
     * Удаление файла
     *
     * @throws Exception Исключение
     */
    @Test
    public void deleteFileTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/del/10"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/home"));
    }
}
