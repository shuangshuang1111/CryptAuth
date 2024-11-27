package com.shuangshuan.cryptauth.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Aspect
@Component
public class PermissionAspect {


    @Before("@annotation(requestMapping)") // 拦截带有 @RequestMapping 或其他映射注解的方法
    public void checkPermission(RequestMapping requestMapping) throws AccessDeniedException {
        // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("User is not authenticated");
        }

        // 获取请求的路径模式
        String[] paths = requestMapping.value();
        if (paths.length == 0) {
            throw new AccessDeniedException("No path mapped for the request");
        }

        // 获取第一个映射路径（通常一个接口只有一个路径）
        String requiredPath = paths[0];

        // 获取用户的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 检查用户是否具有访问该路径的权限
        boolean hasPermission = authorities.stream()
                .map(GrantedAuthority::getAuthority)  // 获取用户的所有权限
                .anyMatch(permission -> permission.equals(requiredPath));  // 判断用户是否有权限

        if (!hasPermission) {
            throw new AccessDeniedException("User does not have permission to access this resource");
        }
    }
}
