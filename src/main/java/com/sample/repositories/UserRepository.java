/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories;

import com.sample.models.YearStats;
import com.sample.utils.enumerations.EntityStatus;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import java.util.List;
import java.util.Optional;

import com.sample.repositories.entities.Role;
import com.sample.repositories.entities.User;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByVkey(String vkey);

    List<User> findByEmailOrFirstNameContainsOrLastNameContains(
            String email, String firstName, String lastName);

    @Query(
            value = "SELECT u FROM User u JOIN u.roles r WHERE r=:role ",
            countQuery = "SELECT count(r.id) FROM User u JOIN u.roles r WHERE r=:role ")
    Page<User> findAllByRole(Role role, Pageable pageable);

    @Query(
            value =
                    "SELECT NEW com.sample.models.YearStats(month(ac.createDate),count(ac))"
                            + " FROM User ac WHERE year(ac.createDate)=:year GROUP BY"
                            + " month(ac.createDate) ")
    List<YearStats> findCountUsersMonthByYear(Integer year);

    long countByStatus(EntityStatus entityStatus);
}
