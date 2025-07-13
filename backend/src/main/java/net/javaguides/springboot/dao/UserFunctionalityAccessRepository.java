package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.UserFunctionalityAccess;
import net.javaguides.springboot.entity.UserFunctionalityAccessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface UserFunctionalityAccessRepository extends JpaRepository<UserFunctionalityAccess, UserFunctionalityAccessId> {
	List<UserFunctionalityAccess> findByUserId(Integer userId);
	
	@Transactional
	void deleteByUserIdAndFunctionality(Integer userId, String functionality);
}