/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.asura.amp.dubbo.monitor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.monitor.MonitorService;
import com.asura.amp.dubbo.monitor.entity.Statistics;
import com.asura.amp.dubbo.monitor.service.SysMonitorService;

/**
 * SimpleMonitorService
 * 
 * @author william.liangf
 */
public class SoaMonitorService implements MonitorService {

    private static final Logger logger = LoggerFactory.getLogger(SoaMonitorService.class);
    
    @Autowired
	private SysMonitorService sysMonitorService;

//    private static final String[] types = {SUCCESS, FAILURE, ELAPSED, CONCURRENT, MAX_ELAPSED, MAX_CONCURRENT};
    
    private static final String POISON_PROTOCOL = "poison";
    
    private final Thread writeThread;
    
    private final BlockingQueue<URL> queue;
    
    private volatile boolean running = true;
    
    public SoaMonitorService() {
        queue = new LinkedBlockingQueue<URL>(Integer.parseInt(ConfigUtils.getProperty("dubbo.monitor.queue", "100000")));
        writeThread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        write(); // 记录统计日志
                    } catch (Throwable t) { // 防御性容错
                        logger.error("Unexpected error occur at write stat log, cause: " + t.getMessage(), t);
                        try {
                            Thread.sleep(5000); // 失败延迟
                        } catch (Throwable t2) {
                        }
                    }
                }
            }
        });
        writeThread.setDaemon(true);
        writeThread.setName("DubboMonitorAsyncWriteLogThread");
        writeThread.start();
    }
    
    private void write() throws Exception {
        URL statistics = queue.take();
        if (POISON_PROTOCOL.equals(statistics.getProtocol())) {
            return;
        }
        
//        for (String key : types) {
//        	monitorDao.saveStatistics(Url2Statistics(statistics, key));
//        }
        
//        sysMonitorService.saveStatistics(Url2Statistics(statistics));
    }
    
	private Statistics Url2Statistics(URL url) {
		Statistics statistics = new Statistics();

		statistics.setApplication(url.getParameter(APPLICATION));
		statistics.setService(url.getServiceInterface());
		statistics.setMethod(url.getParameter(METHOD));
		statistics.setVersion(url.getParameter(VERSION));

		statistics.setSuccess(url.getParameter("success", 0));
		statistics.setFailure(url.getParameter("failure", 0));
		statistics.setElapsed(url.getParameter("elapsed", 0));
		statistics.setConcurrent(url.getParameter("concurrent", 0));
		statistics.setMax_elapsed(url.getParameter("max_elapsed", 0));
		statistics.setMax_concurrent(url.getParameter("max_concurrent", 0));

		String provider = null;
		String consumer = null;
		String type = null;
		if (url.hasParameter(PROVIDER)) {
			// type = CONSUMER;
			type = "1";
			consumer = url.getHost();
			provider = url.getParameter(PROVIDER);
			int i = provider.indexOf(':');
			if (i > 0) {
				provider = provider.substring(0, i);
			}
		} else {
			// type = PROVIDER;
			type = "0";
			consumer = url.getParameter(CONSUMER);
			int i = consumer.indexOf(':');
			if (i > 0) {
				consumer = consumer.substring(0, i);
			}
			provider = url.getHost();
		}
		
		Date date = null;
		String timestamp = url.getParameter(Constants.TIMESTAMP_KEY);
        if (timestamp == null || timestamp.length() == 0) {
        	date = new Date();
        } else if (timestamp.length() == "yyyyMMddHHmmss".length()) {
        	try {
				date = new SimpleDateFormat("yyyyMMddHHmmss").parse(timestamp);
			} catch (ParseException e) {
				date = new Date();
			}
        } else {
        	date = new Date(Long.parseLong(timestamp));
        }
        
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        try {
        	date = format.parse(format.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		statistics.setProvider(provider);
		statistics.setConsumer(consumer);
		statistics.setType(type);
		statistics.setDtime(date);

		return statistics;
	}

    public void count(URL statistics) {
        collect(statistics);
    }

    public void collect(URL statistics) {
        queue.offer(statistics);
        if (logger.isInfoEnabled()) {
            logger.info("collect statistics: " + statistics);
        }
    }

	public List<URL> lookup(URL query) {
		return null;
	}
}