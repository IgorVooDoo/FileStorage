package com.ivd.example.controller;

import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;
import com.ivd.example.service.DataObjectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Контроллер приложения, получает и обрабатывает запросы пользователя
 */
@Controller
@Transactional
public class DataObjectController {

    private final Logger LOG = LoggerFactory.getLogger(DataObjectController.class);
    private final DataObjectService dataObjectService;

    public DataObjectController(DataObjectService dataObjectService) {
        this.dataObjectService = dataObjectService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Метод добавления файла
     *
     * @param user  Пользователь
     * @param name  Название файла
     * @param file  Файл
     * @param model Map<String, Object> model
     * @return String
     */
    @PostMapping("/addData")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam("file") MultipartFile file,
            Map<String, Object> model) throws IOException {
        if (file != null) {
            if (StringUtils.isEmpty(name)) {
                name = Objects.requireNonNull(file).getOriginalFilename();
            }
            dataObjectService.addData(user, file, name);
            model.put("message", "Файл успешно добавлен");
            model.put("messageType", "success");
        } else {
            model.put("message", "Не указан файл");
            model.put("messageType", "danger");
        }
        model.put("messages", dataObjectService.findByAuthor(user));
        return "redirect:/home";
    }

    /**
     * Удаление файла
     *
     * @param user    Пользователь
     * @param message Файл
     * @param model   Возвращаемые объекты
     * @return "redirect:/home"
     */
    @GetMapping("/del/{message}")
    public String deleteMessage(
            @AuthenticationPrincipal User user,
            @PathVariable DataObject message,
            Map<String, Object> model
    ) {
        dataObjectService.deleteDataById(message);

        model.put("messages", dataObjectService.findByAuthor(user));
        return "redirect:/home";
    }

    /**
     * Скачивание файла
     *
     * @param message Файл
     * @return ResponseEntity
     */
    @GetMapping("/download/{message}")
    public ResponseEntity downloadMessage(
            @PathVariable DataObject message
    ) {
        addAccessCount(message);

        File file = new File(uploadPath + "/" + message.getUuidName());
        String mimeType = message.getContentType();
        if (StringUtils.isEmpty(mimeType)) {
            mimeType = "application/octet-stream";
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            LOG.info("new FileInputStream(file) -> ");
            InputStreamResource resource = new InputStreamResource(fileInputStream);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + message.getName())
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        } catch (IOException ex) {
            LOG.info("FileNotFoundException -> " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private void addAccessCount(DataObject message) {
        if (message.getAccessCount() == null) {
            message.setAccessCount(1);
        } else {
            message.setAccessCount(message.getAccessCount() + 1);
        }
        dataObjectService.saveData(message);
    }
}
