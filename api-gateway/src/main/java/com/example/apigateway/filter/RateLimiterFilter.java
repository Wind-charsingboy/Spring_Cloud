package com.example.apigateway.filter;

import com.example.apigateway.exception.RateLimmiterException;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 限流拦截器
 */
@Component
public class RateLimiterFilter extends ZuulFilter {
    //令牌桶算法guava已经为我们实现好了 参数就是每秒钟像令牌桶中放多少个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    //限流的优先级一定是最高的 FilterConstants中目前优先级最高的就是SERVLET_DETECTION_FILTER_ORDER 值是-3
    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取令牌的方法 这个无参方法的默认实现就是取一个令牌
        //没获取到令牌 直接抛出异常
        if (!RATE_LIMITER.tryAcquire()){
            throw new RateLimmiterException();
        }
        return null;
    }
}
