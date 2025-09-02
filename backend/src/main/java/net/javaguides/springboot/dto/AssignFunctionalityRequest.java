package net.javaguides.springboot.dto;


public class AssignFunctionalityRequest {
	private Integer userId;
    private String functionality;

    public AssignFunctionalityRequest() {}

    public AssignFunctionalityRequest(Integer userId, String functionality) {
        this.userId = userId;
        this.functionality = functionality;
    }

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
}