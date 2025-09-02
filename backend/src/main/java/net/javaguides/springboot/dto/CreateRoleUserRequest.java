package net.javaguides.springboot.dto;

import java.util.List;

public class CreateRoleUserRequest {
    private String roleName;
    private List<String> functionalities;
    private String username;

    public CreateRoleUserRequest() {
    }

    public CreateRoleUserRequest(String roleName, List<String> functionalities, String username) {
        this.roleName = roleName;
        this.functionalities = functionalities;
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getFunctionalities() {
        return functionalities;
    }

    public void setFunctionalities(List<String> functionalities) {
        this.functionalities = functionalities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}