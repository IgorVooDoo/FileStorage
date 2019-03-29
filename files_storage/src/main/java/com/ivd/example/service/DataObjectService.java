package com.ivd.example.service;

import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;

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
}
