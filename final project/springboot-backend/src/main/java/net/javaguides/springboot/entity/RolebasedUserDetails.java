package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "rolebased_user_details")
public class RolebasedUserDetails {
	@Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private RolebasedUser user;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String emailId;

    @Column(length = 10)
    private String phoneNo; // can be null
    
    // Getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RolebasedUser getUser() {
		return user;
	}

	public void setUser(RolebasedUser user) {
		this.user = user;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}    

}
