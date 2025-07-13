package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "dynamic_user")
public class DynamicUser {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userRole;

    public DynamicUser() {}

    public DynamicUser(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
    }

    // Getters and Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}