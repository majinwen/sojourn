package com.ziroom.minsu.services.house.api.inner;

/**
 * airbnb房源 服务类
 * @author jixd
 * @created 2017年04月15日 16:44:00
 * @param
 * @return
 */
public interface AbHouseService {

    /**
     * 插入解析数据
     * @author jixd
     * @created 2017年04月15日 16:45:00
     * @param
     * @return
     */
    String saveHouseCalendar(String paramJson);

    /**
     * 插入对应关系
     * @author jixd
     * @created 2017年04月15日 16:45:27
     * @param
     * @return
     */
    String saveHouseRelate(String paramJson); 

    /**
     * 分页查询关联视图信息
     * @param paramJson
     * @return
     */
    String listHouseRelateVoByPage(String paramJson);

    /**
     * 查询房源信息
     * @author jixd
     * @created 2017年04月17日 10:00:52
     * @param
     * @return
     */
    String findHouseRelateByFid(String paramJson);
    
    /**
     * 根据fid修改关系记录
     * @author zyl
     * @created 2017年6月28日 下午7:22:58
     *
     * @param paramJson
     * @return
     */
    String updateHouseRelateByFid(String paramJson);
    
    /**
     * 
     * 异步保存 ab房源状态同步表
     *
     * @author yd
     * @created 2017年7月29日 下午12:33:57
     *
     * @param paramJson
     * @return
     */
    void asySaveHouseCalendar(String paramJson);
    
    /**
     * 
     * 条件查询房源同步连接配置
     *
     * @author bushujie
     * @created 2017年11月8日 上午10:47:48
     *
     * @param paramJson
     * @return
     */
    String findAbHouseByHouse(String paramJson);
}
