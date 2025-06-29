package net.javaguides.springboot.service;

import java.util.List;
import net.javaguides.springboot.entity.Weapon;

public interface AdminWeaponService {
    String assignWeaponToUser(Integer userId, String weaponId);
    String revokeWeaponFromUser(Integer userId, String weaponId);
    List<Weapon> getUserWeapons(Integer userId);
    List<Weapon> getWeaponsByCategory(String category);
}