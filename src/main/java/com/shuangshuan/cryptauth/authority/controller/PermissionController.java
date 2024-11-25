package com.shuangshuan.cryptauth.authority.controller;

import com.shuangshuan.cryptauth.authority.entity.Permission;
import com.shuangshuan.cryptauth.authority.request.PermissionRequest;
import com.shuangshuan.cryptauth.authority.service.PermissionService;
import com.shuangshuan.cryptauth.common.BusinessResponseCode;
import com.shuangshuan.cryptauth.common.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // 获取权限点列表
    @GetMapping
    public ResponseResult<List<Permission>> getPermissionsList() {
        List<Permission> permissions = permissionService.findAllPermissions();
        return ResponseResult.success(permissions, BusinessResponseCode.PERMISSION_LIST_FETCHED_SUCCESS.getMessage());
    }

    // 根据权限ID获取权限点详情
    @GetMapping("/{id}")
    public ResponseResult<Permission> getPermissionById(@PathVariable Integer id) {
        return permissionService.findPermissionById(id)
                .map(permission -> ResponseResult.success(permission, BusinessResponseCode.PERMISSION_FETCHED_SUCCESS.getMessage()))
                .orElseGet(() -> ResponseResult.error(BusinessResponseCode.PERMISSION_NOT_FOUND));
    }

    // 创建新权限点
    @PostMapping
    public ResponseResult<Integer> createPermission(@RequestBody PermissionRequest permissionRequest) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionRequest, permission);
        Permission savedPermission = permissionService.save(permission);
        return savedPermission != null
                ? ResponseResult.success(savedPermission.getId(), BusinessResponseCode.PERMISSION_CREATED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.PERMISSION_CREATION_FAILED);
    }

    // 更新权限点信息
    @PutMapping("/{id}")
    public ResponseResult<Permission> updatePermission(@PathVariable Integer id, @RequestBody PermissionRequest permissionRequest) {
        if (!permissionService.existsById(id)) {
            return ResponseResult.error(BusinessResponseCode.PERMISSION_NOT_FOUND);
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionRequest, permission);
        permission.setId(id); // 确保传入的 ID 会更新原有权限
        Permission updatedPermission = permissionService.save(permission);
        return updatedPermission != null
                ? ResponseResult.success(updatedPermission, BusinessResponseCode.PERMISSION_UPDATED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.PERMISSION_UPDATE_FAILED);
    }

    // 删除权限点
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deletePermission(@PathVariable Integer id) {
        if (!permissionService.existsById(id)) {
            return ResponseResult.error(BusinessResponseCode.PERMISSION_NOT_FOUND);
        }
        boolean isDeleted = permissionService.deleteById(id);
        return isDeleted
                ? ResponseResult.success(null, BusinessResponseCode.PERMISSION_DELETED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.PERMISSION_DELETE_FAILED);
    }
}

