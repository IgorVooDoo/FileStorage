package com.ivd.example.controller;

import com.ivd.example.dao.DataObjectDao;
import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;
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
import java.util.UUID;

/**
 * Контроллер приложения, получает и обрабатывает запросы пользователя
 */
@Controller
@Transactional
public class DataObjectController {

    private final Logger LOG = LoggerFactory.getLogger(DataObjectController.class);
    private final DataObjectDao dataObjectDao;

    public DataObjectController(DataObjectDao dataObjectDao) {
        this.dataObjectDao = dataObjectDao;
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
        LOG.info("Зашли в добавление пользователя");
        DataObject dataObject = new DataObject();
        if (file != null) {
            File uploadDir = new File("D:" + uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File("D:" + uploadPath + "/" + resultFileName));
            dataObject.setUuidName(resultFileName);
        }
        if (name == null || name.trim().equals("")) {
            dataObject.setName(Objects.requireNonNull(file).getOriginalFilename());
        } else {
            dataObject.setName(name);
        }
        dataObject.setAuthor(user);
        dataObject.setContentType(Objects.requireNonNull(file).getContentType());
        dataObject.setAccessCount(0);
        dataObjectDao.save(dataObject);
        Iterable<DataObject> messages = dataObjectDao.findByAuthor(user);
        model.put("messages", messages);
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
        File file = new File("D:" + uploadPath + "/" + message.getUuidName());
        file.delete();
        dataObjectDao.deleteById(message.getId());
        Iterable<DataObject> messages = dataObjectDao.findByAuthor(user);
        model.put("messages", messages);
        return "redirect:/home";
    }

    /**
     * Скачивание файла
     *
     * @param message Файл
     * @return ResponseEntity
     * @throws IOException Исключение
     */
    @GetMapping("/download/{message}")
    public ResponseEntity downloadMessage(
            @PathVariable DataObject message
    ) throws IOException {
        if (message.getAccessCount() == null) {
            message.setAccessCount(1);
        } else {
            message.setAccessCount(message.getAccessCount() + 1);
        }
        dataObjectDao.save(message);
        File file = new File("D:" + uploadPath + "/" + message.getUuidName());

        String mimeType = message.getContentType();

        if ("".equals(mimeType)) {
            mimeType = "application/octet-stream";
        }
        InputStreamResource resource =
                new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + message.getName())
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }
}
