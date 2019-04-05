package com.ivd.example.controller;

import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;
import com.ivd.example.service.DataObjectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Контроллер приложения, получает и обрабатывает запросы пользователя
 */
@Controller
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

            dataObjectService.addData(user, file, name);
            model.put("message", "Файл успешно добавлен");
            model.put("messageType", "success");
        } else {
            model.put("message", "Не указан файл");
            model.put("messageType", "danger");
        }
        model.put("messages", dataObjectService.findByAuthor(user));
        return "home";
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
     */
    @GetMapping("/download/{message}")
    public void downloadMessage(
            @PathVariable DataObject message,
            HttpServletResponse response
    ) {
        File file = new File(uploadPath + "/" + message.getUuidName());

        String mimeType = message.getContentType();
        if (StringUtils.isEmpty(mimeType)) {
            mimeType = "application/octet-stream";
        }

        try (InputStream fis = new BufferedInputStream(new FileInputStream(file));
             OutputStream toClient = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + message.getName());
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType(mimeType);
            toClient.write(buffer);
            toClient.flush();
            dataObjectService.addAccessCount(message);
        } catch (IOException ex) {
            LOG.info("downloadMessage IOException -> " + ex.getMessage());
        }
    }
}
