package com.liucan.controller;

import com.liucan.common.redis.JedisCluster;
import com.liucan.common.response.CommonResponse;
import com.liucan.service.UserInfoJdbcTemplate;
import com.liucan.service.UserInfoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    private JedisCluster jedisCluster;

    @Cacheable(value = "userInfo")
    @GetMapping("/find_name")
    public String findName(@RequestParam("user_id") Integer userId) {
        return userInfoJdbcTemplate.queryUser(userId);
    }

    @GetMapping("/find_name1")
    public CommonResponse findName1(@RequestParam("user_id") Integer userId) {
        return userInfoMybatis.getName(userId);
    }

    @Cacheable(value = "userInfo")
    @GetMapping("/find_phone")
    public CommonResponse findPhone(@RequestParam("user_id") Integer userId) {
        return userInfoMybatis.getUserPhone(userId);
    }

    @PostMapping("/redis_set")
    public CommonResponse redisSet(@RequestParam("key") String key,
                                   @RequestParam("value") String value) {
        jedisCluster.set(key, value);
        return CommonResponse.ok();
    }

    @GetMapping("/redis_set")
    public CommonResponse redisSet(@RequestParam("key") String key) {
        Object value = jedisCluster.get(key);
        return CommonResponse.ok(value);
    }
}

