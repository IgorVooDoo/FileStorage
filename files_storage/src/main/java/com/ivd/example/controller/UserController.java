package com.ivd.example.controller;

import com.ivd.example.entity.Role;
import com.ivd.example.entity.User;
import com.ivd.example.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Контроллер для обработки запросов администрирования пользователями
 */
@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод, возвращающий список пользователей
     *
     * @param model Map<String, Object>
     * @return "userList"
     */
    @GetMapping
    public String userList(Map<String, Object> model) {
        model.put("users", userService.findAll());
        return "userList";
    }

    /**
     * Открытие формы Пользователя для редактирования
     *
     * @param user  Пользователь
     * @param model Map<String, Object>
     * @return "userEdit"
     */
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user,
                               Map<String, Object> model) {
        model.put("user", user);
        model.put("roles", Role.values());
        model.put("messages", user.getDataObjects());
        return "userEdit";
    }

    /**
     * Сохранение изменений
     *
     * @param username Имя пользователя
     * @param form     Список ролей
     * @param user     Пользователь
     * @return "redirect:/user"
     */
    @PostMapping("/save")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam(name = "userId") User user) {
        user.setUsername(username);
        userService.saveUser(user, form.keySet());
        return "redirect:/user";
    }

    /**
     * Удаление пользователя
     *
     * @param user Пользователь
     * @return "redirect:/user"
     */
    @PostMapping("/del")
    public String userDel(
            @RequestParam(name = "userId") User user
    ) {
        userService.delUser(user);
        return "redirect:/user";
    }
}
