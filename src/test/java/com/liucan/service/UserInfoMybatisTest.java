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
public class UserInfoMybatisTest extends BootApplicationTests {
    @Autowired
    private UserInfoMybatis userInfoMybatis;

    @Test
    public void getUserPhone() {
        CommonResponse commonResponse = userInfoMybatis.getUserPhone(1231313);
    }
}