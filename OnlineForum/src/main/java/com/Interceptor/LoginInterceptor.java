package com.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

// 拦截器：负责检查是否登录
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取 Session
        HttpSession session = request.getSession();

        // 2. 检查是否有 currentUser
        if (session.getAttribute("currentUser") == null) {
            // 3. 如果没登录，重定向到登录页（注意：这里要加上 ContextPath）
            response.sendRedirect("/login");
            return false; // 返回 false 表示拦截，不再执行后面的 Controller
        }

        // 4. 如果登录了，放行
        return true;
    }
}