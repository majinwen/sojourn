package com.ziroom.minsu.services.search.listener;


import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.service.SpellServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>初始化搜索的map</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.startupListener")
public class StartupListener implements  ApplicationListener<ContextRefreshedEvent> {

    @Resource(name = "search.spellServiceImpl")
    private SpellServiceImpl spellService;


    private static final Logger LOGGER = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if ( event.getApplicationContext (). getParent() == null) {
            Long start = System.currentTimeMillis();
            LogUtil.info(LOGGER,"开始启动加载汉子转拼音的map............");
            //TODO 不影响大家可以先注释掉
            //spellService.initSpellInfo();
            Long end = System.currentTimeMillis();
            LogUtil.info(LOGGER, "拼音初始化完毕，消费时间：{}",end-start);
        }
    }
}