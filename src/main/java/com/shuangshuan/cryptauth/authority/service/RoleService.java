package com.shuangshuan.cryptauth.authority.service;

import com.shuangshuan.cryptauth.authority.entity.Role;
import com.shuangshuan.cryptauth.authority.response.RoleWithPermissionsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAllEnabledRoles();

    Role save(Role role);

    boolean existsById(Integer id);

    boolean deleteById(Integer id);

    Optional<RoleWithPermissionsResponse> findRoleWithPermissionsById(Integer id);

    Page<Role> findAllRoles(Pageable pageable);

    boolean assignPermissionsToRole(Integer roleId, List<Integer> permIds);
}
