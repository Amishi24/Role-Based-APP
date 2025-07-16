package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.DynamicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DynamicUserRepository extends JpaRepository<DynamicUser, Integer> {
    boolean existsByUserRole(String roleName);
    Optional<DynamicUser> findByUserName(String userName);
    List<DynamicUser> findByUserRole(String userRole);
    
    @Query("SELECT DISTINCT u.userRole FROM DynamicUser u")
    List<String> findDistinctUserRoles();
}
