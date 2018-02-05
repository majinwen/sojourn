package com.ziroom.minsu.api.baseDate.controller;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.constant.BaseDataConstant;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.basedata.entity.MGVo;
import com.ziroom.minsu.valenum.base.MgStatusEnum;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>移动端的常量控制</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/21.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("conf")
public class ConstantConfigController extends AbstractController{


    @Value("#{'${conf_tel}'.trim()}")
    private String confTel;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

    @Resource(name="api.houseService")
    private HouseService houseService;
    
    @Resource(name="basedata.staticResourceService")
    private StaticResourceService staticResourceService;
    
	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;

    @Value("#{'${conf.MG_URL}'.trim()}")
    private String conf_MG_URL;

    @Value("#{'${conf.MG_VERSION}'.trim()}")
    private Integer conf_MG_VERSION;

    @Value("#{'${conf.MG_STATUS}'.trim()}")
    private Integer conf_MG_STATUS;


    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstantConfigController.class);


    /**
     * 获取配置列表
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/inf")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> confInfo(HttpServletRequest request){

        Map<String,Object> conf = new HashMap<>();
        conf.put("confTel",confTel);
        //目前测试用
        try {
            Header header = getHeader(request);
            LogUtil.debug(LOGGER, "header = {}", JsonEntityTransform.Object2Json(header));
            String uid = getUserId(request);
            houseService.saveLocation(uid,header,getIpAddress(request), LocationTypeEnum.INIT,null,null,true);
            Integer versionCode = Check.NuNObj(header.getVersionCode())?1:header.getVersionCode();
            if(!Check.NuNObj(header) && !Check.NuNStr(header.getOsType()) 
            		&& String.valueOf(HouseSourceEnum.IOS.getCode()).equals(header.getOsType().trim())
            		&&versionCode<=100008){
            	conf.clear();
			}
        }catch (Exception e){
            LogUtil.error(LOGGER, "error = {}", e);
        }
        conf.put("imConMax","400");
        return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(conf), HttpStatus.OK);

    }



    /**
     * 获取评价的最大最小配置信息
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/evalconfig")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> evalconfig(HttpServletRequest request) throws Exception{
        //房源最低价格限制
        String limitJson=cityTemplateService.getTextValue(null, EvaluateRulesEnum.EvaluateRulesEnum002.getValue());
        DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(limitJson);
        String limitStr = null;
        if(limitDto.getCode() == DataTransferObject.SUCCESS){
            limitStr = limitDto.parseData("textValue", new TypeReference<String>() {});

        }else {
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("获取配置信息异常"),HttpStatus.OK);
        }
//        String limitStr= SOAResParseUtil.getValueFromDataByKey(limitJson, "textValue", String.class);

        Map<String,Object> conf = new HashMap<>();
        String split = BaseDataConstant.EVAL_SPLIT;
        int index = limitStr.indexOf(split);
        if (index <= 0){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("异常的配置信息"),HttpStatus.OK);
        }
        conf.put("min",ValueUtil.getintValue(limitStr.substring(0,index)));
        conf.put("max",ValueUtil.getintValue(limitStr.substring(index+1)));
        return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(conf), HttpStatus.OK);
    }
    
    /**
     * 
     * 静态资源查询
     *
     * @author bushujie
     * @created 2017年5月11日 下午5:09:41
     *
     * @param request
     * @return
     * @throws SOAParseException
     */
    @RequestMapping("/${NO_LGIN_AUTH}/getStaticRes")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getStaticRes(HttpServletRequest request) throws SOAParseException{
    	DataTransferObject dto=new DataTransferObject();
    	String resCode = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
    	String resultJson=staticResourceService.findStaticResListByResCode(resCode);
    	List<StaticResourceVo> staticResList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "staticResList", StaticResourceVo.class);
    	if(!Check.NuNCollection(staticResList)){
    		dto.putValue("staticRes", staticResList.get(0).getResContent());
    	}
    	return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
    } 
    
    /**
     * 
     * 查询系统当前时间
     *
     * @author bushujie
     * @created 2017年5月11日 下午5:09:41
     *
     * @param request
     * @return
     * @throws SOAParseException
     */
    @RequestMapping("/${NO_LGIN_AUTH}/getCurrentDate")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getCurrentDate(HttpServletRequest request) throws SOAParseException{
    	DataTransferObject dto=new DataTransferObject();
    	dto.putValue("currentDate", DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
    	return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
    } 
    
    /**
     * 
     * 查询今夜特价配置生效时间
     *
     * @author bushujie
     * @created 2017年5月11日 下午5:09:41
     *
     * @param request
     * @return
     * @throws SOAParseException
     */
    @RequestMapping("/${NO_LGIN_AUTH}/getTodayDiscountStartDate")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getTodayDiscountStartDate(HttpServletRequest request) throws SOAParseException{
    	DataTransferObject dto=new DataTransferObject();
    	Date nowDate=new Date();
		String startTime = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_todayDiscountStartTime.getType(), EnumMinsuConfig.minsu_todayDiscountStartTime.getCode());
		Date startDate=null;
		try {
			startDate=DateUtil.parseDate(DateUtil.dateFormat(nowDate, "yyyy-MM-dd")+" "+startTime+":00", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(startDate.getTime()>nowDate.getTime()){
			dto.putValue("currentDate", DateUtil.dateFormat(startDate, "yyyy-MM-dd HH:mm:ss"));
		} else {
			dto.putValue("currentDate", DateUtil.dateFormat(nowDate, "yyyy-MM-dd HH:mm:ss"));
		}
    	return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
    }

    /**
     * 获取app民宿首页动画
     * @author yanb
     * @created 2018年01月05日 10:02:59
     * @param  * @param null
     * @return
     */
    @RequestMapping("/${NO_LGIN_AUTH}/getMG")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> getFirstMotionGraph(HttpServletRequest request, Integer mgVersion) {
        MGVo mgVo = new MGVo();
        try {
            LogUtil.info(LOGGER, "[getFirstMotionGraph]参数:mgVersion={}" , mgVersion);

            /*
            Header header=getHeader(request);
            LogUtil.info(LOGGER, "【getFirstMotionGraph】参数:mgVersion={},app版本号{},手机操作系统{}", mgVersion, header.getVersionCode(), header.getOsType());

            //有问题的iOS的app版本号(不进行动画更新)

            Integer failureVersionCode=100028;
            Boolean isFailureVersion = false;
            if (failureVersionCode.equals(header.getVersionCode()) && String.valueOf(HouseSourceEnum.IOS.getCode()).equals(header.getOsType().trim())) {
                isFailureVersion = true;
            }
            */

            //判断conf_MG_STATUS 动画的开关是否是关闭状态
            if (!Check.NuNObj(conf_MG_STATUS) && conf_MG_STATUS.equals(MgStatusEnum.MG_STATUS_OFF.getCode())) {
                mgVo.setMgStatus(conf_MG_STATUS);
            } else if (!Check.NuNObj(mgVersion) && mgVersion.equals(conf_MG_VERSION)) {
                //((!Check.NuNObj(mgVersion) && mgVersion.equals(conf_MG_VERSION)) || isFailureVersion) {
                //不是关闭状态的话再判断动画的版本号是不是相等
                mgVo.setMgStatus(MgStatusEnum.MG_STATUS_OPEN_CACHE.getCode());
            } else {
                mgVo.setMgVersion(conf_MG_VERSION);
                mgVo.setMgUrl(conf_MG_URL);
                mgVo.setMgStatus(MgStatusEnum.MG_STATUS_OPEN_LOAD.getCode());
            }
            LogUtil.info(LOGGER, "【getFirstMotionGraph】参数:mgVersion={}", mgVo.toJsonStr());
        } catch (BusinessException e) {
            LogUtil.error(LOGGER, "【getFirstMotionGraph】出现错误,error:{}" , e);
        }
        return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptOK(mgVo), HttpStatus.OK);
    }
}
