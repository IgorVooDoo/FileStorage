package com.ivd.example.controller;

import com.ivd.example.entity.User;
import com.ivd.example.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

/**
 * Контроллер для обработки запросов регистрации
 */
@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Открытие страницы регистрации
     *
     * @return registration
     */
    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        model.put("user", new User());
        return "registration";
    }

    /**
     * Сохранение нового пользователя
     *
     * @param user  Пользователь
     * @param model Map<String, Object>
     * @return "redirect:/login"
     */
    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        boolean isUsernameDubl = userService.checkUsername(user.getUsername());
        boolean isEmailDubl = userService.checkEmail(user.getEmail());
        boolean isNotEqualPass = (user.getPassword() != null && !user.getPassword().equals(passwordConfirm));

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Поле не должно быть пустым");
        }

        if (isNotEqualPass) {
            model.addAttribute("passwordError", "Пароли различаются");
        }

        if (isUsernameDubl) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
        }
        if (isEmailDubl) {
            model.addAttribute("emailError", "Пользователь с таким e-mail уже существует");
        }
        if (isNotEqualPass || isEmailDubl || isUsernameDubl || isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        userService.addUser(user);

        return "redirect:/login";
    }

    /**
     * Активация учетной записи
     *
     * @param model Map<String, Object>
     * @param code  Код активации
     * @return "login";
     */
    @GetMapping("/activate/{code}")
    public String activate(
            Map<String, Object> model,
            @PathVariable String code) {
        try {
            userService.activateUser(code);
            model.put("messageType", "success");
            model.put("message", "Success!!");
        } catch (RuntimeException ex) {
            model.put("messageType", "danger");
            model.put("message", ex.getMessage());
            return "login";
        }
        return "login";
    }

    /**
     * Пеход на страницу повторной отправки кода
     *
     * @return "sendEmailForm";
     */
    @GetMapping("/sendCode")
    public String sendCode() {
        return "sendEmailForm";
    }

    /**
     * Команда отправки кода и переход на страницу Авторизации
     *
     * @param email Адрес
     * @param model Сообщения
     * @return "sendEmailForm";
     */
    @PostMapping("/sendCode")
    public String sendCode(
            @RequestParam("email") String email,
            Model model
    ) {
        boolean isEmailEmpty = StringUtils.isEmpty(email);
        boolean isEmailExist = !userService.checkEmail(email);
        if (isEmailEmpty) {
            model.addAttribute("message", "Поле не должно быть пустым");
            return "sendEmailForm";
        } else if (isEmailExist) {
            model.addAttribute("message", "Пользователь с данным адресом отсутствует в базе");
            return "sendEmailForm";
        }

        userService.sendCode(email);
        return "redirect:/home";
    }
}
