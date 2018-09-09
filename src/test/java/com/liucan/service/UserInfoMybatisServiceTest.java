package com.liucan.service;

import com.liucan.BootApplicationTests;
import com.liucan.common.response.CommonResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liucan
 * @date 2018/7/8
 * @brief
 */
public class UserInfoMybatisServiceTest extends BootApplicationTests {
    @Autowired
    private UserInfoMybatisService userInfoMybatisService;

    @Test
    public void getUserPhone() {
        CommonResponse commonResponse = userInfoMybatisService.getUserPhone(1231313);
    }
}