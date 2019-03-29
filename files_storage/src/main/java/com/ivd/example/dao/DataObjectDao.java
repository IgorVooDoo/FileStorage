package com.ivd.example.dao;

import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий обеспечивающий связь с таблицей базы данных
 */
@Repository
public interface DataObjectDao extends CrudRepository<DataObject, Long> {
    /**
     * Поиск объекта по автору
     *
     * @param user Пользователь
     * @return Список объектов
     */
    List<DataObject> findByAuthor(User user);

}
