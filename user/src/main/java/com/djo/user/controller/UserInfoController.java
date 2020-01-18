package com.djo.user.controller;

import com.djo.user.dataObject.UserInfo;
import com.djo.user.service.UserService;
import com.djo.user.utils.CookieUtil;
import com.djo.user.utils.ResultVOUtil;
import com.djo.user.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class UserInfoController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 买家登录 此处需要向cookie中写入值 所以使用HttpServletResponse
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {
        //1.openid和数据库里的数据进行匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null){
            return ResultVOUtil.error("用户不存在");
        }

        //2.判断角色 1是买家 2是卖家
        if(userInfo.getRole() != 1) {
            return ResultVOUtil.error("用户角色不匹配");
        }

        //3.cookie里面设置openid=abc
        CookieUtil.set(response, "openid", openid, 7200);
        return ResultVOUtil.success();
    }

    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        //判断是否已经登录
        Cookie cookie = CookieUtil.get(request, "token");
        if (cookie != null && !StringUtils.isEmpty(redisTemplate.opsForValue().get("token_" + cookie.getValue()))){
            return ResultVOUtil.success();
        }

        //1.openid和数据库里的数据进行匹配
        UserInfo userInfo = userService.findByOpenid(openid);
        if (userInfo == null){
            return ResultVOUtil.error("用户不存在");
        }

        //2.判断角色 1是买家 2是卖家
        if(userInfo.getRole() != 2) {
            return ResultVOUtil.error("用户角色不匹配");
        }

        //3.redis设置key=UUID value=xyz
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("token_" + token, openid, 7200, TimeUnit.SECONDS);

        //4.cookie里面设置openid=xyz
        CookieUtil.set(response, "token", token, 7200);
        return ResultVOUtil.success();
    }
}
