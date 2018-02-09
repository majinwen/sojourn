package com.ziroom.minsu.troy.nps.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.cms.api.inner.NpsService;
import com.ziroom.minsu.services.cms.dto.NpsAttendVo;
import com.ziroom.minsu.services.cms.dto.NpsCollectVo;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.troy.apply.controller.SeedController;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/14 14:31
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("nps")
public class NpsController {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(SeedController.class);

    @Resource(name = "cms.npsService")
    private NpsService npsService;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;


    /**
     * 跳转nps推荐页
     * @param request
     * @return
     */
    @RequestMapping("/toRecommend")
    public String toOrderReList(HttpServletRequest request){
        return "nps/recommendNps";
    }

    /**
     * 跳转nps详情
     * @param request
     * @return
     */
    @RequestMapping("/toQueryNps")
    public String toQueryNps(HttpServletRequest request){
        return "nps/collectNps";
    }

    /**
     * 修改当前nps状态
     * @param
     * @return
     */
    @RequestMapping("/editNpsStatus")
    @ResponseBody
    public DataTransferObject editNpsStatus(NpsEntiy nps){
        String resultJson = npsService.editNpsStatus(nps);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        return dto;
    }

    /**
     * nps汇总信息
     * @param request
     * @return
     */
    @RequestMapping("/queryNps")
    @ResponseBody
    public PageResult queryNps(HttpServletRequest request, NpsGetCondiRequest npsGetCondiRequest){
        try{
            String npsAttendJson = npsService.getNpsForPage(JsonEntityTransform.Object2Json(npsGetCondiRequest));
            DataTransferObject npsAttendDto = JsonEntityTransform.json2DataTransferObject(npsAttendJson);

            List<NpsCollectVo> npsAttendList = npsAttendDto.parseData("list", new TypeReference<List<NpsCollectVo>>() {});

            PageResult pageResult = new PageResult();
            pageResult.setRows(npsAttendList);
            pageResult.setTotal(Long.valueOf(npsAttendDto.getData().get("total").toString()));
            return pageResult;
        }catch (Exception e){
            LogUtil.error(LOGGER,"e:{}", e);
            return null;
        }
    }

    /**
     * 查询nps推荐信息
     * @param request
     * @param npsGetCondiRequest
     * @return
     */
    @RequestMapping("/queryRecommend")
    @ResponseBody
    public PageResult queryRecommend(HttpServletRequest request, NpsGetCondiRequest npsGetCondiRequest){
        try{
            // 根据用户姓名翻译成uid
            if(!Check.NuNStr(npsGetCondiRequest.getUserName())){
                CustomerBaseMsgDto customerBaseMsgDto = new CustomerBaseMsgDto();
                customerBaseMsgDto.setRealName(npsGetCondiRequest.getUserName());
                String userJson = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(customerBaseMsgDto));
                List<CustomerBaseMsgEntity> userList = SOAResParseUtil.getListValueFromDataByKey(userJson, "listCustomerBaseMsg", CustomerBaseMsgEntity.class);
                if(Check.NuNCollection(userList)){
                    return new PageResult();
                }
                for (CustomerBaseMsgEntity entity : userList) {
                    npsGetCondiRequest.getUids().add(entity.getUid());
                }
            }

            // 根据用户手机号翻译成uid，并和上一次查询取交集
            if(!Check.NuNStr(npsGetCondiRequest.getMobile())){
                String userJson = customerInfoService.getCustomerByMobile(npsGetCondiRequest.getMobile());
                CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(userJson, "customerBase", CustomerBaseMsgEntity.class);
                if(Check.NuNObj(customerBase)){
                    return new PageResult();
                }
                npsGetCondiRequest.setUid(customerBase.getUid());
                /*List<String> curList = new ArrayList<String>();
                curList.add(customerBase.getUid());

                List<String> uids = npsGetCondiRequest.getUids();
                if(!Check.NuNCollection(uids)){
                    uids.retainAll(curList);
                }
                if(Check.NuNCollection(uids)){
                    return new PageResult();
                }*/
            }


            String npsAttendJson = npsService.getNpsAttendForPage(JsonEntityTransform.Object2Json(npsGetCondiRequest));
            DataTransferObject npsAttendDto = JsonEntityTransform.json2DataTransferObject(npsAttendJson);

            List<NpsAttendVo> npsAttendList = npsAttendDto.parseData("list", new TypeReference<List<NpsAttendVo>>() {});


            PageResult pageResult = new PageResult();
            pageResult.setRows(npsAttendList);
            pageResult.setTotal(Long.valueOf(npsAttendDto.getData().get("total").toString()));
            return pageResult;
        } catch (Exception e) {
            LogUtil.error(LOGGER,"e:{}", e);
            return null;
        }
    }

    /**
     * @Description: 分时间段计算NPS值
     * @Author: lusp
     * @Date: 2017/7/11 10:27
     * @Params: nps
     */
    @RequestMapping("/calculateNPS")
    @ResponseBody
    public DataTransferObject calculateNPS(HttpServletRequest request,NpsGetCondiRequest nps){
        String resultJson = npsService.calculateNPS(nps);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        return dto;
    }

    /**
     * @Description: 获取NPS名字集合
     * @Author: lusp
     * @Date: 2017/7/13 18:22
     * @Params: nps
     */
    @RequestMapping("/npsNameList")
    @ResponseBody
    public DataTransferObject calculateNPS(HttpServletRequest request){
        String resultJson = npsService.npsNameList();
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        return dto;
    }


}
