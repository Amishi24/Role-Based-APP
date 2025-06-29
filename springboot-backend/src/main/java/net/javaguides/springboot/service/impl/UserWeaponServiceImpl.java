package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dao.UserWeaponAccessRepository;
import net.javaguides.springboot.entity.UserWeaponAccess;
import net.javaguides.springboot.entity.Weapon;
import net.javaguides.springboot.service.UserWeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWeaponServiceImpl implements UserWeaponService {

    @Autowired
    private UserWeaponAccessRepository userWeaponAccessRepo;

    @Override
    public List<Weapon> getMyWeapons(Integer userId) {
        List<UserWeaponAccess> accessList = userWeaponAccessRepo.findByUserId(userId);
        return accessList.stream()
                .map(UserWeaponAccess::getWeapon)
                .collect(Collectors.toList());
    }
}