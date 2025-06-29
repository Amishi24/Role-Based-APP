package net.javaguides.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.dto.AdminAddUserRequestDto;
import net.javaguides.springboot.dto.AdminUpdateUserRequestDto;
import net.javaguides.springboot.dto.UserListResponseDto;
import net.javaguides.springboot.service.UserService;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody AdminAddUserRequestDto dto) {
        try {
            userService.addUserByAdmin(dto);
            return ResponseEntity.ok("User added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<UserListResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllNonAdminUsers());
    }
    
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveUser(@PathVariable Integer id) {
        userService.approveUser(id);
        return ResponseEntity.ok("User approved successfully");
    }
    
    //  REJECT = Only set approved = false
    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectUser(@PathVariable Integer id) {
        userService.rejectUser(id);
        return ResponseEntity.ok("User rejected (approval revoked)");
    }

    //  DELETE = Hard delete user + details
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted permanently");
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<AdminUpdateUserRequestDto> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id,
                                             @RequestBody AdminUpdateUserRequestDto dto) {
        userService.updateUser(id, dto);
        return ResponseEntity.ok("User updated successfully");
    }
    
    @GetMapping("/get-user-id")
    public ResponseEntity<Integer> getUserIdByEmail(@RequestParam String email) {
        return userService.getUserIdByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }
}