package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.AssignFunctionalityRequest;
import java.util.List;

public interface FunctionalityService {
	 List<String> getFunctionalitiesByUserId(Integer userId);
	 void assignFunctionalities(List<AssignFunctionalityRequest> accessList);
	 void revokeFunctionality(Integer userId, String functionality);
	 List<String> getFunctionalitiesByRoleName(String roleName);
}