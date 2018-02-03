package com.ziroom.minsu.services.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

/**
 * <p>房源锁工具信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/8.
 * @version 1.0
 * @since 1.0
 */
public class HouseLockUtil {


    /**
     * 对当前的天锁形成Md5
     * @author afi
     * @param lock
     * @return
     */
    public static String generateMd54lock(HouseLockEntity lock){
        if (Check.NuNObj(lock)){
            return null;
        }
        String key  = "";
        key += lock.getHouseFid();
        //房间fid
        if (!Check.NuNStr(lock.getRoomFid())){
            key += lock.getRoomFid();
        }
        //床fid
        if (!Check.NuNStr(lock.getBedFid())){
            key += lock.getBedFid();
        }
        //房源类型
        if (!Check.NuNObj(lock.getRentWay())){
            key += lock.getRentWay();
        }
        key += lock.getLockTime().getTime();
        String md5Str = DigestUtils.md5Hex(key);
        //返回有效值的md5
        return md5Str;
    }


}
