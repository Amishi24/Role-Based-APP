package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.Weapon;
import net.javaguides.springboot.service.UserWeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import net.javaguides.springboot.entity.RolebasedUser;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserWeaponController {

    @Autowired
    private UserWeaponService userWeaponService;

    @GetMapping("/my-weapons")
    public ResponseEntity<List<Weapon>> getMyWeapons(Authentication authentication) {
        // Spring Security context se user fetch karo
        RolebasedUser user = (RolebasedUser) authentication.getPrincipal();
        Integer userId = user.getId();

        List<Weapon> weapons = userWeaponService.getMyWeapons(userId);
        return ResponseEntity.ok(weapons);
    }
}