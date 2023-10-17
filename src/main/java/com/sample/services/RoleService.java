/* Mohammed Amine AYACHE (C)2022 */
package com.sample.services;

import java.util.List;
import com.sample.repositories.entities.Role;

public interface RoleService {
    Role getRoleById(Long id);

    Role getRoleByCode(String code);

    List<Role> findAllRoles();

    Role updateRole(Role role);

    void deleteRoleById(Long id);

    void deleteRoleByCode(String code);

    Role addRole(Role role);
}
