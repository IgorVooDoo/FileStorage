package com.ivd.example.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Сущность связанная с таблицей хранящей файлы
 */
@Entity
public class DataObject {
    /**
     * Идентификатор объекта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    /**
     * Наименование объекта
     */
    @Column(name = "data_name")
    private String name;

    /**
     * Тип объекта
     */
    @Column(name = "contype")
    private String contentType;
    /**
     * Количество скачиваний
     */
    @Column(name = "access_count")
    private Integer accessCount;
    /**
     * Автор загруженного файла
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;
    /**
     * Уникальное имя файла
     */
    private String uuidName;


    public DataObject(String name, String contentType, User author) {
        this.author = author;
        this.name = name;
        this.contentType = contentType;
    }

    public DataObject(String name, String contentType, Integer accessCount, User author) {
        this.name = name;
        this.contentType = contentType;
        this.accessCount = accessCount;
        this.author = author;
    }

    public DataObject() {
    }

    public String getAuthorName() {
        return author.getUsername();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }

    public String getUuidName() {
        return uuidName;
    }

    public void setUuidName(String uuidName) {
        this.uuidName = uuidName;
    }
}
