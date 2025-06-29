package net.javaguides.springboot.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserWeaponAccessId implements Serializable {

    private Integer user;       // type matches RolebasedUser id
    private String weapon;   // type matches Weapon id (VARCHAR)

    private static final long serialVersionUID = 1L;
    
    // Parameterized constructor
    public UserWeaponAccessId(Integer user, String weapon) {
        this.user = user;
        this.weapon = weapon;
    }

    // Default constructor
    public UserWeaponAccessId() {}

    // hashCode & equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWeaponAccessId)) return false;
        UserWeaponAccessId that = (UserWeaponAccessId) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(weapon, that.weapon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, weapon);
    }
}