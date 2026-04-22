package com.whxy.campusbooktrade2.config; // 注意：你把Interceptor放在了config包下，需和WebConfig的注入对应

import com.whxy.campusbooktrade2.common.R;
import com.whxy.campusbooktrade2.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    // 引入Spring内置JSON工具
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ========== 新增：放行跨域OPTIONS预检请求（关键！避免前端请求被拦截） ==========
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(R.fail("未登录，请先登录")));
            writer.close();
            response.setStatus(401);
            return false;
        }

        try {
            Long userId = jwtUtil.getUserIdFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            request.setAttribute("userId", userId);
            request.setAttribute("role", role); // 核心：给AdminController传role
            return true;
        } catch (Exception e) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(R.fail("Token失效，请重新登录")));
            writer.close();
            response.setStatus(401);
            return false;
        }
    }
}