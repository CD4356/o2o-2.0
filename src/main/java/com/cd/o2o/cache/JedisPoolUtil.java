package com.cd.o2o.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration //标注@Configuration的类，相当于一个xml配置文件
public class JedisPoolUtil {

    //redis配置属性读取
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.database}")
    private int database;

    @Value("${redis.pool.max-active}")
    private int maxActive;
    @Value("${redis.pool.max-idle}")
    private int maxIdle;
    @Value("${redis.pool.min-idle}")
    private int minIdle;
    @Value("${redis.pool.max-wait}")
    private long maxWaitMillis;

    private JedisPool jedisPool;


    @Bean //标注@Bean后表明返回对象会被当做一个Bean注册到Spring IoC容器
    public synchronized Jedis getJedisPool(){
        try{
            //创建连接池配置类
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            //控制连接池最多可以分配多少个jedis实例
            jedisPoolConfig.setMaxTotal(maxActive);
            //连接池最大空闲连接数
            jedisPoolConfig.setMaxIdle(maxIdle);
            //连接池最小空闲连接数，小于这个数会自动创建连接
            jedisPoolConfig.setMinIdle(minIdle);
            //连接池最大阻塞时间
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
            //创建jedisPool连接池
            jedisPool = new JedisPool(jedisPoolConfig,host,port,database);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 获取jedis连接对象
        return jedisPool.getResource();
    }

}