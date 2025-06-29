package net.javaguides.springboot.dto;

public class AdminUpdateUserRequestDto {
	private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Boolean approved;

    // Getters & Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
	
    public Boolean getApproved() { return approved; }
	public void setApproved(Boolean approved) {	this.approved = approved;}
	
}