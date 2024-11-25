package com.shuangshuan.cryptauth.authority.controller;

import com.shuangshuan.cryptauth.authority.entity.Role;
import com.shuangshuan.cryptauth.authority.request.AssignPremRequest;
import com.shuangshuan.cryptauth.authority.request.RoleRequest;
import com.shuangshuan.cryptauth.authority.response.RoleWithPermissionsResponse;
import com.shuangshuan.cryptauth.authority.service.RoleService;
import com.shuangshuan.cryptauth.common.BusinessResponseCode;
import com.shuangshuan.cryptauth.common.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // 获取所有启用的角色
    @GetMapping("/list/enabled")
    public ResponseResult<List<Role>> getAllEnabledRoles() {
        List<Role> roles = roleService.findAllEnabledRoles();
        return ResponseResult.success(roles, BusinessResponseCode.ROLE_LIST_FETCHED_SUCCESS.getMessage());
    }

    // 根据ID获取角色及其权限
    @GetMapping("/{id}")
    public ResponseResult<RoleWithPermissionsResponse> getRoleById(@PathVariable Integer id) {
        return roleService.findRoleWithPermissionsById(id)
                .map(roleWithPermissions -> ResponseResult.success(roleWithPermissions, BusinessResponseCode.ROLE_FETCHED_SUCCESS.getMessage()))
                .orElseGet(() -> ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND));
    }

    // 创建新角色
    @PostMapping
    public ResponseResult<Integer> createRole(@RequestBody RoleRequest roleRequest) {
        Role role = new Role();
        BeanUtils.copyProperties(roleRequest, role);
        Role savedRole = roleService.save(role);
        return savedRole != null
                ? ResponseResult.success(savedRole.getId(), BusinessResponseCode.ROLE_CREATED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.ROLE_CREATION_FAILED);
    }

    // 更新角色信息
    @PutMapping("/{id}")
    public ResponseResult<Role> updateRole(@PathVariable Integer id, @RequestBody RoleRequest roleRequest) {
        if (!roleService.existsById(id)) {
            return ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleRequest, role);
        role.setId(id);  // 确保传入的 ID 会更新原有角色
        Role updatedRole = roleService.save(role);
        return updatedRole != null
                ? ResponseResult.success(updatedRole, BusinessResponseCode.ROLE_UPDATED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.ROLE_UPDATE_FAILED);
    }

    // 删除角色
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteRole(@PathVariable Integer id) {
        if (!roleService.existsById(id)) {
            return ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND);
        }
        boolean isDeleted = roleService.deleteById(id);
        return isDeleted
                ? ResponseResult.success(null, BusinessResponseCode.ROLE_DELETED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.ROLE_DELETE_FAILED);
    }


    // 分页查询角色列表
    @GetMapping
    public ResponseResult<Page<Role>> getRolesPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> rolesPage = roleService.findAllRoles(pageable);
        return ResponseResult.success(rolesPage, BusinessResponseCode.ROLE_LIST_FETCHED_SUCCESS.getMessage());
    }

    // 分配权限给角色
    @PostMapping("/assignPrem")
    public ResponseResult<Void> assignPermissionsToRole(
            @RequestBody AssignPremRequest assignPremRequest) {
        if (!roleService.existsById(assignPremRequest.getId())) {
            return ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND);
        }
        boolean success = roleService.assignPermissionsToRole(assignPremRequest.getId(), assignPremRequest.getPermIds());
        return success
                ? ResponseResult.success(null, BusinessResponseCode.ROLE_PERMISSIONS_ASSIGNED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.ROLE_PERMISSION_ASSIGN_FAILED);
    }
}
