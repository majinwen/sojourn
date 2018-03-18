package com.zra.app;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

//@Component("BeanDefineConfigue")
public class ZraInit implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger LOG = LoggerFactoryProxy.getLogger(ZraInit.class);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //
        LOG.info("ZraInit ----- BEGIN");
        LOG.info("ZraInit ----- END");
    }

}
