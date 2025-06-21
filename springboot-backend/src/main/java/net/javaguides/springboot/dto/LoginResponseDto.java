package net.javaguides.springboot.dto;

public class LoginResponseDto {
	private String email;
    private String role;

    // Constructor, Getters, Setters
    public LoginResponseDto(String email, String role) {
        this.email = email;
        this.role = role;
    }
    
    // ... getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
