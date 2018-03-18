package com.ziroom.minsu.report.house.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.valenum.HouseStatusEnum;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dao.HouseViscosityDao;
import com.ziroom.minsu.report.house.dto.HouseViscosityDto;
import com.ziroom.minsu.report.house.vo.HouseViscosityVo;

/**
 * 
 * <p>房源粘性报表——业务实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */
@Service("report.houseViscosityService")
public class HouseViscosityService implements ReportService <HouseViscosityVo,HouseViscosityDto>{

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseViscosityService.class);
	 
    @Resource(name="report.houseViscosityDao")
    private HouseViscosityDao houseViscosityDao;
    
    @Resource(name="report.confCityDao")
	 private ConfCityDao confCityDao;

    /**
     * 
     * 房源粘性报表——分页查询实现
     *
     * @author ls
     * @created 2017年5月8日 上午10:06:59
     *
     * @param paramDto
     * @return
     * @throws Exception
     */
    public PagingResult<HouseViscosityVo> getHouseViscosityExcelList(HouseViscosityDto paramDto) throws Exception{
    	LogUtil.info(LOGGER, "houseViscosityService, getHouseViscosityExcelList, param={}", JsonEntityTransform.Object2Json(paramDto));
        //查询期间    赋值
    	if(Check.NuNStr(paramDto.getDataQueryBeaginDate())){
    		paramDto.setDataQueryBeaginDate("2016-5-24 00:00:00");
    	}
    	if(Check.NuNStr(paramDto.getDataQueryEndDate())){
    		paramDto.setDataQueryEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
    	}
    	
    	//大区code==>省份codeList
    	if(!Check.NuNStr(paramDto.getRegionCode())){
			List<String> pCodeListByRCode = getPCodeListByRCode(paramDto.getRegionCode());
			if(Check.NuNCollection(pCodeListByRCode)){
				return new PagingResult<HouseViscosityVo>();
			}
			paramDto.setProvinceCodeList(pCodeListByRCode);
		}
    	
    	//整租，查询出所有房源Hfid集合
		if(paramDto.getRentWay()==0){
			PagingResult<HouseViscosityVo> allHfidList = houseViscosityDao.getEntireRentList(paramDto);
			if(allHfidList.getTotal() == 0){
				return allHfidList;
			}
			PagingResult<HouseViscosityVo> allVoByConfirmHfid = getAllVoByConfirmHfid(allHfidList,paramDto);
			return allVoByConfirmHfid;
		}
		
		//分租，查询出所有房间roomFid集合
		PagingResult<HouseViscosityVo> allRfidList = houseViscosityDao.getJoinRentList(paramDto);
		if(allRfidList.getTotal() == 0){
			return allRfidList;
		}
		return getAllVoByConfirmRfid(allRfidList,paramDto);
    }
 
 /**
	 * 根据regionCode获取所有ProvinceCode集合
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
    public List<String> getPCodeListByRCode(String regionCode){
    	List<String> pCodeList = houseViscosityDao.getPCodeListByRCode(regionCode);
    	return pCodeList;
    }
    
    /**
     * 
     * 确认整租的所有Hfid==遍历List==》==》根据Hfid获取Vo需要的数据
     *
     * @author ls
     * @created 2017年5月3日 下午2:42:39
     *
     * @param allConfirmHfid
     * @param paramDto
     * @return
     * @throws Exception
     */
    public PagingResult<HouseViscosityVo> getAllVoByConfirmHfid(PagingResult<HouseViscosityVo> allConfirmHfid, HouseViscosityDto paramDto) throws Exception{
 	    LogUtil.info(LOGGER, "houseViscosityService, getAllVoByConfirmHfid, param={}", JsonEntityTransform.Object2Json(allConfirmHfid));
    	List<HouseViscosityVo> transferList = allConfirmHfid.getRows();
 	   for (HouseViscosityVo temp : transferList) {
 		   //Vo中的nationCode，provinceCode，cityCode  ==》 国家，大区，城市
  		   getHousePhyInfo(temp, paramDto);
 		 
 		   //房源状态==>房源状态名称
    	   if(!Check.NuNObj(temp.getHouseStatus())){
    	  		if(!Check.NuNObj(HouseStatusEnum.getHouseStatusByCode(temp.getHouseStatus()))){
    	  				temp.setHouseStatusName(HouseStatusEnum.getHouseStatusByCode(temp.getHouseStatus()).getName());
    	      	}
    	   }
    	    
    	   //Vo中的Hfid==》Dto中  
    	   paramDto.setHouseBaseFid(temp.getHouseBaseFid());
    	    
 		   //累计浏览量 cumulViewNum;    t_house_statistics_msg 的 house_pv字段(备注：1，只能查询所有的浏览量)
 		   HouseViscosityVo cumulViewVo = houseViscosityDao.getCumulViewVoByHfid(temp.getHouseBaseFid());
 		   temp.setCumulViewNum(!Check.NuNObj(cumulViewVo) && !Check.NuNObj(cumulViewVo.getCumulViewNum()) ? cumulViewVo.getCumulViewNum() : 0);
 		    
 		   //累计咨询量  cumulAdviceNum    t_house_stats_day_msg表的sum(consult_num)(备注：1，房源fid，房间fid一个字段 2，只能查询所有的浏览量)
 		   HouseViscosityVo cumulAdviceVo = houseViscosityDao.getCumulAdviceVoByHfid(paramDto);
    	   temp.setCumulAdviceNum(!Check.NuNObj(cumulAdviceVo) && !Check.NuNObj(cumulAdviceVo.getCumulAdviceNum()) ? cumulAdviceVo.getCumulAdviceNum() : 0);
 
 		   
 		   //累计申请量   cumulApplyNum     所有订单    无论订单状态    无论下单类型 
 		   HouseViscosityVo cumulApplyVo = houseViscosityDao.getCumulApplyVoByHfid(paramDto);
 		  temp.setCumulApplyNum(!Check.NuNObj(cumulApplyVo) && !Check.NuNObj(cumulApplyVo.getCumulApplyNum()) ? cumulApplyVo.getCumulApplyNum() : 0);

 		  //累计接单量  cumulGetOrderNum 
 		       //1，立即下单的所有订单 
 		       //2，申请下单的   order_status不等于 10待确认，31:房东已拒绝 35:房东未确认超时取消
 		   HouseViscosityVo cumulGetOrderVo = houseViscosityDao.getCumulGetOrderVoByHfid(paramDto);
 		   temp.setCumulGetOrderNum(!Check.NuNObj(cumulGetOrderVo) && !Check.NuNObj(cumulGetOrderVo.getCumulGetOrderNum()) ? cumulGetOrderVo.getCumulGetOrderNum() : 0);
 		       
 		   //累计预定间夜量 cumulReserveJYNum  所有订单pay_status=0已支付的order的结束时间减去开始时间 转换成相应的参数  
 		   List<HouseViscosityVo> cumulReserveJYList =  houseViscosityDao.getReserveJYByHfid(paramDto);
 		   int cumulReserveJYNum = 0;
 		   if(!Check.NuNCollection(cumulReserveJYList) && cumulReserveJYList.size()>=1){
 			  temp.setCumulOrderNum(cumulReserveJYList.size());
 			  for (HouseViscosityVo houseViscosityVo : cumulReserveJYList) {
 				 int num = DateUtil.getDatebetweenOfDayNum(new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderStartTime()),new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderEndTime()));
 				 cumulReserveJYNum = cumulReserveJYNum + num;
              }
 		   }else{
 			  temp.setCumulOrderNum(0);
 		   }
 	       temp.setCumulReserveJYNum(cumulReserveJYNum);  
 	       
 		   //累计入住间夜量 cumulCheckInJYNum;  上两个的集合
	       List<HouseViscosityVo> cumulCheckInJYNumList =  houseViscosityDao.getCheckInListByHfid(paramDto);
		   int cumulCheckInJYNum = 0;
		   if(!Check.NuNCollection(cumulCheckInJYNumList) && cumulCheckInJYNumList.size()>=1){ 
			  temp.setCumulCheckInOrderNum(cumulCheckInJYNumList.size());
			  for (HouseViscosityVo houseViscosityVo : cumulCheckInJYNumList) {
				  if(Check.NuNStr(houseViscosityVo.getOrderRelEndTime())){
					  houseViscosityVo.setOrderRelEndTime(paramDto.getDataQueryEndDate());
				  }
				 int num = DateUtil.getDatebetweenOfDayNum(new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderStartTime()),new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderRelEndTime()));
				 cumulCheckInJYNum = cumulCheckInJYNum + num;
             }
		   }else{
			   temp.setCumulCheckInOrderNum(0);
		   }
	       temp.setCumulCheckInJYNum(cumulCheckInJYNum);  
	       
 		   //累计屏蔽间夜量 cumulShieldJYNum;    待确认
	        HouseViscosityVo shieldJYVoByHfid = houseViscosityDao.getShieldJYByHfid(paramDto);
	    	temp.setCumulShieldJYNum(!Check.NuNObj(shieldJYVoByHfid) && !Check.NuNObj(shieldJYVoByHfid.getCumulShieldJYNum()) ? shieldJYVoByHfid.getCumulShieldJYNum() : 0);
	       
 		   //出租率 rentRate;                累计入住间夜量/（查询时间段-累计屏蔽间夜）；下架当天不算
	       int totalDayNum = DateUtil.getDatebetweenOfDayNum(new SimpleDateFormat("yyyy-MM-dd").parse(paramDto.getDataQueryBeaginDate()),new SimpleDateFormat("yyyy-MM-dd").parse(paramDto.getDataQueryEndDate()));
	       temp.setRentRate(accuracy(cumulCheckInJYNum, totalDayNum-temp.getCumulShieldJYNum(), 3));
	       
 		   //累计收到评价量 cumulGetEvalNum;    房客评价总量t_evaluate_order表  join  t_tenant_evaluate表 （is_del=0）
	       HouseViscosityVo   cumulGetEvalVo = houseViscosityDao.getCumulGetEvalByHfid(paramDto); 
	       temp.setCumulGetEvalNum(!Check.NuNObj(cumulGetEvalVo) && !Check.NuNObj(cumulGetEvalVo.getCumulGetEvalNum()) ? cumulGetEvalVo.getCumulGetEvalNum() : 0);
	       	
	       //累计房租收益  cumulProfit;         t_order_money表中Sum(rental_money)   关联订单快照表（Hfid）  关联订单表（order_status>=70）
 	       HouseViscosityVo   cumulProfitVo = houseViscosityDao.getCumulProfitByHfid(paramDto); 
 	       temp.setCumulProfit(!Check.NuNObj(cumulProfitVo) && !Check.NuNObj(cumulProfitVo.getCumulProfit()) ? cumulProfitVo.getCumulProfit() : 0);
 	   }
 	   allConfirmHfid.setRows(transferList);
 	   LogUtil.info(LOGGER, "houseViscosityService, getAllVoByConfirmHfid, result={}", JsonEntityTransform.Object2Json(transferList));
 	   return allConfirmHfid;
    }
    
    /**
     * 
     * 确认分租的所有Rfid==遍历List==》==》根据Rfid获取Vo需要的数据
     *
     * @author ls
     * @created 2017年5月3日 下午6:55:21
     *
     * @param allConfirmHfid
     * @param paramDto
     * @return
     * @throws Exception
     */
    public PagingResult<HouseViscosityVo> getAllVoByConfirmRfid(PagingResult<HouseViscosityVo> allConfirmHfid, HouseViscosityDto paramDto) throws Exception{
  	   LogUtil.info(LOGGER, "houseViscosityService, getAllVoByConfirmRfid, param={}", JsonEntityTransform.Object2Json(allConfirmHfid));
    	List<HouseViscosityVo> transferList = allConfirmHfid.getRows();
  	   for (HouseViscosityVo temp : transferList) {
  		   //Vo中的nationCode，provinceCode，cityCode  ==》 国家，大区，城市
  		   getHousePhyInfo(temp, paramDto);
  		   
  		   //Vo中的roomFid==》Dto中
  		   paramDto.setRoomFid(temp.getRoomFid());
  		   
  		   //累计浏览量 cumulViewNum;    t_house_statistics_msg 的 house_pv字段(备注：1，只能查询所有的浏览量)
  		   HouseViscosityVo cumulViewVo = houseViscosityDao.getCumulViewVoByRfid(temp.getHouseBaseFid());
  		   temp.setCumulViewNum(!Check.NuNObj(cumulViewVo) && !Check.NuNObj(cumulViewVo.getCumulViewNum()) ? cumulViewVo.getCumulViewNum() : 0);
  		  
  		    
  		   //累计咨询量  cumulAdviceNum    t_house_stats_day_msg表的sum(consult_num)(备注：1，房源fid，房间fid一个字段 2，只能查询所有的浏览量)
  		   HouseViscosityVo cumulAdviceVo = houseViscosityDao.getCumulAdviceVoByHfid(paramDto);
  		   temp.setCumulAdviceNum(!Check.NuNObj(cumulAdviceVo) && !Check.NuNObj(cumulAdviceVo.getCumulAdviceNum()) ? cumulAdviceVo.getCumulAdviceNum() : 0);
  		  
  		   //累计申请量   cumulApplyNum     所有订单    无论订单状态    无论下单类型 
  		   HouseViscosityVo cumulApplyVo = houseViscosityDao.getCumulApplyVoByRfid(paramDto);
  		   temp.setCumulApplyNum(!Check.NuNObj(cumulApplyVo) && !Check.NuNObj(cumulApplyVo.getCumulApplyNum()) ? cumulApplyVo.getCumulApplyNum() : 0);

  		  //累计接单量  cumulGetOrderNum 
  		       //1，立即下单的所有订单 
  		       //2，申请下单的   order_status不等于 10待确认，31:房东已拒绝 35:房东未确认超时取消
  		   HouseViscosityVo cumulGetOrderVo = houseViscosityDao.getCumulGetOrderVoByRfid(paramDto);
  		   temp.setCumulGetOrderNum(!Check.NuNObj(cumulGetOrderVo) && !Check.NuNObj(cumulGetOrderVo.getCumulGetOrderNum()) ? cumulGetOrderVo.getCumulGetOrderNum() : 0);
  		       
  		   //累计预定间夜量 cumulReserveJYNum  所有订单pay_status=0已支付的order的结束时间减去开始时间 转换成相应的参数  
  		   List<HouseViscosityVo> cumulReserveJYList =  houseViscosityDao.getReserveJYByRfid(paramDto);
  		   int cumulReserveJYNum = 0;
  		   if(Check.NuNCollection(cumulReserveJYList) && cumulReserveJYList.size()>=1){
  			  temp.setCumulOrderNum(cumulReserveJYList.size());//累计订单量
  			  for (HouseViscosityVo houseViscosityVo : cumulReserveJYList) {
  				 int num = DateUtil.getDatebetweenOfDayNum(new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderStartTime()),new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderEndTime()));
  				 cumulReserveJYNum = cumulReserveJYNum + num;
               }
  		    }else{
  		    	temp.setCumulOrderNum(0);//累计订单量
  		    }
  	       temp.setCumulReserveJYNum(cumulReserveJYNum);  
  	       
  		   //累计入住间夜量  cumulCheckInJYNum; 
 	       List<HouseViscosityVo> cumulCheckInJYNumList =  houseViscosityDao.getCheckInListByRfid(paramDto);
 		   int cumulCheckInJYNum = 0;
 		   if(Check.NuNCollection(cumulCheckInJYNumList) && cumulCheckInJYNumList.size()>=1){ 
 			  temp.setCumulCheckInOrderNum(cumulCheckInJYNumList.size()); //累计入住订单量
 			  for (HouseViscosityVo houseViscosityVo : cumulCheckInJYNumList) {
 				 if(Check.NuNStr(houseViscosityVo.getOrderRelEndTime())){
					  houseViscosityVo.setOrderRelEndTime(paramDto.getDataQueryEndDate());
				  }
 				 int num = DateUtil.getDatebetweenOfDayNum(new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderStartTime()),new SimpleDateFormat("yyyy-MM-dd").parse(houseViscosityVo.getOrderRelEndTime()));
 				 cumulCheckInJYNum = cumulCheckInJYNum + num;
              }
 		   }else{
 			  temp.setCumulCheckInOrderNum(0);//累计入住订单量
 		   }
 	       temp.setCumulCheckInJYNum(cumulCheckInJYNum);  
 	       
  		   //累计屏蔽间夜量 cumulShieldJYNum;    待确认
 	       HouseViscosityVo shieldJYVoByRfid = houseViscosityDao.getShieldJYByRfid(paramDto);
 	       temp.setCumulShieldJYNum(!Check.NuNObj(shieldJYVoByRfid) && !Check.NuNObj(shieldJYVoByRfid.getCumulShieldJYNum()) ? shieldJYVoByRfid.getCumulShieldJYNum() : 0);
 	       
  		   //出租率 rentRate;                累计入住间夜量/（查询时间段-累计屏蔽间夜）；下架当天不算
 	       int totalDayNum = DateUtil.getDatebetweenOfDayNum(new SimpleDateFormat("yyyy-MM-dd").parse(paramDto.getDataQueryBeaginDate()),new SimpleDateFormat("yyyy-MM-dd").parse(paramDto.getDataQueryEndDate()));
 	       temp.setRentRate(accuracy(cumulCheckInJYNum, totalDayNum, 3));
 	       
  		   //累计收到评价量 cumulGetEvalNum;    房客评价总量t_evaluate_order表  join  t_tenant_evaluate表 （is_del=0）
 	       HouseViscosityVo   cumulGetEvalVo = houseViscosityDao.getCumulGetEvalByRfid(paramDto); 
 	       temp.setCumulGetEvalNum(!Check.NuNObj(cumulGetEvalVo) && !Check.NuNObj(cumulGetEvalVo.getCumulGetEvalNum()) ? cumulGetEvalVo.getCumulGetEvalNum() : 0);
 	       	
  		   //累计房租收益  cumulProfit;         t_order_money表中Sum(rental_money)   关联订单快照表（Hfid）  关联订单表（order_status>=70）
 	       HouseViscosityVo   cumulProfitVo = houseViscosityDao.getCumulProfitByRfid(paramDto); 
 	       temp.setCumulProfit(!Check.NuNObj(cumulProfitVo) && !Check.NuNObj(cumulProfitVo.getCumulProfit()) ? cumulProfitVo.getCumulProfit() : 0);
  	   }
  	   allConfirmHfid.setRows(transferList);
  	   LogUtil.info(LOGGER, "houseViscosityService, getAllVoByConfirmRfid, result={}", JsonEntityTransform.Object2Json(transferList));
  	   return allConfirmHfid;
     }
    
    /**
     * 
     * Vo中的nationCode，provinceCode，cityCode  ==》 国家，大区，城市
     *
     * @author ls
     * @created 2017年5月3日 下午6:58:45
     *
     * @param temp
     * @param paramDto
     */
    public void getHousePhyInfo(HouseViscosityVo temp, HouseViscosityDto paramDto){
    	//国家code==》国家名称 
    	if(!Check.NuNStr(temp.getNationCode())){
    		NationCodeEntity nationCodeEntity = confCityDao.getNationName(temp.getNationCode());
    		if(!Check.NuNObj(nationCodeEntity) && !Check.NuNObj(nationCodeEntity.getNationName())){
    			temp.setNationName(nationCodeEntity.getNationName());
    		}
    	}
    	
    	//城市code==》城市名称
    	ConfCityEntity cityByCode = confCityDao.getCityByCode(temp.getCityCode());
    	 if(!Check.NuNObj(cityByCode) && !Check.NuNStr(cityByCode.getShowName())){
    		 temp.setCityName(cityByCode.getShowName());
    	 }
    
    	//获取大区名称
    	 if(!Check.NuNStr(temp.getProvinceCode())){
    		CityRegionEntity cityRegionEntity = confCityDao.getRegionByProvinceCode(temp.getProvinceCode());
    		 if(!Check.NuNObj(cityRegionEntity) && !Check.NuNStr(cityRegionEntity.getRegionName())){
    			 temp.setRegionName(cityRegionEntity.getRegionName());
    		 }
    	 }
		 
		   //房源状态==>房源状态名称
   	       if(!Check.NuNObj(temp.getHouseStatus())){
   	  		  if(!Check.NuNObj(HouseStatusEnum.getHouseStatusByCode(temp.getHouseStatus()))){
   	  				temp.setHouseStatusName(HouseStatusEnum.getHouseStatusByCode(temp.getHouseStatus()).getName());
   	      	  }
   	       }
    }
    
  /**
   * 工具方法——求两个数的百分比
   * @author ls
   * @created 2017年5月3日 下午1:19:54
   *
   * @param num
   * @param total
   * @param scale 小数点位数
   * @return
   */
  	public static String accuracy(double num, double total, int scale){  
  	        DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();  
  	        //可以设置精确几位小数  
  	        df.setMaximumFractionDigits(scale);  
  	        //模式 例如四舍五入  
  	        df.setRoundingMode(RoundingMode.HALF_UP);  
  	        double accuracy_num = num / total * 100;  
  	        return df.format(accuracy_num)+"%";  
  	}

  	/**
  	 * 房源粘性报表（整租）——一条sql解决，已经校验可用(可作为备用方案);
  	 * 
  	 * @author ls
  	 * @created 2017年5月6日 下午3:31:49
  	 *
  	 * @param paramDto
  	 * @return
  	 */
  	public PagingResult<HouseViscosityVo> getAllVoByParam(HouseViscosityDto paramDto){
  	   //查询期间    赋值
    	if(Check.NuNStr(paramDto.getDataQueryBeaginDate())){
    		paramDto.setDataQueryBeaginDate("2016-5-24 00:00:00");
    	}
    	if(Check.NuNStr(paramDto.getDataQueryEndDate())){
    		paramDto.setDataQueryEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
    	}
    	
    	//大区code==>省份codeList
    	if(!Check.NuNStr(paramDto.getRegionCode())){
			List<String> pCodeListByRCode = getPCodeListByRCode(paramDto.getRegionCode());
			if(Check.NuNCollection(pCodeListByRCode)){
				return new PagingResult<HouseViscosityVo>();
			}
			paramDto.setProvinceCodeList(pCodeListByRCode);
		}
    	PagingResult<HouseViscosityVo> allList = houseViscosityDao.getAllVoByParam(paramDto);
    	if(allList.getTotal() == 0){
			return allList;
		}
    	List<HouseViscosityVo> transferList = allList.getRows();
  	    for (HouseViscosityVo temp : transferList) {
  		//Vo中的nationCode，provinceCode，cityCode  ==》 国家，大区，城市
  		   getHousePhyInfo(temp, paramDto);
 		 
 		   //房源状态==>房源状态名称
    	   if(!Check.NuNObj(temp.getHouseStatus())){
    	  		if(!Check.NuNObj(HouseStatusEnum.getHouseStatusByCode(temp.getHouseStatus()))){
    	  				temp.setHouseStatusName(HouseStatusEnum.getHouseStatusByCode(temp.getHouseStatus()).getName());
    	      	}
    	   }
    	 //rentRate.
    	   if(Check.NuNObj(temp.getCumulShieldJYNum())){
    		   temp.setCumulShieldJYNum(0);
    	   }
    	   if(Check.NuNObj(temp.getCumulCheckInJYNum())){
    		   temp.setCumulCheckInJYNum(0);
    	   }
    	   int totalDayNum = 1;
		   try {
			  totalDayNum = DateUtil.getDatebetweenOfDayNum(new SimpleDateFormat("yyyy-MM-dd").parse(paramDto.getDataQueryBeaginDate()),new SimpleDateFormat("yyyy-MM-dd").parse(paramDto.getDataQueryEndDate()));
		   } catch (ParseException e) {
			    e.printStackTrace();
		   }
    	    temp.setRentRate(accuracy(temp.getCumulCheckInJYNum(), totalDayNum-temp.getCumulShieldJYNum(), 3));
  	   }
  	    allList.setRows(transferList);
  		return allList;
  	}
  	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<HouseViscosityVo> getPageInfo(HouseViscosityDto par) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(HouseViscosityDto par) {
		return null;
	}
  	
}
