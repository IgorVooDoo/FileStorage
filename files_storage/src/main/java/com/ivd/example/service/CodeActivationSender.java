package com.ivd.example.service;

/**
 * Сервис для отправки сообщений
 */
public interface CodeActivationSender {

    /**
     * Отправка сообщения на email пользователя
     *
     * @param emailTo адрес
     * @param subject объект
     * @param message текст
     */
    void sendMail(String emailTo, String subject, String message);
}
