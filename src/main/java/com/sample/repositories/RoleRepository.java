/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories;

import io.micronaut.data.annotation.Repository;
import java.util.Optional;
import com.sample.repositories.entities.Role;

@Repository
public interface RoleRepository extends GenericRepository<Role, Long> {
    Optional<Role> findFirstByCode(String code);
}
