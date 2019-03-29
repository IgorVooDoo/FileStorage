package com.ivd.example.controller;

import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.TypeAccess;
import com.ivd.example.entity.User;
import com.ivd.example.service.AccessService;
import com.ivd.example.service.DataObjectService;
import com.ivd.example.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Контроллер для навигации
 */
@Controller
public class MainController {

    private final UserService userService;
    private final DataObjectService dataObjectService;
    private final AccessService accessService;

    public MainController(UserService userService, DataObjectService dataObjectService, AccessService accessService) {
        this.userService = userService;
        this.dataObjectService = dataObjectService;
        this.accessService = accessService;
    }

    /**
     * Переход на домашнюю страницу
     *
     * @return "redirect:/home"
     */
    @GetMapping("/")
    public String main() {
        return "redirect:/home";
    }

    /**
     * Открытие домашней страницы пользователя
     *
     * @param model Map<String, Object>
     * @return String
     */
    @GetMapping("/home")
    public String home(
            @AuthenticationPrincipal User user,
            Map<String, Object> model) {
        Iterable<DataObject> messages = dataObjectService.findByAuthor(user);
        model.put("messages", messages);
        return "home";
    }

    /**
     * Переход на страницу с доступными для чтения данными
     *
     * @param user  Пользователь
     * @param model Map<String, Object>
     * @return "accessReadOwner"
     */
    @GetMapping("/accessReadOwner")
    public String showAccessReadOwner(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {
        Iterable<User> readOwner = accessService.findByUserAndType(user, TypeAccess.READ);
        model.put("readOwner", readOwner);
        return "accessReadOwner";
    }

    /**
     * Переход на страницу с доступными для скачивания данными
     */
    @GetMapping("/accessLoadOwner")
    public String showAccessLoadOwner(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {
        Iterable<User> loadOwner = accessService.findByUserAndType(user, TypeAccess.LOAD);
        model.put("loadOwner", loadOwner);
        return "accessLoadOwner";
    }

    /**
     * Переход на страницу со списками участников
     *
     * @param user  Пользователь
     * @param model Сообщения
     * @return return "accessMember";
     */
    @GetMapping("/accessMember")
    public String showMembers(
            @AuthenticationPrincipal User user,
            Map<String, Object> model
    ) {
        model.put("users", userService.findAllNotMember(user));
        model.put("usersQuery", userService.findAllNotMemberForQuery(user));
        model.put("readMember", accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, false));
        model.put("loadMember", accessService.findByMemberAndTypeAndQuery(user, TypeAccess.LOAD, false));
        model.put("readQueryMember", accessService.findByMemberAndTypeAndQuery(user, TypeAccess.READ, true));
        model.put("loadQueryMember", accessService.findByMemberAndTypeAndQuery(user, TypeAccess.LOAD, true));
        return "accessMember";
    }

    /**
     * Переход на страницу Аналитика
     *
     * @param model Сообщения
     * @return "analystForm";
     */
    @GetMapping("/analyst")
    public String analyst(
            Map<String, Object> model
    ) {
        model.put("users", userService.findAll());
        model.put("usersCount", userService.userCount());
        model.put("messagesCount", dataObjectService.messageCount());
        return "analystForm";
    }
}
