package com.djo.user.repository;

import com.djo.user.dataObject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByOpenid(String openid);
}
