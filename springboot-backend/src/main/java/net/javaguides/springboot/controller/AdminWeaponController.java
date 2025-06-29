package net.javaguides.springboot.controller;

import net.javaguides.springboot.entity.Weapon;
import net.javaguides.springboot.service.AdminWeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminWeaponController {

    @Autowired
    private AdminWeaponService adminWeaponService;
    
    @PostMapping("/assign-weapon")
    public ResponseEntity<String> assignWeapon(
            @RequestParam Integer userId,
            @RequestParam String weaponId) {
        String result = adminWeaponService.assignWeaponToUser(userId, weaponId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/revoke-weapon")
    public ResponseEntity<String> revokeWeapon(
            @RequestParam Integer userId,
            @RequestParam String weaponId) {
        String result = adminWeaponService.revokeWeaponFromUser(userId, weaponId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user-weapons/{userId}")
    public ResponseEntity<List<Weapon>> getUserWeapons(@PathVariable Integer userId) {
        List<Weapon> weapons = adminWeaponService.getUserWeapons(userId);
        return ResponseEntity.ok(weapons);
    }
    
    @GetMapping("/weapons")
    public List<Weapon> getWeaponsByCategory(@RequestParam String category) {
    	return adminWeaponService.getWeaponsByCategory(category);
    }
    
}