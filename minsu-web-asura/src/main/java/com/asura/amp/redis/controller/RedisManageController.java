/**
 * @FileName: RedisManageController.java
 * @Package com.asura.amp.redis.controller
 * 
 * @author zhangshaobin
 * @created 2016年5月15日 上午2:57:39
 * 
 * Copyright 2011-2099 asura
 */
package com.asura.amp.redis.controller;

import com.asura.framework.base.util.Check;
import com.asura.framework.cache.redisOne.RedisOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>redis缓存管理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Controller("redisManageController")
@RequestMapping("/redis/mg")
public class RedisManageController {

//
    @Autowired
    private RedisOperations redisOperations;

    /**
     * 缓存前缀
     */
//    private static String keyPre = "minsu_";
    @Value("#{'${key_pre}'.trim()}")
    private String keyPre;

	/**
	 * 
	 * 初始化页面
	 *
	 * @author zhangshaobin
	 * @created 2016年5月15日 上午3:15:25
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(Model model) {

        model.addAttribute("keyPre",keyPre);
		return "/redis/init";
	}

    @RequestMapping(value = "/clean")
    public String cleanKey(Model model) {

        model.addAttribute("keyPre",keyPre);
        return "/redis/cleanKey";
    }


	/**
	 * 
	 * 查询redis值
	 *
	 * @author zhangshaobin
	 * @created 2016年5月15日 上午3:24:12
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String search(Model model,String keyValue) {
        String key = keyPre + keyValue;
        String rst = redisOperations.get(key);
        if(Check.NuNStr(rst)){
            rst = "缓存未存在";
        }
        model.addAttribute("redisInf",rst);
		return "/redis/search";
	}


    @RequestMapping(value = "/del")
    public String delKey(Model model,String keyValue) {
        String key = keyPre + keyValue;
        String rst = redisOperations.get(key);
        if(Check.NuNStr(rst)){
            rst = "缓存未存在";
        }else {
            redisOperations.del(key);
            rst = "删除成功";
        }
        model.addAttribute("redisInf",rst);
        return "/redis/search";
    }

    @RequestMapping(value = "/add")
    public String add(Model model,String keyValue,String bodyValue) {
        String key = keyPre + keyValue;

        String rst = redisOperations.get(key);
        redisOperations.setex(key.toString(), 7200, bodyValue);
        model.addAttribute("redisInf","添加成功");
        return "/redis/search";
    }

}
