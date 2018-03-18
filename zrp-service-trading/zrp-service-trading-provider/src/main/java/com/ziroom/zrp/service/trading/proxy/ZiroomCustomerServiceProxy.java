package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.api.ZiroomCustomerService;
import com.ziroom.zrp.service.trading.dto.PersonalInfoDto;
import com.ziroom.zrp.service.trading.utils.CustomerLibraryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * <p>友家服务接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年09月26日 11:39
 * @since 1.0
 */
@Component("trading.ziroomCustomerServiceProxy")
public class ZiroomCustomerServiceProxy implements ZiroomCustomerService {

    private static Logger LOGGER = LoggerFactory.getLogger(ZiroomCustomerServiceProxy.class);

    /**
     * 根据手机号查询uid信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String queryUidByPhone(String phone) {
        LogUtil.info(LOGGER, "queryUidByPhone：{}", phone);
        return CustomerLibraryUtil.findUidByPhone(phone);
    }
    /**
     * 根据uid查询用户信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    @Override
    public String findAuthInfoFromCustomer(String uid) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(uid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("uid 参数为空");
            return dto.toJsonString();
        }

        LogUtil.info(LOGGER, "findAuthInfoFromCustomer：{}", uid);
        PersonalInfoDto  personalInfoDto = CustomerLibraryUtil.findAuthInfoFromCustomer(uid);
        dto.putValue("data", personalInfoDto);
        return dto.toJsonString();

    }

    /**
     * 查询用户实名认证状态
     * @param jsonParam {uid:""}
     * @return json
     * @author cuigh6
     * @Date 2017年10月
     */
    @Override
    public String getUserAuthInfo(String jsonParam) {
        LogUtil.info(LOGGER, "【getUserAuthInfo】参数={}", jsonParam);
        DataTransferObject dto = new DataTransferObject();
        Map map = JsonEntityTransform.json2Object(jsonParam, Map.class);
        String uid = (String) map.get("uid");
        if (Check.NuNStr(uid)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("uid 参数为空");
            return dto.toJsonString();
        }
        JSONObject result = CustomerLibraryUtil.getUserAuthStatus(uid);
        if (result == null) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
            return dto.toJsonString();
        }
        dto.putValue("data", result);
        return dto.toJsonString();
    }

    /**
     * 保存客户库信息
     * @param paramJson 社会资质证明
     * @return
     * @author cuigh6
     * @Date 2017年12月
     */
    @Override
    public String saveCustomerInfo(String paramJson) {
        LogUtil.info(LOGGER, "【saveCustomerInfo】参数={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        Map map = JsonEntityTransform.json2Map(paramJson);
        String uid = (String) map.get("uid");
        if (Check.NuNStr(uid)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("uid 参数为空");
            return dto.toJsonString();
        }
        JSONArray result = CustomerLibraryUtil.saveSocialProof(map);
        if (result == null) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("调用客户库接口错误");
            return dto.toJsonString();
        }
        dto.putValue("data", result);
        return dto.toJsonString();
    }

}
