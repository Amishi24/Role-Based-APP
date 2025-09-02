package net.javaguides.springboot.entity;

import java.io.Serializable;
import java.util.Objects;

public class RoleFunctionalityId implements Serializable {

    private Integer roleId;
    private String functionality;

    public RoleFunctionalityId() {}

    public RoleFunctionalityId(Integer roleId, String functionality) {
        this.roleId = roleId;
        this.functionality = functionality;
    }

    // Optional but helpful: getters & setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleFunctionalityId)) return false;
        RoleFunctionalityId that = (RoleFunctionalityId) o;
        return Objects.equals(roleId, that.roleId) &&
               Objects.equals(functionality, that.functionality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, functionality);
    }
}