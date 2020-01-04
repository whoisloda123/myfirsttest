package com.liucan.boot.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liucan.boot.framework.annotation.LoginCheck;
import com.liucan.boot.framework.annotation.UserId;
import com.liucan.boot.framework.config.CachingConfig;
import com.liucan.boot.mode.OrderQuery;
import com.liucan.boot.persist.mybatis.mapper.CommonUserMapper;
import com.liucan.boot.persist.mybatis.mapper.UserOrderMapper;
import com.liucan.boot.persist.mybatis.mode.UserOrder;
import com.liucan.boot.service.db.JooqService;
import com.liucan.boot.service.db.UserInfoJdbcTemplateService;
import com.liucan.boot.service.db.UserInfoMybatisService;
import com.liucan.boot.service.dubbo.BootDubboServiceImpl;
import com.liucan.boot.service.kafka.common.KafkaService;
import com.liucan.boot.service.redis.RedisPubSub;
import com.liucan.boot.service.redis.RedisTemplateService;
import com.liucan.boot.web.common.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: liucan
 * @Date: 2018/7/6
 */
@Slf4j
@RestController
@RequestMapping("bootlearn")
@AllArgsConstructor
public class MyRestController {
    private final UserInfoJdbcTemplateService userInfoJdbcTemplateService;
    private final UserInfoMybatisService userInfoMybatisService;
    private final StringRedisTemplate redisTemplate;
    private final BootDubboServiceImpl bootDubboService;
    private final RedisPubSub redisPubSub;
    private final RedisTemplateService redisTemplateService;
    private final KafkaService kafkaService;
    private final JooqService jooqService;
    private final CommonUserMapper commonUserMapper;
    private final UserOrderMapper userOrderMapper;

    @Cacheable(cacheNames = CachingConfig.ENTRY_TTL_1M, cacheManager = "redisCacheManager", keyGenerator = "keyGenerator")
    @GetMapping("find_name")
    public String findName(@RequestParam("user_id") Integer userId) {
        return userInfoJdbcTemplateService.queryUser(userId);
    }

    @GetMapping("queryOrder")
    public CommonResponse queryOrder(OrderQuery query) {
        //查询
        List<UserOrder> userOrders = userOrderMapper.selectList(Wrappers.<UserOrder>lambdaQuery()
                .in(CollectionUtils.isNotEmpty(query.getIds()), UserOrder::getId, query.getIds())
                .eq(query.getUserId() != null, UserOrder::getUserId, query.getUserId())
                .eq(StringUtils.isNotBlank(query.getAddress()), UserOrder::getAddress, query.getAddress())
                .eq(query.getPayType() != null, UserOrder::getPayType, query.getPayType())
                .le(query.getEndTime() != null, UserOrder::getCreateTime, query.getEndTime())
                .ge(query.getStartTime() != null, UserOrder::getCreateTime, query.getStartTime())
                .select(UserOrder::getUserId, UserOrder::getAddress));

        IPage<UserOrder> userOrderIPage1 = userOrderMapper.selectPageVo(new Page(1, 1), 2);

        IPage<UserOrder> userOrderIPage = userOrderMapper.selectPage(new Page<>(1, 1), null);
        //删除

        //更新
//        UserOrder userOrder = new UserOrder();
//        userOrder.setAddress("nsfsfsfsfs");
//        userOrder.setPrice(1323);
//        userOrderMapper.update(userOrder, Wrappers.<UserOrder>lambdaUpdate()
//                .set(UserOrder::getOrderId, null)
//                .eq(UserOrder::getUserId, 3));
//
//        userOrderMapper.update(null, Wrappers.<UserOrder>lambdaUpdate()
//                .set(UserOrder::getAddress, "123")
//                .set(UserOrder::getPrice, null)
//                .eq(UserOrder::getUserId, 3));
        //插入
//        UserOrder o = new UserOrder();
//        o.setUserId(9);
//        o.setAddress("sfsfsf");
//        userOrderMapper.insert(o);
        return CommonResponse.ok(query);
    }

    @Cacheable("userInfo")
    @GetMapping("find_phone")
    public CommonResponse findPhone(@RequestParam("user_id") Integer userId) {
        return userInfoMybatisService.getUserPhone(userId);
    }

    @PostMapping("redis_set")
    public CommonResponse redisSet(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return CommonResponse.ok();
    }

    @GetMapping("redis_set")
    public Object redisSet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("kafka")
    public CommonResponse kafka() {
        for (int i = 0; i < 50; i++) {
            kafkaService.send("topic-logger", String.valueOf(i), "kafka消息" + i);
            kafkaService.send("topic-order", String.valueOf(i), "kafka消息" + i);
        }
        return CommonResponse.ok();
    }

    @GetMapping("dubbo")
    public CommonResponse dubbo(@RequestParam("user_id") Integer userId) {
        return CommonResponse.ok(bootDubboService.getUserName(userId));
    }

    @GetMapping("redisPubSub")
    public CommonResponse redisPubSub(String message) {
        redisPubSub.publish(CommonResponse.ok(message));
        return CommonResponse.ok();
    }

    @GetMapping("spring-redis")
    public CommonResponse springRedis() {
        redisTemplateService.SpringRedis();
        return CommonResponse.ok();
    }

    @GetMapping("annotaiton")
    @LoginCheck
    public CommonResponse annotaiton(@UserId Integer userId) {
        return CommonResponse.ok(userId);
    }

    @GetMapping("jooq1")
    public CommonResponse jooq1(Integer userId) {
        return CommonResponse.ok(jooqService.getUserName(userId));
    }
}

