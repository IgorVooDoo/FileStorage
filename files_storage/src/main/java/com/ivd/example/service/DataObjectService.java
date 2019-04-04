package com.ivd.example.service;

import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Сервис для обработки запросов по файлам
 */
public interface DataObjectService {
    /**
     * Поиск объектов по автору
     *
     * @param user Пользователь
     * @return List<DataObject>
     */
    List<DataObject> findByAuthor(User user);

    /**
     * Количество сообщений
     *
     * @return Long
     */
    Long messageCount();

    /**
     * Добавление файла в базу
     *
     * @param user Пользователь
     * @param file Файл
     * @param name Имя файла
     * @throws IOException Исключение
     */
    void addData(User user, MultipartFile file, String name) throws IOException;

    /**
     * Сохранение изменений в файле
     *
     * @param message Файл
     */
    void saveData(DataObject message);

    /**
     * Удаление файла по идентификатору
     *
     * @param message Файл
     */
    void deleteDataById(DataObject message);
}
