package com.shuangshuan.cryptauth.authority.controller;

import com.shuangshuan.cryptauth.authority.entity.Role;
import com.shuangshuan.cryptauth.authority.request.AssignPremRequest;
import com.shuangshuan.cryptauth.authority.request.RoleRequest;
import com.shuangshuan.cryptauth.authority.response.RoleWithPermissionsResponse;
import com.shuangshuan.cryptauth.authority.service.RoleService;
import com.shuangshuan.cryptauth.common.BusinessResponseCode;
import com.shuangshuan.cryptauth.common.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "RoleController", description = "Role management operations")
@RestController
@RequestMapping("/sys/role")
@Validated  // 启用校验
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    // 获取所有启用的角色
    @Operation(summary = "Get all enabled roles", description = "Fetch all enabled roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles fetched successfully")
    })
    @GetMapping("/list/enabled")
    public ResponseResult<List<Role>> getAllEnabledRoles() {
        logger.info("Fetching all enabled roles...");
        List<Role> roles = roleService.findAllEnabledRoles();
        logger.info("Fetched {} enabled roles", roles.size());
        return ResponseResult.success(roles, BusinessResponseCode.ROLE_LIST_FETCHED_SUCCESS.getMessage());
    }

    // 根据ID获取角色及其权限
    @Operation(summary = "Get role with permissions", description = "Fetch role and its permissions by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role fetched successfully"),
            @ApiResponse(responseCode = "1001", description = "Role not found")
    })
    @GetMapping("/{id}")
    public ResponseResult<RoleWithPermissionsResponse> getRoleById(
            @Parameter(description = "ID of the role to fetch") @PathVariable Integer id) {
        logger.info("Fetching role with ID: {}", id);
        return roleService.findRoleWithPermissionsById(id)
                .map(roleWithPermissions -> {
                    logger.info("Fetched role with permissions: {}", roleWithPermissions);
                    return ResponseResult.success(roleWithPermissions, BusinessResponseCode.ROLE_FETCHED_SUCCESS.getMessage());
                })
                .orElseGet(() -> {
                    logger.warn("Role with ID: {} not found", id);
                    return ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND);
                });
    }

    // 创建新角色
    @Operation(summary = "Create a new role", description = "Create a new role with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role created successfully"),
            @ApiResponse(responseCode = "1002", description = "Failed to create role")
    })
    @PostMapping
    public ResponseResult<Integer> createRole(@Parameter(description = "Role details to create")
                                              @RequestBody @Valid RoleRequest roleRequest) {
        logger.info("Creating new role with data: {}", roleRequest);
        Role role = new Role();
        BeanUtils.copyProperties(roleRequest, role);
        Role savedRole = roleService.save(role);
        if (savedRole != null) {
            logger.info("Created role with ID: {}", savedRole.getId());
            return ResponseResult.success(savedRole.getId(), BusinessResponseCode.ROLE_CREATED_SUCCESS.getMessage());
        } else {
            logger.error("Failed to create role");
            return ResponseResult.error(BusinessResponseCode.ROLE_CREATION_FAILED);
        }
    }

    // 更新角色信息
    @Operation(summary = "Update role", description = "Update an existing role with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "1001", description = "Role not found"),
            @ApiResponse(responseCode = "1003", description = "Failed to update role")
    })
    @PutMapping("/{id}")
    public ResponseResult<Role> updateRole(
            @Parameter(description = "ID of the role to update") @PathVariable Integer id,
            @Parameter(description = "Role details to update") @RequestBody @Valid RoleRequest roleRequest) {
        logger.info("Updating role with ID: {} and data: {}", id, roleRequest);
        if (!roleService.existsById(id)) {
            logger.warn("Role with ID: {} not found for update", id);
            return ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleRequest, role);
        role.setId(id); // Ensure the ID is set to update the existing role
        Role updatedRole = roleService.save(role);
        if (updatedRole != null) {
            logger.info("Updated role with ID: {}", updatedRole.getId());
            return ResponseResult.success(updatedRole, BusinessResponseCode.ROLE_UPDATED_SUCCESS.getMessage());
        } else {
            logger.error("Failed to update role with ID: {}", id);
            return ResponseResult.error(BusinessResponseCode.ROLE_UPDATE_FAILED);
        }
    }

    // 删除角色
    @Operation(summary = "Delete role", description = "Delete a role by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "1001", description = "Role not found"),
            @ApiResponse(responseCode = "1004", description = "Failed to delete role")
    })
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteRole(@Parameter(description = "ID of the role to delete") @PathVariable Integer id) {
        logger.info("Deleting role with ID: {}", id);
        if (!roleService.existsById(id)) {
            logger.warn("Role with ID: {} not found for deletion", id);
            return ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND);
        }
        boolean isDeleted = roleService.deleteById(id);
        if (isDeleted) {
            logger.info("Successfully deleted role with ID: {}", id);
            return ResponseResult.success(null, BusinessResponseCode.ROLE_DELETED_SUCCESS.getMessage());
        } else {
            logger.error("Failed to delete role with ID: {}", id);
            return ResponseResult.error(BusinessResponseCode.ROLE_DELETE_FAILED);
        }
    }

    // 分页查询角色列表
    @Operation(summary = "Get roles with pagination", description = "Fetch roles in a paginated manner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles fetched successfully")
    })
    @GetMapping
    public ResponseResult<Page<Role>> getRolesPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        logger.info("Fetching roles with pagination, page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> rolesPage = roleService.findAllRoles(pageable);
        logger.info("Fetched {} roles in page {} with size {}", rolesPage.getTotalElements(), page, size);
        return ResponseResult.success(rolesPage, BusinessResponseCode.ROLE_LIST_FETCHED_SUCCESS.getMessage());
    }

    // 分配权限给角色
    @Operation(summary = "Assign permissions to role", description = "Assign multiple permissions to a specific role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissions assigned successfully"),
            @ApiResponse(responseCode = "1001", description = "Role not found")
    })
    @PostMapping("/assignPrem")
    public ResponseResult<Void> assignPermissionsToRole(
            @RequestBody @Valid AssignPremRequest assignPremRequest) {
        logger.info("Assigning permissions to role with ID: {} and permissions: {}", assignPremRequest.getId(), assignPremRequest.getPermIds());
        if (!roleService.existsById(assignPremRequest.getId())) {
            logger.warn("Role with ID: {} not found for permission assignment", assignPremRequest.getId());
            return ResponseResult.error(BusinessResponseCode.ROLE_NOT_FOUND);
        }
        boolean success = roleService.assignPermissionsToRole(assignPremRequest.getId(), assignPremRequest.getPermIds());
        return success
                ? ResponseResult.success(null, BusinessResponseCode.ROLE_PERMISSIONS_ASSIGNED_SUCCESS.getMessage())
                : ResponseResult.error(BusinessResponseCode.ROLE_PERMISSION_ASSIGN_FAILED);
    }
}
