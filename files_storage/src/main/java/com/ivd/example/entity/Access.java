package com.ivd.example.entity;

import javax.persistence.*;

@Entity
public class Access {
    /**
     * Составной идентификатор
     */
    @EmbeddedId
    private
    AccessKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("member_id")
    @JoinColumn(name = "member_id")
    private User member;
    /**
     * Тип доступа
     */
    private TypeAccess typeAccess;
    /**
     * Проверяется, запрос или нет
     */
    private boolean isQuery;

    public Access(User user, User member) {
        this.id = new AccessKey(user.getId(), member.getId());
        this.user = user;
        this.member = member;
    }

    public Access() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public TypeAccess getTypeAccess() {
        return typeAccess;
    }

    public void setTypeAccess(TypeAccess typeAccess) {
        this.typeAccess = typeAccess;
    }

    public boolean isQuery() {
        return isQuery;
    }

    public void setQuery(boolean query) {
        isQuery = query;
    }
}
