package net.javaguides.springboot.dao;

import net.javaguides.springboot.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeaponRepository extends JpaRepository<Weapon, String> {
    List<Weapon> findByCategory(String category);
    
}