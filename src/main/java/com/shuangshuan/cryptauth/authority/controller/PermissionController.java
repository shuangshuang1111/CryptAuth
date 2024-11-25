package com.shuangshuan.cryptauth.authority.controller;

import com.shuangshuan.cryptauth.authority.entity.Permission;
import com.shuangshuan.cryptauth.authority.request.PermissionRequest;
import com.shuangshuan.cryptauth.authority.service.PermissionService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PermissionController", description = "Permission management operations")
@RestController
@RequestMapping("/sys/permission")
@Validated  // 开启 Spring 的 Validated 校验
public class PermissionController {

    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    @Operation(summary = "Get all permissions", description = "Fetch the list of all permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissions fetched successfully")
    })
    @GetMapping
    public ResponseResult<List<Permission>> getPermissionsList() {
        logger.info("Fetching all permissions...");
        List<Permission> permissions = permissionService.findAllPermissions();
        logger.info("Fetched {} permissions", permissions.size());
        return ResponseResult.success(permissions, BusinessResponseCode.PERMISSION_LIST_FETCHED_SUCCESS.getMessage());
    }

    @Operation(summary = "Get permission details", description = "Fetch the details of a specific permission by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission fetched successfully"),
            @ApiResponse(responseCode = "3004", description = "Permission not found")
    })
    @GetMapping("/{id}")
    public ResponseResult<Permission> getPermissionById(
            @Parameter(description = "ID of the permission to fetch") @PathVariable Integer id) {
        logger.info("Fetching permission with ID: {}", id);
        return permissionService.findPermissionById(id)
                .map(permission -> {
                    logger.info("Fetched permission: {}", permission);
                    return ResponseResult.success(permission, BusinessResponseCode.PERMISSION_FETCHED_SUCCESS.getMessage());
                })
                .orElseGet(() -> {
                    logger.warn("Permission with ID: {} not found", id);
                    return ResponseResult.error(BusinessResponseCode.PERMISSION_NOT_FOUND);
                });
    }

    @Operation(summary = "Create a new permission", description = "Create a new permission with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission created successfully"),
            @ApiResponse(responseCode = "3001", description = "Permission creation failed")
    })
    @PostMapping
    public ResponseResult<Integer> createPermission(
            @Parameter(description = "Permission details to create") @RequestBody @Valid PermissionRequest permissionRequest) {
        logger.info("Creating new permission with data: {}", permissionRequest);
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionRequest, permission);
        Permission savedPermission = permissionService.save(permission);
        if (savedPermission != null) {
            logger.info("Created permission with ID: {}", savedPermission.getId());
            return ResponseResult.success(savedPermission.getId(), BusinessResponseCode.PERMISSION_CREATED_SUCCESS.getMessage());
        } else {
            logger.error("Failed to create permission");
            return ResponseResult.error(BusinessResponseCode.PERMISSION_CREATION_FAILED);
        }
    }

    @Operation(summary = "Update permission", description = "Update an existing permission with the provided ID and details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
            @ApiResponse(responseCode = "3004", description = "Permission not found"),
            @ApiResponse(responseCode = "3002", description = "Permission update failed")
    })
    @PutMapping("/{id}")
    public ResponseResult<Permission> updatePermission(
            @Parameter(description = "ID of the permission to update") @PathVariable Integer id,
            @Parameter(description = "Permission details to update") @RequestBody @Valid PermissionRequest permissionRequest) {
        logger.info("Updating permission with ID: {} and data: {}", id, permissionRequest);
        if (!permissionService.existsById(id)) {
            logger.warn("Permission with ID: {} not found for update", id);
            return ResponseResult.error(BusinessResponseCode.PERMISSION_NOT_FOUND);
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionRequest, permission);
        permission.setId(id);  // Ensure the ID is set to update the existing permission
        Permission updatedPermission = permissionService.save(permission);
        if (updatedPermission != null) {
            logger.info("Updated permission with ID: {}", updatedPermission.getId());
            return ResponseResult.success(updatedPermission, BusinessResponseCode.PERMISSION_UPDATED_SUCCESS.getMessage());
        } else {
            logger.error("Failed to update permission with ID: {}", id);
            return ResponseResult.error(BusinessResponseCode.PERMISSION_UPDATE_FAILED);
        }
    }

    @Operation(summary = "Delete permission", description = "Delete a permission by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission deleted successfully"),
            @ApiResponse(responseCode = "3004", description = "Permission not found"),
            @ApiResponse(responseCode = "3003", description = "Permission deletion failed")
    })
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deletePermission(
            @Parameter(description = "ID of the permission to delete") @PathVariable Integer id) {
        logger.info("Deleting permission with ID: {}", id);
        if (!permissionService.existsById(id)) {
            logger.warn("Permission with ID: {} not found for deletion", id);
            return ResponseResult.error(BusinessResponseCode.PERMISSION_NOT_FOUND);
        }
        boolean isDeleted = permissionService.deleteById(id);
        if (isDeleted) {
            logger.info("Successfully deleted permission with ID: {}", id);
            return ResponseResult.success(null, BusinessResponseCode.PERMISSION_DELETED_SUCCESS.getMessage());
        } else {
            logger.error("Failed to delete permission with ID: {}", id);
            return ResponseResult.error(BusinessResponseCode.PERMISSION_DELETE_FAILED);
        }
    }
}
