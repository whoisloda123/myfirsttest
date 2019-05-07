package com.liucan.boot.service.redis;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author liucan
 * @date 2018/9/16
 * @brief
 */
@Slf4j
@Service
public class RedisTemplateService {
    private final RedisTemplate redisTemplate;

    public RedisTemplateService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过RedisTemplate中的execute方法的参数RedisCallback回
     * 调接口来获取RedisConnection，进一步操作Redis，比如事务控制,管道控制
     */
    @SuppressWarnings("unchecked")
    public void SpringRedis() {
        //redis事务，利用SessionCallback,此处事务没有起作用，因为集群不支持
//        redisTemplate.execute(new SessionCallback() {
//            @Override
//            public Object execute(RedisOperations e) throws DataAccessException {
//                e.watch(Arrays.asList("hkey-a", "hkey-b", "skey"));
//                e.multi();
//
//                e.opsForSet().add("skey", "1", "2");
//                return e.exec();
//            }
//        });

        redisTemplate.execute((RedisConnection e) -> {
            String key = "hkey-b";
            for (int i = 0; i < 10; i++) {
                String hKey = String.format("multikey-b-%s", i);
                String hValue = String.format("multivalue-b-%s", i);
                e.hSet(key.getBytes(), hKey.getBytes(), JSONObject.toJSONString(hValue).getBytes());
            }

            //对hash, zset扫描，对其key对应的hkey
            Cursor<Map.Entry<byte[], byte[]>> cursor = e.hScan(key.getBytes(), ScanOptions.scanOptions().match("multikey*").build());
            while (cursor.hasNext()) {
                Map.Entry<byte[], byte[]> next = cursor.next();
                String mapKey = (String) redisTemplate.getHashKeySerializer().deserialize(next.getKey());
                String mapValue = (String) redisTemplate.getHashValueSerializer().deserialize(next.getValue());
            }

            //扫描所有的key
            RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
            JedisClusterConnection jedisClusterConnection = (JedisClusterConnection) redisConnection;
            JedisCluster jedisCluster = jedisClusterConnection.getNativeConnection();
            Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();

            ScanParams scanParams = new ScanParams().count(100).match("hkey*");
            clusterNodes.values().forEach(node -> {
                String scanCursor = ScanParams.SCAN_POINTER_START;
                do {
                    Jedis jedis = node.getResource();
                    ScanResult<String> scanResult = jedis.scan(scanCursor, scanParams);
                    List<String> result = scanResult.getResult();
                    scanCursor = scanResult.getStringCursor();
                } while (!scanCursor.equals("0"));

            });
            return null;
        });

        //pipeline一起执行，redis集群不支持pipeline
        try {
            String key = "hkey-a";
            RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
            JedisClusterConnection jedisClusterConnection = (JedisClusterConnection) redisConnection;
            //获取到原始到JedisCluster连接
            JedisCluster jedisCluster = jedisClusterConnection.getNativeConnection();

            // 计算hash slot，根据特定的slot可以获取到特定的Jedis实例
            int slot = JedisClusterCRC16.getSlot(key);
            Field field = ReflectionUtils.findField(BinaryJedisCluster.class, null, JedisClusterConnectionHandler.class);

            field.setAccessible(true);
            JedisSlotBasedConnectionHandler jedisClusterConnectionHandler = (JedisSlotBasedConnectionHandler) field.get(jedisCluster);
            //获取到jedis
            Jedis jedis = jedisClusterConnectionHandler.getConnectionFromSlot(slot);

            Pipeline pipeline = jedis.pipelined();

            for (int i = 0; i < 10; i++) {
                String hKey = String.format("multikey-a-%s", i);
                String hValue = String.format("multivalue-a-%s", i);
                pipeline.hset(key.getBytes(), hKey.getBytes(), hValue.getBytes());
            }

            pipeline.sync();
        } catch (Exception exception) {
            log.error("[redis]springRedis操作异常", exception);
        }

        //pipeline一起执行
        redisTemplate.executePipelined((RedisConnection e) -> {

            String key = "hkey-a";
            e.hGetAll(key.getBytes());
            return null;
        });
    }
}
