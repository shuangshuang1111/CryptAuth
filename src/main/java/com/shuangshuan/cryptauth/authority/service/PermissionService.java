package com.shuangshuan.cryptauth.authority.service;

import java.util.List;

public interface PermissionService {

    Boolean assignPermissionsToRole(Integer roleId, List<Integer> permIds);
}
