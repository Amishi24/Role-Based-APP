package net.javaguides.springboot.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.dao.RolebasedUserRepository;
import net.javaguides.springboot.dao.UserWeaponAccessRepository;
import net.javaguides.springboot.dao.WeaponRepository;
import net.javaguides.springboot.entity.RolebasedUser;
import net.javaguides.springboot.entity.UserWeaponAccess;
import net.javaguides.springboot.entity.UserWeaponAccessId;
import net.javaguides.springboot.entity.Weapon;
import net.javaguides.springboot.service.AdminWeaponService;

@Service
public class AdminWeaponServiceImpl implements AdminWeaponService {

    @Autowired
    private UserWeaponAccessRepository userWeaponAccessRepo;

    @Autowired
    private RolebasedUserRepository userRepo;

    @Autowired
    private WeaponRepository weaponRepo;

    @Override
    public String assignWeaponToUser(Integer userId, String weaponId) {
        RolebasedUser user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Weapon weapon = weaponRepo.findById(weaponId)
                .orElseThrow(() -> new RuntimeException("Weapon not found"));

        UserWeaponAccessId id = new UserWeaponAccessId(userId, weaponId);

        if (userWeaponAccessRepo.existsById(id)) {
            return "Weapon already assigned to user";
        }

        UserWeaponAccess access = new UserWeaponAccess();
        access.setUser(user);
        access.setWeapon(weapon);
        userWeaponAccessRepo.save(access);

        return "Weapon assigned successfully";
    }

    @Override
    public String revokeWeaponFromUser(Integer userId, String weaponId) {
        UserWeaponAccessId id = new UserWeaponAccessId(userId, weaponId);

        if (!userWeaponAccessRepo.existsById(id)) {
            return "Weapon not assigned to user";
        }

        userWeaponAccessRepo.deleteById(id);
        return "Weapon access revoked";
    }

    @Override
    public List<Weapon> getUserWeapons(Integer userId) {
        List<UserWeaponAccess> accessList = userWeaponAccessRepo.findByUserId(userId);
        return accessList.stream()
                .map(UserWeaponAccess::getWeapon)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Weapon> getWeaponsByCategory(String category) {
        return weaponRepo.findByCategory(category);
    }
}