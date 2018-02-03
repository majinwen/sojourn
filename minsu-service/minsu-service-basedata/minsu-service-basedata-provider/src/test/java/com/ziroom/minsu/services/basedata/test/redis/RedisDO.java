package com.ziroom.minsu.services.basedata.test.redis;

import com.asura.framework.base.util.JsonEntityTransform;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/8.
 * @version 1.0
 * @since 1.0
 */
public class RedisDO {

    public Jedis jedis;

    public void close(){
        jedis.disconnect();
        jedis = null;
    }

    public Jedis open(){
        JedisPoolConfig config = new JedisPoolConfig();

//        config.setMaxActive(100);

        config.setMaxIdle(20);

//        config.setMaxWait(1000l);
        JedisPool pool;
        pool = new JedisPool(config, "redis.cluster.ziroom.com", 6379);

        boolean borrowOrOprSuccess = true;
        try {
            jedis = pool.getResource();
            // do redis opt by instance
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                pool.returnBrokenResource(jedis);

        } finally {
            if (borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        jedis = pool.getResource();
        return jedis;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RedisDO rd = new RedisDO();
        rd.open();
        Set s = rd.jedis.keys("minsu-service-t*");
        Iterator it = s.iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            Map<String, String> par =  rd.jedis.hgetAll(key);

//            String value = rd.jedis.get(key);

            System.out.println("key:"+ key );
//            System.out.println("value:" + JsonEntityTransform.Object2Json(value));
//            System.out.println(value.length());


        }
        rd.close();
    }

}
