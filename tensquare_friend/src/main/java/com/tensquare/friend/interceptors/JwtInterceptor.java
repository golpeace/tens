package com.tensquare.friend.interceptors;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权拦截器：在访问控制器方法之前要验证权限
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 验证token
     *  取出消息头，判断是用户还是管理员，存入请求域中，放行，把权限判定的事情交给控制器方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.取出消息头
        String header = request.getHeader("Authorization");
        //2.判断消息头是否存在
        if(!StringUtils.isEmpty(header) && header.startsWith("Bearer ")){
            //3.取出token
            String token = header.split(" ")[1];
            //4.使用jwt验证token，得到claims
            Claims claims = jwtUtil.parseJWT(token);
            //5.判断是用户还是管理员
            String roles = (String)claims.get("roles");
            if("admin".equals(roles)){
                request.setAttribute("admin_claims",claims);
            }else if("user".equals(roles)){
                request.setAttribute("user_claims",claims);
            }
        }
        //不管有没有权限都放行
        return true;
    }
}
