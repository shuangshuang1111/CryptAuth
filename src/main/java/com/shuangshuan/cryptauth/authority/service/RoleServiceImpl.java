package com.shuangshuan.cryptauth.authority.service;

import com.shuangshuan.cryptauth.authority.entity.Role;
import com.shuangshuan.cryptauth.authority.repository.RolePermissionRepository;
import com.shuangshuan.cryptauth.authority.repository.RoleRepository;
import com.shuangshuan.cryptauth.authority.response.RoleWithPermissionsResponse;
import com.shuangshuan.cryptauth.security.util.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private PermissionService permissionService;

    // 根据ID查询角色及其权限
    public Optional<RoleWithPermissionsResponse> findRoleWithPermissionsById(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            // 查询该角色的权限
            List<Integer> permIds = rolePermissionRepository.findPermissionIdsByRoleId(id);
            // 封装成RoleWithPermissionsResponse返回
            RoleWithPermissionsResponse response = new RoleWithPermissionsResponse();
            response.setId(role.get().getId());
            response.setName(role.get().getName());
            response.setDescription(role.get().getDescription());
            response.setState(role.get().getState());
            response.setPermIds(permIds);
            return Optional.of(response);
        }
        return Optional.empty();
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public boolean assignPermissionsToRole(Integer roleId, List<Integer> permIds) {
        // 假设存在一个方法，关联角色和权限到数据库
        return permissionService.assignPermissionsToRole(roleId, permIds);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional<Role> findRoleById(Integer id) {
        return roleRepository.findById(id);
    }

    // 获取所有启用的角色
    public List<Role> findAllEnabledRoles() {
        return roleRepository.findByState(1); // 状态为 1 表示启用
    }

    // 保存角色（创建或更新）
    public Role save(Role role) {
        String userName = SecurityUtils.getCurrentUsername();
        if (role != null && role.getId() == null) {
            role.setCreatedBy(userName);
            role.setDeleted(0);
        }
        role.setUpdatedBy(userName);
        return roleRepository.save(role);
    }

    // 判断角色是否存在
    public boolean existsById(Integer id) {
        return roleRepository.existsById(id);
    }

    // 根据 ID 删除角色
    @Transactional
    public void deleteById(Integer id) {
        roleRepository.softDeleteById(id);

    }
}
