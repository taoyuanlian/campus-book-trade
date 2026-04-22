package com.whxy.campusbooktrade2.config;

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
public class AdminInterceptor implements HandlerInterceptor {
    // 注入JwtUtil Bean（关键修复点）
    @Autowired
    private JwtUtil jwtUtil;
    // 引入JSON工具，统一返回格式
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        // 1. 校验token是否存在
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(R.fail("请先登录")));
            writer.close();
            response.setStatus(401);
            return false;
        }

        try {
            // 2. 解析token获取角色（修复：用注入的jwtUtil调用实例方法）
            String role = jwtUtil.getRoleFromToken(token);
            // 3. 校验是否为管理员
            if (!"admin".equals(role)) {
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(objectMapper.writeValueAsString(R.fail("权限不足！仅管理员可访问")));
                writer.close();
                response.setStatus(403);
                return false;
            }
            return true;
        } catch (Exception e) {
            // token失效/解析失败
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(R.fail("Token失效，请重新登录")));
            writer.close();
            response.setStatus(401);
            return false;
        }
    }
}