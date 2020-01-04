package com.tensquare.manager;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Component
public class ManagerFilter extends ZuulFilter {

    /**
     * 执行时机
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 核心业务方法
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //1.获取请求上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2.获取请求对象
        HttpServletRequest request = requestContext.getRequest();

        //当跨域访问，共有两次请求。第一次：预请求。第二次是正式请求。 预请求时是不携带任何信息的。它的特征是请求方式是OPTIONS
        String method = request.getMethod();
        if(method.equalsIgnoreCase("OPTIONS")){
            //表示跨域访问的预请求，此时不会有消息头
            return null;
        }


        //如果请求URL是管理员登录，此时无须权限  /admin/login
        //取出请求的URL
        String url = request.getRequestURL().toString();
        //判断url是否是管理员登录操作
        if(url.indexOf("/admin/login")>0){
            //管理员登录操作：此时直接放行
            System.out.println("管理员登录");
            return null;
        }

        //3.获取请求消息头
        String header = request.getHeader("Authorization");
        //4.验证消息头是否符合规则
        if(!StringUtils.isEmpty(header) && header.startsWith("Bearer ")){
            requestContext.addZuulRequestHeader("Authorization",header);
            return null;
        }
        //5.不符合条件，通过过滤器就执行响应
        requestContext.setSendZuulResponse(false);//true的时候继续执行，false的时候终止执行
        requestContext.setResponseStatusCode(401);//设置响应状态码
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setResponseBody("{false,20004,'没有权限'}");//设置响应正文
        //放行
        return null;
    }
}
