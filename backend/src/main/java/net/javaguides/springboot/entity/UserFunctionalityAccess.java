package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_functionality_access")
@IdClass(UserFunctionalityAccessId.class)
public class UserFunctionalityAccess {

	@Id
    private Integer userId;

    @Id
    private String functionality;

    private Integer roleId;

    private String roleName;

    public UserFunctionalityAccess() {}

    public UserFunctionalityAccess(Integer userId, Integer roleId, String roleName, String functionality) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleName = roleName;
        this.functionality = functionality;
    }

    // Getters and Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFunctionality() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}