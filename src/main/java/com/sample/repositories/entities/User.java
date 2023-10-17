/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.sample.repositories.entities.audit.TimeStampEntity;
import org.hibernate.annotations.*;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE user SET status = 'DELETED' WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "status <> 'DELETED'")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@DynamicInsert
public class User extends TimeStampEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, length = 150, nullable = false)
    private String email;

    private String password;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean shouldChangePassword;

    private String vkey;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
