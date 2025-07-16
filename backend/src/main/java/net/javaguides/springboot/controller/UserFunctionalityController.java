package net.javaguides.springboot.controller;

import net.javaguides.springboot.service.FunctionalityService;
import net.javaguides.springboot.dao.DynamicUserRepository;
import net.javaguides.springboot.dto.AssignFunctionalityRequest;
import net.javaguides.springboot.entity.DynamicUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserFunctionalityController {

    private final FunctionalityService functionalityService;
    private final DynamicUserRepository userRepository;

    @Autowired
    public UserFunctionalityController(FunctionalityService functionalityService, DynamicUserRepository userRepository) {
        this.functionalityService = functionalityService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}/functionalities")
    public List<String> getUserFunctionalities(@PathVariable Integer userId) {
        return functionalityService.getFunctionalitiesByUserId(userId);
    }

    @PostMapping("/assign-functionalities")
    public ResponseEntity<String> assignFunctionalities(@RequestBody List<AssignFunctionalityRequest> accessList) {
        functionalityService.assignFunctionalities(accessList);
        return ResponseEntity.ok("Functionalities assigned successfully");
    }

    @DeleteMapping("/{userId}/revoke/{functionality}")
    public ResponseEntity<String> revokeFunctionality(
            @PathVariable Integer userId,
            @PathVariable String functionality) {

        functionalityService.revokeFunctionality(userId, functionality);
        return ResponseEntity.ok("Functionality revoked successfully");
    }

    @GetMapping("/details/{username}")
    public ResponseEntity<DynamicUser> getUserDetailsByUsername(@PathVariable String username) {
        return userRepository.findByUserName(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{roleName}/functionalities")
    public ResponseEntity<List<String>> getFunctionalitiesByRole(@PathVariable String roleName) {
        List<String> functionalities = functionalityService.getFunctionalitiesByRoleName(roleName);
        return ResponseEntity.ok(functionalities);
    }

    @GetMapping("/role/{roleName}/users")
    public ResponseEntity<List<DynamicUser>> getUsersByRole(@PathVariable String roleName) {
        List<DynamicUser> users = userRepository.findByUserRole(roleName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getAllDistinctRoles() {
        List<String> roles = userRepository.findDistinctUserRoles();
        return ResponseEntity.ok(roles);
    }
}
