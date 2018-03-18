package com.zra.app;

import com.ziroom.platform.tesla.server.BaseTeslaApplication;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.ZraApiConst;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * Author: wangxm113
 * CreateDate: 2016/7/26.
 */
public class ZraapiApplication extends BaseTeslaApplication {
    public ZraapiApplication() {
        /*加载扫描包*/
        packages("com");//测试

        //注册swagger相关信息，后面需要再次修改
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        register(MultiPartFeature.class);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});

        //swagger访问json地址
        beanConfig.setHost(PropUtils.getString(ZraApiConst.CON_SWAGGER_HOST_KEY));
        beanConfig.setBasePath("/");

        //配置扫描resource包
        beanConfig.setResourcePackage(PropUtils.getString(ZraApiConst.CON_SWAGGER_RES_PACKAG_KEY));
        beanConfig.setScan(true);

        //注册过滤器
        register(AppFilter.class);
    }
}
