package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.RoleFunctionality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleFunctionalityRepository extends JpaRepository<RoleFunctionality, Long> {
	boolean existsByRoleName(String roleName);
    List<RoleFunctionality> findByRoleName(String roleName);
    Optional<RoleFunctionality> findFirstByFunctionality(String functionality);
}