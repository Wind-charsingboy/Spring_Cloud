package com.djo.user.service.impl;

import com.djo.user.dataObject.UserInfo;
import com.djo.user.repository.UserInfoRepository;
import com.djo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
