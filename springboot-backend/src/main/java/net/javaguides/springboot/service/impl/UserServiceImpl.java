package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dao.RolebasedUserRepository;
import net.javaguides.springboot.dao.RolebasedUserDetailsRepository;
import net.javaguides.springboot.dto.AdminAddUserRequestDto;
import net.javaguides.springboot.dto.AdminUpdateUserRequestDto;
import net.javaguides.springboot.dto.UserListResponseDto;
import net.javaguides.springboot.dto.LoginResponseDto;
import net.javaguides.springboot.dto.UserLoginDto;
import net.javaguides.springboot.dto.UserRegistrationDto;
import net.javaguides.springboot.entity.RolebasedUser;
import net.javaguides.springboot.entity.RolebasedUserDetails;
import net.javaguides.springboot.service.UserService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RolebasedUserRepository userRepo;

    @Autowired
    private RolebasedUserDetailsRepository detailsRepo;

    @Override
    @Transactional
    public void registerUser(UserRegistrationDto dto) {
        if (userRepo.existsByUsername(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        RolebasedUser user = new RolebasedUser();
        user.setUsername(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole("user"); // Optional: if your entity has a role field
        user.setApproved(false); // Optional: if your entity has an approved field
        userRepo.save(user);

        RolebasedUserDetails details = new RolebasedUserDetails();
        details.setUser(user);
        details.setFullName(dto.getFullName());
        details.setEmailId(dto.getEmail());
        details.setPhoneNo(null);
        detailsRepo.save(details);
    }
    
    @Override
    public LoginResponseDto loginUser(UserLoginDto dto) {
        // 1. Check if user exists
        Optional<RolebasedUser> userOpt = userRepo.findByUsername(dto.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }
        RolebasedUser user = userOpt.get();

        // 2. Check password
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        // 3. Check approval
        if (!Boolean.TRUE.equals(user.getApproved())) {
            throw new RuntimeException("Not approved yet");
        }

        // 4. Success
        return new LoginResponseDto(user.getUsername(), user.getRole()); // Assuming user.getRole() is available
    }
    
    @Override
    @Transactional
    public void addUserByAdmin(AdminAddUserRequestDto dto) {
        if (userRepo.existsByUsername(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        RolebasedUser user = new RolebasedUser();
        user.setUsername(dto.getEmail());
        user.setPassword(dto.getEmail()); // As per your logic: password = email
        user.setRole(dto.getRole().toLowerCase());
        user.setApproved(true); // Admin-added user is auto-approved

        user = userRepo.save(user);

        RolebasedUserDetails details = new RolebasedUserDetails();
        details.setUser(user);
        details.setFullName(dto.getFirstName() + " " + dto.getLastName());
        details.setEmailId(dto.getEmail());
        details.setPhoneNo(null);

        detailsRepo.save(details);
    }
    
    @Override
    public List<UserListResponseDto> getAllNonAdminUsers() {
        List<RolebasedUser> users = userRepo.findAll();
        List<UserListResponseDto> response = new ArrayList<>();

        for (RolebasedUser user : users) {
            if (!user.getRole().equalsIgnoreCase("admin")) {
                RolebasedUserDetails details = detailsRepo.findById(user.getId()).orElse(null);
                if (details != null) {
                    String[] nameParts = details.getFullName().split(" ", 2);
                    String firstName = nameParts.length > 0 ? nameParts[0] : "";
                    String lastName = nameParts.length > 1 ? nameParts[1] : "";

                    UserListResponseDto dto = new UserListResponseDto();
                    dto.setId(user.getId());
                    dto.setEmail(user.getUsername());
                    dto.setFirstName(firstName);
                    dto.setLastName(lastName);
                    dto.setApproved(user.getApproved());

                    response.add(dto);
                }
            }
        }
        return response;
    }
    
    
    @Override
    @Transactional
    public void approveUser(Integer id) {
        RolebasedUser user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setApproved(true);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void rejectUser(Integer id) {
        RolebasedUser user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        detailsRepo.deleteById(user.getId());
        userRepo.deleteById(user.getId());
    }
    
    @Override
    public AdminUpdateUserRequestDto getUserById(Integer id) {
        RolebasedUser user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        RolebasedUserDetails details = detailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Details not found"));

        AdminUpdateUserRequestDto dto = new AdminUpdateUserRequestDto ();
        String[] name = details.getFullName().split(" ", 2);
        dto.setFirstName(name.length > 0 ? name[0] : "");
        dto.setLastName(name.length > 1 ? name[1] : "");
        dto.setEmail(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    @Transactional
    public void updateUser(Integer id, AdminUpdateUserRequestDto dto) {
        RolebasedUser user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        RolebasedUserDetails details = detailsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Details not found"));

        user.setUsername(dto.getEmail());
        user.setRole(dto.getRole());
        userRepo.save(user);

        details.setEmailId(dto.getEmail());
        details.setFullName(dto.getFirstName() + " " + dto.getLastName());
        detailsRepo.save(details);
    }
    
    @Override
    public void deleteUser(Integer id) {
    	detailsRepo.deleteById(id);
        userRepo.deleteById(id);
    }
    
}
