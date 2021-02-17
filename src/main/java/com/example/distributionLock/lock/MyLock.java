package com.example.distributionLock.lock;

import com.example.distributionLock.service.DistributionWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author hjwjames
 * @date 2021.2.17
 */
@Component
public class MyLock {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    DistributionWorkService distributionWorkService;
    private final String lockKey = "lockKey";
    private final String clientId = UUID.randomUUID().toString();

    /**
     * 简单的分布式锁实现
     * 能解决大部分分布式锁问题。但是还是又小瑕疵。
     * 过期时间设置问题，30s是否够用？
     * 可以写一个监听分布式锁，进行续期
     * @return
     */
    public String myRedisLock(){
        try{
            //redis setnx lockKey ex 30  如果redis不存在则设置lockKey，过期时间为30秒
            if(!redisTemplate.opsForValue().setIfAbsent(lockKey,clientId,30, TimeUnit.SECONDS)){
                return "error code";
            }

            //工作业务
            String result = distributionWorkService.work1();
            return result;
        }finally {
            //使用完后释放锁
            if(clientId.equals(redisTemplate.opsForValue().get(lockKey))){
                redisTemplate.delete(lockKey);
            }
        }
    }
}
