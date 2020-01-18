package com.example.apigateway.filter;

import com.example.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限拦截 区分卖家和买家
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        /**
         * 对三个不同的api做不同的处理
         * /order/cerate 只能买家访问(cookie里有openid)
         * /order/finish 只能卖家访问(cookie里有token 并且对应的redis中有值)
         * /product/list 都可以访问
         */
        //此处的第一个order是因为所有的请求都是先到达Zuul 再通过网关服务分发下去的 所以要带上转发的那个服务的服务名字
        if ("/order/order/cerate".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, "openid");
            if (cookie == null || cookie.getValue() == null){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        if ("/order/order/finish".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, "token");
            if (cookie == null || cookie.getValue() == null){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }


        return null;
    }
}
