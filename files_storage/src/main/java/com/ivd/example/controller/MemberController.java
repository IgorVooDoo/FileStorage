package com.ivd.example.controller;

import com.ivd.example.entity.TypeAccess;
import com.ivd.example.entity.User;
import com.ivd.example.service.AccessService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для обработки запросов на получение и предоставление доступов
 */
@Controller
@RequestMapping("/member")
@Transactional
public class MemberController {
    private final AccessService accessService;

    public MemberController(AccessService accessService) {
        this.accessService = accessService;
    }

    /**
     * Добавление пользователя на чтение списка
     *
     * @param user   Пользователь
     * @param member Участник
     * @return "redirect:/accessMember";
     */
    @PostMapping("/addRead")
    public String addMemberRead(
            @AuthenticationPrincipal User user,
            @RequestParam("mem") User member
    ) {
        accessService.save(user, member, TypeAccess.READ, false);
        return "redirect:/accessMember";
    }

    /**
     * Принять запрос на доступ
     *
     * @param user   Пользователь
     * @param member Участник
     * @return "redirect:/accessMember";
     */
    @GetMapping("/add/{member}")
    public String addMemberFromQuery(
            @AuthenticationPrincipal User user,
            @PathVariable User member
    ) {
        accessService.updateQueryById(user, member);
        return "redirect:/accessMember";
    }

    /**
     * Создание запроса на добавление доступа на чтение списка
     *
     * @param user   Пользователь
     * @param member Участник
     * @return "redirect:/accessMember";
     */
    @PostMapping("/queryRead")
    public String queryRead(
            @AuthenticationPrincipal User user,
            @RequestParam("mem") User member
    ) {
        accessService.save(member, user, TypeAccess.READ, true);
        return "redirect:/accessMember";
    }

    /**
     * Добавление пользователя на скачивание файлов
     *
     * @param user   Пользователь
     * @param member Участник
     * @return "redirect:/accessMember";
     */
    @PostMapping("/addLoad")
    public String addMemberLoad(
            @AuthenticationPrincipal User user,
            @RequestParam("mem") User member
    ) {
        accessService.save(user, member, TypeAccess.LOAD, false);
        return "redirect:/accessMember";
    }

    /**
     * Создание запроса на добавление доступа на скачивание
     *
     * @param user   Пользователь
     * @param member Участник
     * @return "redirect:/accessMember";
     */
    @PostMapping("/queryLoad")
    public String queryLoad(
            @AuthenticationPrincipal User user,
            @RequestParam("mem") User member
    ) {
        accessService.save(member, user, TypeAccess.LOAD, true);
        return "redirect:/accessMember";
    }

    /**
     * Удаление участника
     *
     * @param user   Пользователь
     * @param member Участник
     * @return "redirect:/accessMember";
     */
    @GetMapping("/del/{member}")
    public String delMemberRead(
            @AuthenticationPrincipal User user,
            @PathVariable User member
    ) {
        accessService.deleteById(user, member);
        return "redirect:/accessMember";
    }
}
