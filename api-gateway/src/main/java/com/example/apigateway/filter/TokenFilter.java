package com.example.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 这就相当于是一个前置的过滤器 用来做参数校验
 */
@Component
public class TokenFilter extends ZuulFilter {
    //参数校验
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    //顺序
    //将我们自定义的过滤器放到PRE_DECORATION_FILTER_ORDER前面 数字越小代表越先执行 所以此处是减1
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    //是否应该拦截 这里面可以根据不同的条件自定义处理
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //我们要真正实现的逻辑
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //从url参数里获取 也可以从cookie header中获取
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)){
//            requestContext.setSendZuulResponse(false);
//            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
