package com.example.distributionLock.lock;

import com.example.distributionLock.service.DistributionWorkService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author hjwjames
 * @date 2021.2.17
 */
@Component
public class RedissonLock {

    @Autowired
    private Redisson redisson;
    @Autowired
    DistributionWorkService distributionWorkService;
    private final String lockKey = "lockKey";

    /**
     * 企业级的分布式锁应用实现
     * 可以解决大部分分布式锁问题，CAP中能保证AP
     * 如果想保证CP，则用ZK / RedLock
     * @return
     */
    public String redissonLockTest(){
        RLock redissonLock = redisson.getLock(lockKey);
        try{
            //redis setnx lockKey ex 30
            redissonLock.lock();

            //工作业务
            String result = distributionWorkService.work1();
            return result;
        }finally {
            redissonLock.unlock();
        }
    }
}
