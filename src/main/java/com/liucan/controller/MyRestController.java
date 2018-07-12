package com.liucan.controller;

import com.liucan.common.redis.Ledis;
import com.liucan.common.response.CommonResponse;
import com.liucan.service.UserInfoJdbcTemplate;
import com.liucan.service.UserInfoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private UserInfoMybatis userInfoMybatis;
    @Autowired
    private Ledis ledis;

    @RequestMapping("/find_name")
    public CommonResponse findName(@RequestParam("user_id") Integer userId) {
        return CommonResponse.ok(userInfoJdbcTemplate.queryUser(userId));
    }

    @RequestMapping("/find_name1")
    public CommonResponse findName1(@RequestParam("user_id") Integer userId) {
        return userInfoMybatis.getName(userId);
    }

    @RequestMapping("/find_phone")
    public CommonResponse findPhone(@RequestParam("user_id") Integer userId) {
        return userInfoMybatis.getUserPhone(userId);
    }

    @PostMapping("/redis_set")
    public CommonResponse redisSet(@RequestParam("key") String key,
                                   @RequestParam("value") String value) {
        ledis.set(key, value);
        return CommonResponse.ok();
    }

    @GetMapping("/redis_set")
    public CommonResponse redisSet(@RequestParam("key") String key) {
        Object value = ledis.get(key);
        return CommonResponse.ok(value);
    }
}

