/* Mohammed Amine AYACHE (C)2022 */
package com.sample.services.Imp;

import com.sample.services.RoleService;
import jakarta.inject.Singleton;
import java.util.List;
import lombok.RequiredArgsConstructor;
import com.sample.exceptions.EntityNotFoundException;
import com.sample.repositories.RoleRepository;
import com.sample.repositories.entities.Role;

@RequiredArgsConstructor
@Singleton
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("role_not_found");
                        });
    }

    @Override
    public Role getRoleByCode(String code) {
        return roleRepository
                .findFirstByCode(code)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("role_not_found");
                        });
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        Role role = getRoleById(id);
        roleRepository.delete(role);
    }

    @Override
    public void deleteRoleByCode(String code) {
        Role role = getRoleByCode(code);
        roleRepository.delete(role);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}
