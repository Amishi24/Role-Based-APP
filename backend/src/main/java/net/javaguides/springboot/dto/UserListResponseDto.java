package net.javaguides.springboot.dto;

public class UserListResponseDto {
	
	private String roleName;
    private String userName;
    private String functionality;
    
    public UserListResponseDto() {}
    
    public UserListResponseDto(String roleName, String userName, String functionality) {
        this.roleName = roleName;
        this.userName = userName;
        this.functionality = functionality;
    }
    
    // Getters and setters
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFunctionality() {
		return functionality;
	}
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}    
}
