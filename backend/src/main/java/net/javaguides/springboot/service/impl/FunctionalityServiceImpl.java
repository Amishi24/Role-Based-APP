package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dao.*;
import net.javaguides.springboot.entity.*;
import net.javaguides.springboot.dto.AssignFunctionalityRequest;
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
	         // Step 1: Find role info by functionality
	         RoleFunctionality rf = roleRepo.findFirstByFunctionality(dto.getFunctionality())
	             .orElseThrow(() -> new RuntimeException("Functionality not found: " + dto.getFunctionality()));

	         // Step 2: Use fetched roleId and roleName
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
	 

}
