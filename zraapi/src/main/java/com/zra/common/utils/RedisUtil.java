package com.zra.common.utils;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.zmconfig.ConfigUtils;
import com.zra.zmconfig.entity.CfZmConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 连接和使用数据库资源的工具类
 *
 * @author gxn
 * @version IAM 2013-04-25
 *          <p>
 *          TODO CUIYH9 注意配置文件修改以及废弃方法调用
 */
@ThreadSafe
public class RedisUtil {

    private final static Logger LOGGER = LoggerFactoryProxy.getLogger(RedisUtil.class);

    /**
     * 数据源 初始化spring注入
     */
    private static JedisSentinelPool jedisSentinelPool;

    public static final String KEY_PREFIX = "ZRA_API_CACHE_";

    public static final int EXPIRE_SECOND = 1200;// 20分钟

    /**
     * 设置数据
     */
    public static boolean setData(String key, String value) {
        if (isCanNotUseRedis()) {
            return true;
        }
        return setDataToCache(key, value);
    }
    
    public static boolean setDataToCache(String key, String value) {
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);

        } finally {
            closeConnection(jedis);
        }
        return false;
    }

    /**
     * 设置数据，带过期时间，未来多少秒过期
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public static boolean setData(String key, String value,int timeout) {
        if (isCanNotUseRedis()) {
            return true;
        }
        return setDataToCache(key,value,timeout);
    }
    
    public static boolean setDataToCache(String key, String value,int timeout) {
        Jedis jedis = null;
        key =KEY_PREFIX+key;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.set( key, value);
            if(timeout>0){
                jedis.expire(key, timeout);
            }
            LOGGER.info("redis添加限时数据key="+key+",value="+value+"成功,有效期"+timeout+"秒");

            return true;
        } catch (Exception e) {
            LOGGER.error("redis添加限时数据key="+key+",value="+value+"失败,有效期"+timeout+"秒", e);
            return false;
        } finally {
            if (jedis!=null) {
                jedis.close();
            }
        }
    }

    public static boolean setDataExpire(String key, String value) {
        if (isCanNotUseRedis()) {
            return true;
        }
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            jedis.set(key, value);
            jedis.expire(key, EXPIRE_SECOND);
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);

        } finally {
            closeConnection(jedis);
        }
        return false;
    }

    /**
     * 设置数据-设置时间
     *
     * @param time seconds add by Tanght 2014-10-16
     */
    public static boolean setDataExpire(String key, String value, Integer time) {
        if (isCanNotUseRedis()) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            jedis.setex(KEY_PREFIX + key, time, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            closeConnection(jedis);
        }
        return false;
    }

    /**
     * 获取数据
     *
     */
    public static String getData(String key) {
        if (isCanNotUseRedis()) {
            return null;
        }
        return getDataFromCache(key);
    }
    
    public static String getDataFromCache(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            key = getKey(key);
            value = jedis.get(key);
            return value;
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            closeConnection(jedis);
        }
        return value;
    }

    
    /**
     * 删除数据
     *
     * @param key
     * @return
     */
    public static boolean deleteData(String key) {
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            closeConnection(jedis);
        }
        return false;
    }

    /**
     * 获得对象 add by wgh
     *
     * @param key
     * @return
     */
    public static Object getObject(String key) {
        if (isCanNotUseRedis()) {
            return null;
        }
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            key = getKey(key);
            value = ObjectTranscoder.deserialize(jedis.get(key.getBytes()));
            return value;
        } catch (Exception e) {
            LOGGER.error("[Redis读取对象]错误:", e);
        } finally {
            closeConnection(jedis);
        }
        return value;
    }

    /**
     * Increase created by cuigh6 on 2016/07/11.
     *
     * @param key the key
     * @return the long
     */
    public static Long increase(String key) {
        Long value = null;
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            value = jedis.incr(key);
            return value;
        } catch (Exception e) {
            LOGGER.error("【Redis操作对象】错误:", e);
        } finally {
            closeConnection(jedis);
        }
        return value;
    }

    /**
     * Increase created by cuigh6 on 2016/07/11.
     *
     * @param key the key
     * @return the long
     */
    public static Long decrement(String key) {
        Long value = null;
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            value = jedis.decr(key);
            return value;
        } catch (Exception e) {
            LOGGER.error("【Redis操作对象】错误:", e);
        } finally {
            closeConnection(jedis);
        }
        return value;
    }

    /**
     * 设置对象 add by wgh
     *
     * @param key
     * @return
     */
    public static boolean setObjectExpire(String key, Object value) {
        if (isCanNotUseRedis()) {
            return true;
        }
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            jedis.set(key.getBytes(), ObjectTranscoder.serialize(value));
            jedis.expire(key.getBytes(), EXPIRE_SECOND);
            return true;
        } catch (Exception e) {
            LOGGER.error("[Redis保存对象并设置过期时间]错误:", e);
        } finally {
            closeConnection(jedis);
        }
        return false;
    }

    public static boolean setObject(String key, Object value) {
        if (isCanNotUseRedis()) {
            return true;
        }
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            jedis.set(key.getBytes(), ObjectTranscoder.serialize(value));
            return true;
        } catch (Exception e) {
            LOGGER.error("[Redis保存对象]", e);
        } finally {
            closeConnection(jedis);
        }
        return false;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(Jedis jedis) {
        if (null != jedis) {
            try {
                jedis.close();
            } catch (Exception e) {
                LOGGER.error("[Redis关闭连接]错误:", e);
            }
        }
    }

    /**
     * 单独测试redis时使用该方法
     **/
    public static JedisSentinelPool testShardedJedisPool() {
		Set sentinels = new HashSet();

		// 对应三个sentinel地址
		sentinels.add(new HostAndPort("10.16.34.125", 6411).toString());
		sentinels.add(new HostAndPort("10.16.34.15", 6390).toString());
		sentinels.add(new HostAndPort("10.16.34.16", 6387).toString());

		// masterName为：sentinel-"应用的master ip"-"master port"
		JedisSentinelPool sentinelPool = new JedisSentinelPool("sentinel-10.16.34.15-6389", sentinels);
		return sentinelPool;

    }
    
    private static String getKey(String key) {
        return KEY_PREFIX + key;
    }

    /**
     * 获取连接池
     * @return
     */
    public static JedisSentinelPool getJedisSentinelPool() {
		return jedisSentinelPool;
	}

    /**
     * 设置连接池
     * @param jedisSentinelPool
     */
	public static void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
		RedisUtil.jedisSentinelPool = jedisSentinelPool;
	}
	
	

	public static void main(String[] args) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setJedisSentinelPool(testShardedJedisPool());
        RedisUtil.setObjectExpire("11", new String("aa"));
        RedisUtil.setObjectExpire("12", new String("aa"));
        RedisUtil.setObjectExpire("13", new String("aa"));
        RedisUtil.setObjectExpire("14", new String("aa"));
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            LOGGER.error("[Redis获取连接池]错误:", e);
        }

    }

    /**
     * 保存/覆盖对象，设置过期时间
     *
     * @param key
     * @param value
     * @return
     * @Author: wangxm113
     * @CreateDate: 2016年4月23日
     */
    public static boolean setObjectExpire(String key, Object value, int expire) {
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            jedis.setex(key.getBytes(), expire, ObjectTranscoder.serialize(value));
            return true;
        } catch (Exception e) {
            LOGGER.error("[Redis保存对象并设置过期时间]出错:", e);
        } finally {
            closeConnection(jedis);
        }
        return false;
    }

    /**
     * 获取某个对象剩余过期时间
     *
     * @param key
     * @return
     * @Author: wangxm113
     * @CreateDate: 2016年5月4日
     */
    public static Long getExpireTime(String key) {
        Jedis jedis = null;
        try {
            key = getKey(key);
            jedis = jedisSentinelPool.getResource();
            return jedis.ttl(key);
        } finally {
            closeConnection(jedis);
        }
    }

    /**
     * 加锁，实现资源跨jvm同步
     *
     * @param lockKey      ：自己指定一个key作为锁
     * @param expireSecond ： 此资源占有多长时间锁(单位：秒)
     * @param timeOut      ： 等待锁的最长等待时间(单位：秒)
     * @return
     * @throws Exception
     * @Author: wangxm113
     * @CreateDate: 2016年5月18日
     */
    public static boolean lock(String lockKey, long expireSecond, long timeOut) throws Exception {
        Jedis shardedJedis = null;
        boolean lockSuccess = false;
        try {
            lockKey = getKey(lockKey);
            shardedJedis = jedisSentinelPool.getResource();

            long start = System.currentTimeMillis();
            do {
                long result = shardedJedis.setnx(lockKey,
                        String.valueOf(System.currentTimeMillis() + expireSecond * 1000));
                if (result == 1) {// 插入成功
                    lockSuccess = true;
                    break;
                } else {// 原来存在此key
                    String lockTimeStr = shardedJedis.get(lockKey);// if决定了此值必为最初的值
                    if (Long.valueOf(lockTimeStr) < System.currentTimeMillis()) {// 锁已过期
                        String originStr = shardedJedis.getSet(lockKey,
                                String.valueOf(System.currentTimeMillis() + expireSecond * 1000));// redis中新值替换的旧值
                        if (lockTimeStr.equals(originStr)) {// 表明锁由该线程获得!此步骤必须要有
                            lockSuccess = true;
                            break;
                        }
                    }
                }

                if (timeOut == 0) {// 如果不等待，则直接返回
                    break;
                }
                Thread.sleep(200);// 等待200ms继续尝试加锁
            } while ((System.currentTimeMillis() - start) < timeOut * 1000);
        } catch (Exception e) {
            LOGGER.error("[Redis加锁]出错:", e);
            throw e;
        } finally {
            closeConnection(shardedJedis);
        }
        return lockSuccess;
    }

    /**
     * 解锁，实质就是删掉该锁数据
     *
     * @param lockKey
     * @Author: wangxm113
     * @CreateDate: 2016年5月18日
     */
    public static void unLock(String lockKey) {
        Jedis shardedJedis = null;
        try {
            lockKey = getKey(lockKey);
            shardedJedis = jedisSentinelPool.getResource();

            shardedJedis.del(lockKey);
        } catch (Exception e) {
            LOGGER.error("[Redis解锁]出错:", e);
        } finally {
            closeConnection(shardedJedis);
        }
    }
    
    /**
     * 是否启用Redis
     * @return
     */
    private static boolean isCanNotUseRedis() {
        return !"true".equalsIgnoreCase(PropUtils.getString(ZraApiConst.REDIS_SWITCH_KEY));
    }
    
    
    /**
     * @author tianxf9 将RedisConfig功能迁移到这里统一维护
     * @param key
     * @return
     */
	public static String getConfig(String key){
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			return jedis.get(ConfigUtils.wrap(key));
		} catch (Exception e) {
			LOGGER.error("[Redis-getConfig]出错:", e);
		}finally{
			closeConnection(jedis);
		}
		return null;
	}
	
	
	public static void setConfig(CfZmConfig config){
		setConfig(ConfigUtils.wrapCfKey(config),config.getCfValue());
	}
	
	public static void setConfig(String key,String value){
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.set(ConfigUtils.wrap(key), value);
		} catch (Exception e) {
			LOGGER.error("[Redis-setConfig]出错:", e);
		}finally{
			closeConnection(jedis);
		}
		
	}
	
	public static void deleteConfig(CfZmConfig config){
		Jedis jedis = null;
		try {
			jedis = jedisSentinelPool.getResource();
			jedis.del(ConfigUtils.wrapCfKey(config));
		} catch (Exception e) {
			LOGGER.error("[Redis-deleteConfig]出错:", e);
		}finally{
			closeConnection(jedis);
		}
	}
	
}
