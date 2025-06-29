package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "weapon")
public class Weapon {

    @Id
    @Column(name = "weapon_id", length = 50)
    private String weaponId;  // since your DB weapon_id is VARCHAR

    @Column(name = "weapon_name", nullable = false)
    private String weaponName;

    @Column(nullable = false)
    private String category;  // Army / Navy / Airforce

    // Getters & Setters
    public String getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(String weaponId) {
        this.weaponId = weaponId;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}