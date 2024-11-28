package com.shuangshuan.cryptauth.security.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    /**
     * 获取当前登录用户的用户名
     *
     * @return 当前登录用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
            return principal.toString();  // 处理可能是字符串类型的 principal
        }
        return null;  // 如果未认证用户，返回 null 或者抛出异常，依据具体需求
    }

    /**
     * 获取原始请求的 URI（如果是转发或包含时，会返回转发前的 URI）
     * 这个方法目前在postman里卖弄测试获取不到值  到前端再调用看看吧
     *
     * @param request HttpServletRequest 对象
     * @return 原始请求的 URI
     */
    public static String getOriginalRequestUri(HttpServletRequest request) {
        // 尝试从转发或包含的请求 URI 中获取原始 URI
        String originalUri = (String) request.getAttribute("javax.servlet.forward.request_uri");

        if (originalUri == null) {
            originalUri = (String) request.getAttribute("javax.servlet.include.request_uri");
        }

        // 如果没有转发或包含的 URI，则返回当前请求的 URI
        if (originalUri == null) {
            originalUri = request.getRequestURI();
        }

        return originalUri;
    }
}

