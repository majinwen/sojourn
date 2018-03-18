package com.zra.common.constant;

/**
 * 统一缓存Key常量
 * @author cuiyh9
 *
 */
public interface CacheKeyCons {
    
    /**
     * CMS模块的缓存Key
     * @author cuiyh9
     *
     */
    public  interface CMS{
        
        /**
         * 客户端APP显示信息,使用时需要添加projectId
         * V1版本，以后鬼知道有多少版本
         */
        public static final String CMS_APP_PROJECT_V1 = "CMS_APP_PROJECT_V1";
        
        /**
         * @author tianxf9
         * app显示户型信息
         */
        public static final String CMS_APP_HOUSETYPE_V1 = "CMS_APP_HOUSETYPE_V1";
        
        /**
         * @author tianxf9
         * app显示户型详情页标签
         */
        public static final String CMS_APP_HOUSETYPE_LAB_V1 = "CMS_APP_HOUSETYPE_LAB_V1";
    }
    
}

