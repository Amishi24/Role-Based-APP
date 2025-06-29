package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "rolebased_user")
public class RolebasedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username; // email

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "user";

    @Column(nullable = false)
    private Boolean approved = false;
    
    
    // getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
    
}
