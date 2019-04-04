package com.ivd.example.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Сущность связанная с таблицей пользователей
 */
@Entity
@Table(name = "usr")
public class User implements UserDetails {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Логин пользователя
     */
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;
    /**
     * Пароль
     */
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    /**
     * Номер телефона
     */
    @Length(max = 10)
    private String phone;
    /**
     * Адрес электронной почты
     */
    @Email(message = "E-mail is not correct!")
    @NotBlank(message = "E-mail не может быть пустым")
    private String email;

    private boolean active;
    /**
     * Код активации
     */
    private String activationCode;
    /**
     * Дата регистрации
     */
    private Date dateRegistration;
    /**
     * Роль пользователя
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * Связь с таблице хранимых файлов
     */
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    private Set<DataObject> dataObjects;

    public User(String username, String password, @Length(max = 10) String phone, String email, boolean active, Date dateRegistration, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.active = active;
        this.dateRegistration = dateRegistration;
        this.roles = roles;
    }

    public User() {
    }

    /**
     * Проверка на Админа
     *
     * @return boolean
     */
    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    /**
     * Проверка на Аналитика
     *
     * @return boolean
     */
    public boolean isAnalyst() {
        return roles.contains(Role.ANALYST);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<DataObject> getDataObjects() {
        return dataObjects;
    }

    public void setDataObjects(Set<DataObject> dataObjects) {
        this.dataObjects = dataObjects;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
