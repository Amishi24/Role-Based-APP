package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.UserWeaponAccess;
import net.javaguides.springboot.entity.UserWeaponAccessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserWeaponAccessRepository extends JpaRepository<UserWeaponAccess, UserWeaponAccessId> {
    List<UserWeaponAccess> findByUserId(Integer userId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM UserWeaponAccess uwa WHERE uwa.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
}