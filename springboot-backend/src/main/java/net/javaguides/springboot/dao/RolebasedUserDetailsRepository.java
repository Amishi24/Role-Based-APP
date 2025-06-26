package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.RolebasedUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolebasedUserDetailsRepository extends JpaRepository<RolebasedUserDetails, Integer> {

}