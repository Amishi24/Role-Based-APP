package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "role_functionality")
@IdClass(RoleFunctionalityId.class)
public class RoleFunctionality {

    @Id
    private Integer roleId;

    @Id
    private String functionality;

    @Column(nullable = false)
    private String roleName;

    public RoleFunctionality() {}

    public RoleFunctionality(Integer roleId, String roleName, String functionality) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.functionality = functionality;
    }

    // Getters and Setters

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getFunctionality() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality = functionality;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}