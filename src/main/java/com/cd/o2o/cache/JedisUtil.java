package com.cd.o2o.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

import java.util.Set;

@Component  //标注@Component，表示将该类注册到Spring IoC容器
public class JedisUtil {

    @Autowired
    private Jedis jedis;

    //操作key的方法 （用于判断某个key是否存在，需不需要清空key里的数据），这是对key的操作

    /**
     * 判断某个Key是否存在
     *
     * @param key
     * @return boolean
     */
    public boolean exists(String key){
        boolean exists = false;
        try{
            //调用jedis的exists()方法，判断某个Key是否存在于Redis服务器中
            exists = jedis.exists(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return exists;
    }


    /**
     * 查找所有给定模式的key
     *
     * @param pattern  key表达式：*表示多个 ?表示一个 （粒如：shop*表示所有以shop开头的key）
     * @return
     */
    public Set<String> keys(String pattern){
        Set<String> set = null;
        try{
            //调用jedis的keys()方法，获取匹配的key，并保存到Set集合中
            set = jedis.keys(pattern);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return set;
    }

    /**
     * 删除key对应的记录，可以是多个记录
     *
     * @param keys
     * @return 删除的记录数
     */
    public long del(String... keys){
        long count = 0;
        try{
            //调用jedis的del()方法将相关的Keys删除，并返回删除的记录数
            count = jedis.del(keys);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return count;
    }

    /**
     * 清除所有的key
     * @return 状态码
     */
    public String flushAll(){
        String state = null;
        try{
            //调用jedis的flushAll()方法，清空所有的key
            state = jedis.flushAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return state;
    }

    /**
     * 修改key名，如果新key已存在，则旧key的值会覆盖新key的值
     *
     * @param oldKey
     * @param newKey
     * @return 状态码
     */
    public String rename(String oldKey,String newKey){
        String state = null;
        try{
            state = jedis.rename(SafeEncoder.encode(oldKey),SafeEncoder.encode(newKey));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return state;
    }


    /**
     * 在新key不存在时，将旧key修改成该新key的名称
     *
     * @param oldKey
     * @param newKey
     * @return 状态码
     */
    public Long renamenx(String oldKey,String newKey){
        long status = 0;
        try{
            //重命名key
            status = jedis.renamenx(oldKey,newKey);
        }catch (Exception e){
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return status;
    }


    //对存储结构为String的操作（Redis中，字符串类型的value最大可容纳的数据为512M），这是对value的操作

    /**
     * 根据key名，获取其存储数据
     * @param key
     * @return
     */
    public String get(String key){
        String value = null;
        try{
            //获取指定key的存储数据
            value = jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return value;
    }

    /**
     * 添加记录，如果记录已存在则覆盖原有的value
     * @param key
     * @param value
     * @return
     */
    public String set(String key,String value){
        String state = null;
        try{
            //设置指定key的存储数据
            state = jedis.set(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭jedis连接，避免资源占用
            jedis.close();
        }
        return state;
    }

}