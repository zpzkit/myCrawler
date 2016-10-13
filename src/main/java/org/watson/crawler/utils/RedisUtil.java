package org.watson.crawler.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by watson zhang on 16/9/29.
 */
public class RedisUtil {
    Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static String CONFIGNAME = "crawler.properties";

    String host;
    int port;
    public Jedis redis;
    private RedisUtil(){
        try {
            PropertiesConfiguration conf = new PropertiesConfiguration(CONFIGNAME);
            host = conf.getString("org.watson.crawler.redis.host", "127.0.0.1");
            port = conf.getInt("org.watson.crawler.redis.prot", 6379);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        redis = new Jedis(host, port);
    }

    private static RedisUtil redisUtil = null;
    private static String lock = new String("1");

    public static RedisUtil getInstance(){
        if (redisUtil == null){
            synchronized (lock){
                if (redisUtil == null){
                    redisUtil = new RedisUtil();
                }
            }
        }
        return redisUtil;
    }
}
