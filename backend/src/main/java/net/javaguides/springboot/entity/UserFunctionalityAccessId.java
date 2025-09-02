package net.javaguides.springboot.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFunctionalityAccessId implements Serializable {

	private Integer userId;
    private String functionality;

    public UserFunctionalityAccessId() {}

    public UserFunctionalityAccessId(Integer userId, String functionality) {
        this.userId = userId;
        this.functionality = functionality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFunctionalityAccessId)) return false;
        UserFunctionalityAccessId that = (UserFunctionalityAccessId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(functionality, that.functionality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, functionality);
    }
}