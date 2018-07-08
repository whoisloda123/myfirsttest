package com.liucan.controller;

import com.liucan.Service.UserInfoJdbcTemplate;
import com.liucan.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liucan
 * @Date: 2018/7/6
 * @Description:
 */
@RestController
@RequestMapping("/bootlearn")
public class MyRestController {
    @Autowired
    private UserInfoJdbcTemplate userInfoJdbcTemplate;

    @RequestMapping("/find_name")
    public CommonResponse findName(@RequestParam("user_id") Integer userId) {
        return CommonResponse.ok(userInfoJdbcTemplate.queryUser(userId));
    }
}

