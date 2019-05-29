package com.liucan.boot.service;

import com.liucan.boot.ApplicationTests;
import com.liucan.boot.service.db.UserInfoMybatisService;
import com.liucan.boot.web.common.CommonResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liucan
 * @date 2018/7/8
 * @brief
 */
public class UserInfoMybatisServiceTest extends ApplicationTests {
    @Autowired
    private UserInfoMybatisService userInfoMybatisService;

    @Test
    public void getUserPhone() {
        CommonResponse commonResponse = userInfoMybatisService.getUserPhone(1231313);
    }
}