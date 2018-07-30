package com.liucan.controller;

import com.alibaba.fastjson.JSONObject;
import com.liucan.common.redis.JedisCluster;
import com.liucan.common.response.CommonResponse;
import com.liucan.domain.Person;
import com.liucan.service.UserInfoJdbcTemplate;
import com.liucan.service.UserInfoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
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
    @Autowired
    private KafkaTemplate kafkaTemplate;

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
        jedisCluster.set(key, CommonResponse.ok(value));
        return CommonResponse.ok();
    }

    @GetMapping("/redis_set")
    public Object redisSet(@RequestParam("key") String key) {
        return jedisCluster.get(key);
    }

    @PostMapping("kafka")
    public CommonResponse kafka() {
        Person person = new Person();
        person.setAge(12);
        person.setName("liucan");
        person.setAddress("重庆市");
        Object object = kafkaTemplate.send(kafkaTemplate.getDefaultTopic(), JSONObject.toJSONString(person));
        return CommonResponse.ok(object);

    }
}

