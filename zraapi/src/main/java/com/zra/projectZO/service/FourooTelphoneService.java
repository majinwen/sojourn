package com.zra.projectZO.service;

import com.alibaba.fastjson.JSONObject;
import com.zra.api.crm.KeeperPhoneApi;

import com.zra.common.dto.zotel.FourooTelDto;
import com.zra.common.utils.MD5Util;
import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.dao.FourooTelphoneServiceMapper;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 绑定分机号
 */
@Service
public class FourooTelphoneService {

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(FourooTelphoneService.class);

    @Autowired
    private FourooTelphoneServiceMapper dao;

    @Autowired
    private KeeperPhoneApi keeperPhoneApi;


    private static final String ADD = "ADD";// 新增
    private static final String LFG = "LFG";// 忽略
    private static final String DEL = "DEL";//管家离职
    private static final String ISD = "ISD"; //管家分机号已经被回收
    private static final String SYS_FLAG = "kaifangpingtai";


    /**
     * 绑定分机号
     */
    public boolean bindPhone(ProjectZODto zo) {
        boolean ret = true;
        try {
            String status = checkStatus(zo);
            if (ADD.equals(status)) {
                //新增
                addCall(zo);
            } else if (DEL.equals(status)) {
                //删除
                delCall(zo);
            }
        } catch (Exception e) {
            LOGGER.error("bindPhone异常", e);
        }
        return ret;
    }

    /**
     * 检查管家状态
     */
    String checkStatus(ProjectZODto zo) {
        String status = LFG; //无操作；
        boolean isEhrDel = false; //ehr未离职
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userCode", "[" + zo.getProjectZOId() + "]");
        JSONObject json = keeperPhoneApi.checkKeeperStatus(paramMap);
        if (null == json) {
            LOGGER.info("获取管家状态出错");
            return status;
        } else if (json.containsKey("data") && json.getJSONArray("data").size() == 0) {
            isEhrDel = true;
        }
        List<FourooTelDto> list = dao.queryPhone(zo.getProjectZOId());
        LOGGER.info("【管家姓名】{},【管家id】{},【管家在职状态】{},【分配的分机数】{},",zo.getProjectZOName(),zo.getProjectZOId(),isEhrDel?"离职":"未离职",list==null?0:list.size());
        if (list == null || list.isEmpty()) {
            status = isEhrDel ? ISD : ADD; //ehr离职，自如寓无管家，为已经删除isd；ehr未离职，自如寓没有管家，需要新增add
//        } else if (0 == list.get(0).getIsDel() && isEhrDel) { //自如寓管家在职 delete
        } else if (isEhrDel) { //自如寓管家在职
            status = DEL; //如ehr离职，标记为离职，删除cc分机
        }
        return status;
    }

    /**
     * 新增分机号
     */
    void addCall(ProjectZODto zo) {
        //获取分机号
        String extCode = getExtCode();
        Map<String, Object> paramMap = new HashMap<>();
        Long d = System.currentTimeMillis();
        paramMap.put("sys_code", MD5Util.encodeMD5(SYS_FLAG, String.valueOf(d)));
        paramMap.put("access_time", d);
        paramMap.put("keeperId", zo.getProjectZOId());
        paramMap.put("keeperPhone", zo.getZrojectZoPhone());
        paramMap.put("extCode", extCode);
        paramMap.put("keeperName", zo.getProjectZOName());
        //调用crm400新增接口
        JSONObject ret = keeperPhoneApi.addKeeperPhone(paramMap);
        LOGGER.info("addCall接口返回=" + ret);
        if (null != ret && ret.containsKey("status") && "success".equals(ret.getString("status"))) {
            FourooTelDto telDto = new FourooTelDto();
            telDto.setId(zo.getProjectZOId());
            telDto.setCreatTime(new Date(System.currentTimeMillis()));
            telDto.setFourooTel(extCode);
            //本地保存分机号
            dao.bindPhone(telDto);
        } else {
            LOGGER.info("addCall失败，zoid=" + zo.getProjectZOId());
        }
    }


    /**
     * 删除分机号
     */
    void delCall(ProjectZODto zo) {
        Map<String, Object> paramMap = new HashMap<>();
        Long d = System.currentTimeMillis();
        paramMap.put("sys_code", MD5Util.encodeMD5(SYS_FLAG, String.valueOf(d)));
        paramMap.put("access_time", d);
        paramMap.put("keeperId", zo.getProjectZOId());
        //调用crm删除接口
        JSONObject ret = keeperPhoneApi.delKeeperPhone(paramMap);
        if (null != ret && ret.containsKey("status") && "success".equals(ret.getString("status"))) {
            dao.unBindPhone(zo.getProjectZOId());
        } else {
            LOGGER.info("delCall失败，zoid=" + zo.getProjectZOId());
        }
    }

    /**
     * 获取最大分机号
     */
    String getExtCode() {
        String extCode =dao.queryMaxExtCode();
        if (null == dao.queryMaxExtCode()) {
            extCode = "700001";
        } else {
            extCode = String.valueOf(Integer.parseInt(dao.queryMaxExtCode()) + 1);
        }
        return extCode;
    }


}
