package net.javaguides.springboot.service;

import java.util.List;
import net.javaguides.springboot.entity.Weapon;

public interface UserWeaponService {
    List<Weapon> getMyWeapons(Integer userId);
}