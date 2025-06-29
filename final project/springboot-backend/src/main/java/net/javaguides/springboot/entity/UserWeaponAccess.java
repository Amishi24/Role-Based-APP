package net.javaguides.springboot.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_weapon_access")
@IdClass(UserWeaponAccessId.class)  // composite key
public class UserWeaponAccess {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RolebasedUser user;

    @Id
    @ManyToOne
    @JoinColumn(name = "weapon_id", nullable = false)
    private Weapon weapon;

    // getters and setters
    public RolebasedUser getUser() {
        return user;
    }

    public void setUser(RolebasedUser user) {
        this.user = user;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}