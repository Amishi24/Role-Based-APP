package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.RolebasedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolebasedUserRepository extends JpaRepository<RolebasedUser, Integer> {
    boolean existsByUsername(String username);
    Optional<RolebasedUser> findByUsernameAndPassword(String username, String password);
    Optional<RolebasedUser> findByUsername(String username);
}