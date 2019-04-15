package com.ivd.example;

import com.ivd.example.controller.MainController;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("user")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainController controller;

    /**
     * Переход на домашнюю страницу
     *
     * @throws Exception Исключение
     */
    @Test
    public void homePageTest() throws Exception {
        this.mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='navbarSupportedContent']/div").string("user"))
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(0));
    }

    /**
     * Страница с пользователями предоставившими доступ на чтение
     *
     * @throws Exception Исключение
     */
    @Test
    public void showAccessReadOwnerTest() throws Exception {
        this.mockMvc.perform(get("/accessReadOwner"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='idOwner3']/h2/button").string("userRead ( 0 )"));
    }

    /**
     * Страница с пользователями предоставившими доступ на скачивание
     *
     * @throws Exception Исключение
     */
    @Test
    public void showAccessLoadOwnerTest() throws Exception {
        this.mockMvc.perform(get("/accessLoadOwner"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='idOwner4']/h2/button").string("userLoad ( 0 )"));
    }

    /**
     * Страница с пользователями кому предоставлен доступ или находится в ожидании
     *
     * @throws Exception Исключение
     */
    @Test
    public void showAccessMemberTest() throws Exception {
        this.mockMvc.perform(get("/accessMember"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(
                        xpath("//*[@id='readAccess']/div/form/table/tbody/tr/th[1]/label").string("memberRead")
                )
                .andExpect(
                        xpath("//*[@id='loadAccess']/div/form/table/tbody/tr/th[1]/label").string("memberLoad")
                );
    }

    /**
     * Страница пользователя с ролью Аналитик
     *
     * @throws Exception Исключение
     */
    @Test
    @WithUserDetails("analyst")
    public void showFormAnalyst() throws Exception {
        this.mockMvc.perform(get("/analyst"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"inputCount\"]").exists())
                .andExpect(xpath("//*[@id=\"idOwner1\"]/h2/button").string("user ( 0 )"));
    }

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
                .andExpect(xpath("/html/body/div/table/tbody/tr").nodeCount(1))
                .andExpect(xpath("/html/body/div/table/tbody/tr[@id=100]").exists());
    }

}
