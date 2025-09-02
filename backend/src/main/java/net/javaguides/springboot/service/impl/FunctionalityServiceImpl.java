package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dao.*;
import net.javaguides.springboot.entity.*;
import net.javaguides.springboot.dto.AssignFunctionalityRequest;
import net.javaguides.springboot.dto.UserListResponseDto;
import net.javaguides.springboot.service.FunctionalityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FunctionalityServiceImpl implements FunctionalityService {

    private final UserFunctionalityAccessRepository accessRepo;
    private final RoleFunctionalityRepository roleRepo;
    private final DynamicUserRepository userRepo;

    public FunctionalityServiceImpl(UserFunctionalityAccessRepository accessRepo, RoleFunctionalityRepository roleRepo, DynamicUserRepository userRepo) {
        this.accessRepo = accessRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<String> getFunctionalitiesByUserId(Integer userId) {
        List<UserFunctionalityAccess> accessList = accessRepo.findByUserId(userId);
        List<String> functionalityList = new ArrayList<>();

        for (UserFunctionalityAccess access : accessList) {
            functionalityList.add(access.getFunctionality());
        }
        return functionalityList;
    }

    @Override
    public void assignFunctionalities(List<AssignFunctionalityRequest> accessList) {
        for (AssignFunctionalityRequest dto : accessList) {
            RoleFunctionality rf = roleRepo.findFirstByFunctionality(dto.getFunctionality())
                .orElseThrow(() -> new RuntimeException("Functionality not found: " + dto.getFunctionality()));

            UserFunctionalityAccess access = new UserFunctionalityAccess(
                dto.getUserId(),
                rf.getRoleId(),
                rf.getRoleName(),
                dto.getFunctionality()
            );

            accessRepo.save(access);
        }
    }

    @Override
    public void revokeFunctionality(Integer userId, String functionality) {
        accessRepo.deleteByUserIdAndFunctionality(userId, functionality);
    }

    @Override
    public List<String> getFunctionalitiesByRoleName(String roleName) {
        List<RoleFunctionality> roleFunctionalities = roleRepo.findByRoleName(roleName);
        List<String> functionalities = new ArrayList<>();
        for (RoleFunctionality rf : roleFunctionalities) {
            functionalities.add(rf.getFunctionality());
        }
        return functionalities;
    }

    @Override
    public List<String> getUsernamesByRole(String roleName) {
        List<DynamicUser> users = userRepo.findByUserRole(roleName);
        List<String> usernames = new ArrayList<>();
        for (DynamicUser user : users) {
            usernames.add(user.getUserName());
        }
        return usernames;
    }

    @Override
    public String createRoleUserAndFunctionalities(String roleName, List<String> functionalities, String username) {
        boolean isNewRole = !userRepo.existsByUserRole(roleName);
        Integer roleId;

        if (roleName == null || roleName.trim().isEmpty()) {
            throw new RuntimeException("Role name is required.");
        }

        if (isNewRole) {
            if (functionalities == null || functionalities.isEmpty()) {
                throw new RuntimeException("At least one functionality is required for a new role.");
            }

            if (username == null || username.trim().isEmpty()) {
                throw new RuntimeException("Username is required for a new role.");
            }

            if (userRepo.findByUserName(username).isPresent()) {
                throw new RuntimeException("User already exists");
            }

            roleId = roleRepo.findMaxRoleId().orElse(0) + 1;
            for (String func : functionalities) {
                RoleFunctionality rf = new RoleFunctionality();
                rf.setRoleId(roleId);
                rf.setRoleName(roleName);
                rf.setFunctionality(func);
                roleRepo.save(rf);
            }

            Integer userId = userRepo.findMaxUserId().orElse(0) + 1;
            DynamicUser user = new DynamicUser();
            user.setUserId(userId);
            user.setUserName(username);
            user.setUserRole(roleName);
            userRepo.save(user);

            return "New role, functionalities, and user added successfully";

        } else {
            List<RoleFunctionality> existingFuncs = roleRepo.findByRoleName(roleName);
            roleId = existingFuncs.stream()
                    .findFirst()
                    .map(RoleFunctionality::getRoleId)
                    .orElseThrow(() -> new RuntimeException("Role exists but no roleId found"));

            List<String> existingFuncNames = existingFuncs.stream()
                    .map(RoleFunctionality::getFunctionality)
                    .toList();

            int addedCount = 0;
            if (functionalities != null) {
                for (String func : functionalities) {
                    if (!existingFuncNames.contains(func)) {
                        RoleFunctionality rf = new RoleFunctionality();
                        rf.setRoleId(roleId);
                        rf.setRoleName(roleName);
                        rf.setFunctionality(func);
                        roleRepo.save(rf);
                        addedCount++;
                    }
                }
            }

            if (username != null && !username.trim().isEmpty()) {
                if (userRepo.findByUserName(username).isPresent()) {
                    throw new RuntimeException("User already exists");
                }

                Integer userId = userRepo.findMaxUserId().orElse(0) + 1;
                DynamicUser user = new DynamicUser();
                user.setUserId(userId);
                user.setUserName(username);
                user.setUserRole(roleName);
                userRepo.save(user);

                if (addedCount > 0) {
                    return "New user added and functionalities updated";
                } else {
                    return "New user added to existing role";
                }
            }

            if (addedCount > 0) {
                return "Role functionalities updated";
            } else {
                return "No new changes applied";
            }
        }
    }
    
    @Override
    public List<UserListResponseDto> getAllRoleUserFunctionalities() {
        List<UserFunctionalityAccess> accessList = accessRepo.findAll();
        List<UserListResponseDto> result = new ArrayList<>();

        for (UserFunctionalityAccess access : accessList) {
            Integer userId = access.getUserId();
            DynamicUser user = userRepo.findById(userId).orElse(null);
            if (user != null) {
                result.add(new UserListResponseDto(
                    access.getRoleName(),
                    user.getUserName(),
                    access.getFunctionality()
                ));
            }
        }

        return result;
    }

}