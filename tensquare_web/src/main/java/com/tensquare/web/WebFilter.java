package com.tensquare.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**前台网关过滤器
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Component
public class WebFilter extends ZuulFilter {

    /**
     * 过滤器的类型：取值有4个
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行时机：取值越小，执行时间点越靠前
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器是否执行
     *  false表示不执行
     *  true表示执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的核心方法，要增强的部分都写在此处
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //1.获取请求上下文对象RequestContext
        RequestContext requestContext = RequestContext.getCurrentContext();
        //2.获取HttServletRequest对象
        HttpServletRequest request = requestContext.getRequest();
        //3.获取名称为Authorization的请求消息头
        String header = request.getHeader("Authorization");
        //4.把消息头添加到指定地方，为目的地微服务提供消息头
        requestContext.addZuulRequestHeader("Authorization",header);
        //5.放行：当范围值为null的时候就表示放行
        return null;
    }
}
