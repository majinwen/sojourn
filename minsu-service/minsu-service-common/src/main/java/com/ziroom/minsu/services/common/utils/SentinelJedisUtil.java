/**
 * @FileName: SentinelJedisUtil.java
 * @Package com.ziroom.minsu.services.common.utils
 * 
 * @author bushujie
 * @created 2016年12月26日 上午9:13:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.common.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.utils.LogUtil;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * <p>哨兵模式redis工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class SentinelJedisUtil {
	
	private JedisSentinelPool sentinelPool;
	
	/**
	 * 哨兵服务地址1
	 */
	private String sentinelOneServer;
	
	/**
	 * 哨兵服务地址2
	 */
	private String sentinelTwoServer;
	
	/**
	 * 哨兵服务地址3
	 */
	private String sentinelThreeServer;
	
	/**
	 * 主服务器地址
	 */
	private String redisMasterServer;
	
	/**
	 * #最小能够保持idel状态的对象数
	 */
	private int poolMinIdle; 
	
	/**
	 * #最大能够保持idel状态的对象数
	 */
	private int poolMaxIdle;
	
	/**
	 * #最大的对象数
	 */
	private int poolMaxTotal;
	
	/**
	 * 最大等待时间
	 */
	private long poolMaxWait;
	
	/**
	 * 超时时间
	 */
	private int poolTimeOut;
	
	/**
	 * 缓存环境区别标示
	 */
	private String redisApp;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(SentinelJedisUtil.class);
	
	/**
	 * 
	 * 初始化配置
	 *
	 * @author bushujie
	 * @created 2016年12月26日 下午8:47:53
	 *
	 */
	public void initSentinelJedisPool(){
		Set<String> sentinels = new HashSet<String>();
		// 对应三个sentinel地址
		sentinels.add(new HostAndPort(sentinelOneServer.split(",")[0], Integer.valueOf(sentinelOneServer.split(",")[1])).toString());
		sentinels.add(new HostAndPort(sentinelTwoServer.split(",")[0], Integer.valueOf(sentinelTwoServer.split(",")[1])).toString());
		sentinels.add(new HostAndPort(sentinelThreeServer.split(",")[0], Integer.valueOf(sentinelThreeServer.split(",")[1])).toString());
		//连接池配置
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig(); 
		jedisPoolConfig.setMaxIdle(poolMaxIdle);
		jedisPoolConfig.setMaxTotal(poolMaxTotal);
		jedisPoolConfig.setMinIdle(poolMinIdle);
		jedisPoolConfig.setMaxWaitMillis(poolMaxWait);
		sentinelPool = new JedisSentinelPool(redisMasterServer, sentinels,jedisPoolConfig,poolTimeOut);
		LogUtil.info(LOGGER,"redis地址： " + sentinelPool.getCurrentHostMaster().toString());
	}
	
	/**
	 * 
	 * 缓存存入值
	 *
	 * @author bushujie
	 * @created 2016年12月26日 下午7:59:10
	 *
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public void setex(String key, int seconds, String value){
		Jedis jedis=null;
		boolean broken=false;
		key=getKeyAll(key);
		try {
			jedis=sentinelPool.getResource();
			LogUtil.info(LOGGER, "ups放入:{}的key:{}：",value,key);
			jedis.setex(key, seconds, value);
		} catch (JedisException e) {
			broken=handleJedisException(e);
		} finally {
			closeResource(jedis, broken);
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
     * @Author: yd
     * @CreateDate: 2017年12月17日 
     */
    public  boolean lock(String lockKey, long expireSecond, long timeOut){
        Jedis shardedJedis = null;
        boolean lockSuccess = false;
    	boolean broken=false;
        try {
            lockKey = getKeyAll(lockKey);
            shardedJedis = sentinelPool.getResource();
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
        } catch (JedisException e) {
            LOGGER.error("[Redis加锁]出错:lockKey={},e={}",lockKey, e);
        	broken=handleJedisException(e);
        } catch (InterruptedException e) {
        	LOGGER.error("[Redis加锁]线程出错:lockKey={},e={}",lockKey, e);
        	throw new BusinessException("[Redis加锁]线程出错");
		} finally {
        	closeResource(shardedJedis, broken);
        }
        return lockSuccess;
    }
    
    
    /**
     * 解锁，实质就是删掉该锁数据
     *
     * @param lockKey
     * @Author: yd
     * @CreateDate: 2017年12月17日
     */
    public  void unLock(String lockKey) {
        Jedis shardedJedis = null;
    	boolean broken=false;
        try {
            lockKey = getKeyAll(lockKey);
            shardedJedis = sentinelPool.getResource();
            shardedJedis.del(lockKey);
        } catch (JedisException e) {
            LOGGER.error("[Redis解锁]出错:lockKey={},e={}",lockKey, e);
        	broken=handleJedisException(e);
        } finally {
        	closeResource(shardedJedis, broken);
        }
    }
	
	/**
	 * 
	 * 获取key值
	 *
	 * @author bushujie
	 * @created 2016年12月26日 下午8:50:35
	 *
	 * @param key
	 * @return
	 */
	public String getex(String key){
		Jedis jedis=null;
		boolean broken=false;
		key=getKeyAll(key);
		String value=null;
		try {
			LogUtil.info(LOGGER, "report系统取值key：{}",key);
			jedis=sentinelPool.getResource();
			value=jedis.get(key);
		} catch (JedisException e) {
			broken=handleJedisException(e);
		} finally {
			closeResource(jedis, broken);
		}
		return value;
	}
	
	/**
	 * 
	 * 删除缓存
	 *
	 * @author bushujie
	 * @created 2016年12月27日 下午4:20:17
	 *
	 * @param key
	 */
	public void del(String key){
		Jedis jedis=null;
		boolean broken=false;
		key=getKeyAll(key);
		try {
			jedis=sentinelPool.getResource();
			jedis.del(key);
		} catch (JedisException e) {
			broken=handleJedisException(e);
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * 头部插入元素
	 * @author jixd
	 * @created 2017年04月01日 10:30:32
	 * @param
	 * @return
	 */
	public void lpush(String key,String... value){
		Jedis jedis=null;
		boolean broken=false;
		key=getKeyAll(key);
		try {
			jedis=sentinelPool.getResource();
			jedis.lpush(key,value);
		} catch (JedisException e) {
			broken=handleJedisException(e);
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * 右边插入元素
	 * @author jixd
	 * @created 2017年04月01日 10:21:58
	 * @param
	 * @return
	 */
	public void rpush(String key,String... value){
		Jedis jedis=null;
		boolean broken=false;
		key=getKeyAll(key);
		try {
			jedis=sentinelPool.getResource();
			jedis.rpush(key,value);
		} catch (JedisException e) {
			broken=handleJedisException(e);
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * 左边队列弹出首元素
	 * @author jixd
	 * @created 2017年04月01日 10:24:34
	 * @param
	 * @return
	 */
	public String lpop(String key){
		Jedis jedis=null;
		boolean broken=false;
		key=getKeyAll(key);
		try {
			jedis=sentinelPool.getResource();
			return jedis.lpop(key);
		} catch (JedisException e) {
			broken=handleJedisException(e);
		} finally {
			closeResource(jedis, broken);
		}
		return null;
	}

	/**
	 * 获取一个范围的所有值
	 * @author jixd
	 * @created 2017年04月01日 11:00:04
	 * @param
	 * @return
	 */
	public List<String> lrange(String key, int start, int end){
		Jedis jedis=null;
		boolean broken=false;
		key=getKeyAll(key);
		try {
			jedis=sentinelPool.getResource();
			return jedis.lrange(key,start,end);
		} catch (JedisException e) {
			broken=handleJedisException(e);
		} finally {
			closeResource(jedis, broken);
		}
		return null;
	}

	
	/**
	 * 
	 * 判断是否网络异常
	 *
	 * @author bushujie
	 * @created 2016年12月26日 下午6:06:19
	 *
	 * @param jedisException
	 * @return
	 */
	private boolean handleJedisException(JedisException jedisException) {
	    if (jedisException instanceof JedisConnectionException) {
	    	LogUtil.error(LOGGER, "Redis 连接 " + sentinelPool.getCurrentHostMaster().getHost() + " lost.", jedisException);
	    } else if (jedisException instanceof JedisDataException) {
	        if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
	        	LogUtil.error(LOGGER,"Redis 连接 " +sentinelPool.getCurrentHostMaster().getHost() + " are read-only slave.", jedisException);
	        } else {
	            return false;
	        }
	    } else {
	    	LogUtil.error(LOGGER,"Jedis exception happen.", jedisException);
	    }
	    return true;
	}
	
	 /**
	  * 
	  * jedis销毁或者回收
	  *
	  * @author bushujie
	  * @created 2016年12月26日 下午7:39:47
	  *
	  * @param jedis
	  * @param conectionBroken
	  */
	private void closeResource(Jedis jedis, boolean conectionBroken) {
	    try {
	        if (conectionBroken) {
	        	sentinelPool.returnBrokenResource(jedis);
	        } else {
	        	sentinelPool.returnResource(jedis);
	        }
	    } catch (Exception e) {
	    	LogUtil.error(LOGGER,"异常销毁jedis", e);
	    }
	}
	
	
	/**
	 * 
	 * 添加缓存标示
	 *
	 * @author bushujie
	 * @created 2016年12月28日 下午3:06:06
	 *
	 * @param key
	 * @return
	 */
	private String getKeyAll(String key) {
		return redisApp + "_" + key;
	}
	/**
	 * 
	 * 销毁redis连接池释放资源
	 *
	 * @author bushujie
	 * @created 2016年12月27日 上午11:44:48
	 *
	 */
	public void destorySentinelJedisPool(){
		if(sentinelPool!=null){
			LogUtil.info(LOGGER,"正常销毁sentinelPool");
			sentinelPool.destroy();
		}
	}

	/**
	 * @return the sentinelPool
	 */
	public JedisSentinelPool getSentinelPool() {
		return sentinelPool;
	}

	/**
	 * @param sentinelPool the sentinelPool to set
	 */
	public void setSentinelPool(JedisSentinelPool sentinelPool) {
		this.sentinelPool = sentinelPool;
	}

	/**
	 * @return the sentinelOneServer
	 */
	public String getSentinelOneServer() {
		return sentinelOneServer;
	}

	/**
	 * @param sentinelOneServer the sentinelOneServer to set
	 */
	public void setSentinelOneServer(String sentinelOneServer) {
		this.sentinelOneServer = sentinelOneServer;
	}

	/**
	 * @return the sentinelTwoServer
	 */
	public String getSentinelTwoServer() {
		return sentinelTwoServer;
	}

	/**
	 * @param sentinelTwoServer the sentinelTwoServer to set
	 */
	public void setSentinelTwoServer(String sentinelTwoServer) {
		this.sentinelTwoServer = sentinelTwoServer;
	}

	/**
	 * @return the sentinelThreeServer
	 */
	public String getSentinelThreeServer() {
		return sentinelThreeServer;
	}

	/**
	 * @param sentinelThreeServer the sentinelThreeServer to set
	 */
	public void setSentinelThreeServer(String sentinelThreeServer) {
		this.sentinelThreeServer = sentinelThreeServer;
	}

	/**
	 * @return the redisMasterServer
	 */
	public String getRedisMasterServer() {
		return redisMasterServer;
	}

	/**
	 * @param redisMasterServer the redisMasterServer to set
	 */
	public void setRedisMasterServer(String redisMasterServer) {
		this.redisMasterServer = redisMasterServer;
	}

	/**
	 * @return the poolMinIdle
	 */
	public int getPoolMinIdle() {
		return poolMinIdle;
	}

	/**
	 * @param poolMinIdle the poolMinIdle to set
	 */
	public void setPoolMinIdle(int poolMinIdle) {
		this.poolMinIdle = poolMinIdle;
	}

	/**
	 * @return the poolMaxIdle
	 */
	public int getPoolMaxIdle() {
		return poolMaxIdle;
	}

	/**
	 * @param poolMaxIdle the poolMaxIdle to set
	 */
	public void setPoolMaxIdle(int poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}

	/**
	 * @return the poolMaxTotal
	 */
	public int getPoolMaxTotal() {
		return poolMaxTotal;
	}

	/**
	 * @param poolMaxTotal the poolMaxTotal to set
	 */
	public void setPoolMaxTotal(int poolMaxTotal) {
		this.poolMaxTotal = poolMaxTotal;
	}

	/**
	 * @return the poolMaxWait
	 */
	public long getPoolMaxWait() {
		return poolMaxWait;
	}

	/**
	 * @param poolMaxWait the poolMaxWait to set
	 */
	public void setPoolMaxWait(long poolMaxWait) {
		this.poolMaxWait = poolMaxWait;
	}

	/**
	 * @return the poolTimeOut
	 */
	public int getPoolTimeOut() {
		return poolTimeOut;
	}

	/**
	 * @param poolTimeOut the poolTimeOut to set
	 */
	public void setPoolTimeOut(int poolTimeOut) {
		this.poolTimeOut = poolTimeOut;
	}
	
	/**
	 * @return the redisApp
	 */
	public String getRedisApp() {
		return redisApp;
	}

	/**
	 * @param redisApp the redisApp to set
	 */
	public void setRedisApp(String redisApp) {
		this.redisApp = redisApp;
	}
}
