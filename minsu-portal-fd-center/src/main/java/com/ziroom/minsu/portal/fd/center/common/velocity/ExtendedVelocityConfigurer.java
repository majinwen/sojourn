package com.ziroom.minsu.portal.fd.center.common.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * 扩展velcoity configurer 
 * <p>
 * 	源configurer 的 resourceLoader不能加载jar中的文件，此类用于重写此方法
 * </p>
 * @author yd
 * @date 2016-03-24 23:20
 * @version 1.0
 *
 */
public class ExtendedVelocityConfigurer extends VelocityConfigurer {
	
	@Override
	protected void initVelocityResourceLoader(VelocityEngine velocityEngine, String resourceLoaderPath) {
		if (isPreferFileSystemAccess()) {
			initSpringResourceLoader(velocityEngine, resourceLoaderPath);
		}
		else {
			// Always load via SpringResourceLoader
			// (without hot detection of template changes).
			if (logger.isDebugEnabled()) {
				logger.debug("File system access not preferred: using SpringResourceLoader");
			}
			initSpringResourceLoader(velocityEngine, resourceLoaderPath);
		}
	}
}
