package com.djo.user.service;

import com.djo.user.dataObject.UserInfo;

public interface UserService {
    UserInfo findByOpenid(String openid);
}
