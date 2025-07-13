package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.DynamicUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicUserRepository extends JpaRepository<DynamicUser, Integer> {
	boolean existsByUserRole(String roleName);
	Optional<DynamicUser> findByUserName(String userName);
}