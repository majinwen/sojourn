package com.ziroom.minsu.services.cms.proxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.asura.framework.base.exception.BusinessException;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.services.cms.service.*;
import com.ziroom.minsu.valenum.cms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.cms.api.inner.ActivityFullService;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.dto.ActivityGiftItemRequest;
import com.ziroom.minsu.services.cms.entity.AcGiftItemVo;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.order.CouponStatusEnum;

/**
 * <p>优惠券活动的一些操作</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/14.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityFullProxy")
public class ActivityFullProxy  implements ActivityFullService {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityProxy.class);


    @Resource(name = "cms.messageSource")
    private MessageSource messageSource;


    @Resource(name = "cms.actCouponServiceImpl")
    private ActCouponServiceImpl actCouponService ;


    @Resource(name = "cms.activityFullServiceImpl")
    private ActivityFullServiceImpl activityFullService;


    @Resource(name = "cms.activityServiceImpl")
    private ActivityServiceImpl activityServiceImpl ;
    
    @Resource(name = "cms.activityGiftItemServiceImpl")
    private ActivityGiftItemServiceImpl activityGiftItemServiceImpl;

    @Resource(name="cms.activityHouseServiceImpl")
    private ActivityHouseServiceImpl activityHouseServiceImpl;
    
    @Resource(name="cms.actCouponExtServiceImpl")
    private ActCouponExtServiceImpl actCouponExtServiceImpl;


    /**
     * 根据活动编号获取优惠券活动信息
     *
     * 说明 ：如果当前是礼品活动  获取当前的礼品信息
     * @author afi
     * @param actSn
     * @return
     */
    public String getActivityFullBySn(String actSn) {

        LogUtil.info(LOGGER, "参数:{}", actSn);
        DataTransferObject dto = new DataTransferObject();
        try {
            ActivityFullEntity activityFullEntity  = activityFullService.getActivityFullBySn(actSn);
            List<ActivityCityEntity> cityEntityList =  activityServiceImpl.getActivityCitiesByActSn(actSn);
            //获取礼品
            ActivityGiftItemRequest activityGiftItemRe = new ActivityGiftItemRequest();
        	activityGiftItemRe.setActSn(actSn);
        	List<AcGiftItemVo> lsitAcGiftItemVo = this.activityGiftItemServiceImpl.getAcGiftItemByCon(activityGiftItemRe);
        	dto.putValue("lsitAcGiftItemVo",lsitAcGiftItemVo);
            
            dto.putValue("full",activityFullEntity);
            dto.putValue("cityList",cityEntityList);
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();


    }

    @Override
    public String listActivityFullByGroupSn(String groupSn) {
        LogUtil.info(LOGGER, "【listActivityFullByGroupSn】参数:{}", groupSn);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(groupSn)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动组号为空");
            return dto.toJsonString();
        }
        List<ActivityFullEntity> fullList = activityFullService.getActivityFullByGroupSn(groupSn);
        dto.putValue("list",fullList);
        return dto.toJsonString();
    }


    /**
     * 查询优惠券列表
     * @author afi
     * @param paramJson
     * @return
     */
    public String getCouponFullList(String paramJson){

        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ActCouponRequest request = JsonEntityTransform.json2Object(paramJson, ActCouponRequest.class);
            PagingResult<ActCouponUserEntity> pagingResult = actCouponService.getCouponFullList(request);
            dto.putValue("total", pagingResult.getTotal());
            dto.putValue("list", pagingResult.getRows());

        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();


    }

    @Override
    public String findLimitHouseByActsn(String actSn) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(actSn)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动编号为空");
            return dto.toJsonString();
        }
        List<ActivityHouseEntity> list = activityHouseServiceImpl.findHouseByActsn(actSn);
        dto.putValue("list",list);
        return dto.toJsonString();
    }


    /**
     * 生成或者追加优惠券
     * @author afi
     * @param actSn
     * @return
     */
    public String generateCouponByActSnExt(String actSn,Integer extNum){
        LogUtil.info(LOGGER, "参数actSn:{} ext:{}", actSn,extNum);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(actSn)){
            LogUtil.error(LOGGER, "generateCouponByActSnContine actSn is null :{} ");
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        //优惠券的验证信息
        this.generateCouponPre(actSn,dto,true,extNum);
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * 通过活动编号生成优惠券信息
     * @param actSn
     * @return
     */
    public String generateCouponByActSn(String actSn){
        LogUtil.info(LOGGER, "参数:{}", actSn);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(actSn)){
            LogUtil.error(LOGGER, "generateCouponByActSn actSn is null :{}");
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        //优惠券的验证信息
        this.generateCouponPre(actSn,dto);
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


    /**
     * 生成优惠券
     * @author afi
     * @param actSn
     * @param dto
     */
    private  void generateCouponPre(final String actSn,DataTransferObject dto){
        generateCouponPre(actSn,dto,false,0);
    }


    /**
     * 生成优惠券
     * @author afi
     * @param actSn
     * @param dto
     * @param checkOrg
     * @param extNum 新增的数量
     */
    private synchronized void generateCouponPre(final String actSn,DataTransferObject dto,boolean checkOrg,int extNum){

        try {
            ActivityFullEntity act = activityFullService.getActivityFullBySn(actSn);
            if(Check.NuNObj(act)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
                return ;
            }
            if(act.getActKind() != ActKindEnum.COUPON.getCode()){
                //当前活动已经生成优惠券
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("非优惠券活动不能生成优惠券");
                return ;
            }
            ActStatusEnum actStatusEnum = ActStatusEnum.getByCode(act.getActStatus());
            if (Check.NuNObj(actStatusEnum)){
                //异常的活动状态
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("异常的活动状态");
                return ;
            }

            if(!actStatusEnum.checkExt() && checkOrg){
                //当前活动已经生成优惠券
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("当前状态不能追加优惠券");
                return ;
            }

            if(act.getIsCoupon() == IsCouponEnum.YES.getCode() && !checkOrg){
                //当前活动已经生成优惠券
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("已经生成");
                return ;
            }
            if(act.getIsCoupon() == IsCouponEnum.ING.getCode()){
                //当前活动已经生成优惠券
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg("优惠券生成中");
                return ;
            }

            //修改为生成中
            activityServiceImpl.updateAcCouponIng(actSn, dto);
            if (dto.getCode() == DataTransferObject.SUCCESS){
                //异步生成优惠券
                generateCoupon(act,checkOrg,extNum);
            }//TODO  当判断状态为生成中提示用户稍后点击
        } catch(Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
    }

    /**
     * 生成优惠券
     * @author afi
     * @param act
     * @param checkOrg
     * @param extNum
     */
    private void generateCoupon(final ActivityFullEntity act,final Boolean checkOrg,final Integer extNum){
        if (Check.NuNObj(act)){
            return;
        }
        Thread task = new Thread(){
            @Override
            public void run() {
                try {
                    //生成当前的优惠券信息
                    Set<String> set = null;
                    if (checkOrg){
                        set = generateCouponSetContinue(act.getActSn(), act.getCouponNum(),extNum);
                    }else {
                        set = generateCouponSet(act.getActSn(), act.getCouponNum());
                    }

//                List<ActCouponEntity> disCountQuota = disCountQuota(act);
//                int flag = 0;
                    for (String sn:set ){

                        ActCouponEntity actCouponEntity = new ActCouponEntity();
                        actCouponEntity.setCouponSn(sn);
                        actCouponEntity.setRandSn(SnUtil.getChar(20));
                        actCouponEntity.setActSn(act.getActSn());
                        //当前的活动组不为空
                        if (!Check.NuNStr(act.getGroupSn())){
                            actCouponEntity.setGroupSn(act.getGroupSn());
                        }
                        actCouponEntity.setCreateId(act.getCreateId());
                        //判断是否为优惠定额活动，如果是，则优惠券金额通过定额规则生成
//                    Integer actType = act.getActType();
//                    if(actType == 5){
//                    		actCouponEntity.setActCut(disCountQuota.get(flag).getActCut());
//                    		actCouponEntity.setActLimit(disCountQuota.get(flag).getActLimit());
//                    		flag++;
//                    }else{
                        actCouponEntity.setActCut(act.getActCut());
                        actCouponEntity.setActLimit(act.getActLimit());
//                    }
                        actCouponEntity.setActType(act.getActType());
                        actCouponEntity.setActMax(act.getActMax());
                        actCouponEntity.setActUser(act.getRoleCode());
                        actCouponEntity.setCouponName(act.getCouponName());

                        CouponTimeTypeEnum couponTimeType = CouponTimeTypeEnum.getByCode(act.getCouponTimeType());
                        if (Check.NuNObj(couponTimeType)){
                            throw new BusinessException("异常的优惠券时间限制类型");
                        }
                        if (couponTimeType.getCode() == CouponTimeTypeEnum.FIX.getCode()){
                            actCouponEntity.setCouponStartTime(act.getCouponStartTime());
                            actCouponEntity.setCouponEndTime(act.getCouponEndTime());
                        }
                        actCouponEntity.setCheckInTime(act.getCheckInTime());
                        actCouponEntity.setCheckOutTime(act.getCheckOutTime());
                        actCouponEntity.setCouponStatus(CouponStatusEnum.WAIT.getCode());
                        actCouponEntity.setIsLimitHouse(act.getIsLimitHouse());
                        actCouponService.saveCoupon(actCouponEntity);
                    }
                }catch (Exception e){
                    LogUtil.error(LOGGER, "追加优惠券 actSn:{}error:{}",act.getActSn(),e);
                }
                
                try{
	                //修改t_activity_ext_coupon表根据活动码修改优惠券数量
	                ActivityExtCouponEntity activityEntity = new ActivityExtCouponEntity();
	                activityEntity.setActSn(act.getActSn());
	                activityEntity.setCouponNum(act.getCouponNum()+extNum);
	                activityEntity.setWarnTimes(0);
	                actCouponExtServiceImpl.updateActivityInfo(activityEntity);
	                
	                //修改为已生成
	                activityServiceImpl.updateIsCouponStatus(act.getActSn(), IsCouponEnum.YES.getCode());
                }catch (Exception e){
                    LogUtil.error(LOGGER, "追加优惠券 actSn:{}error:{}",act.getActSn(),e);
                }
            }
        };
        SendThreadPool.execute(task);
    }


    /**
     * 获取优惠券的编号集合
     * @param orgSize 原来优惠券的生成数量
     * @return
     */
    private Set<String> generateCouponSetContinue(String actSn,Integer orgSize,Integer extNum){
        Set<String> rst = new HashSet<>();
        Set<String> set = new HashSet<>();

        int pageSize = 200;
        ActCouponRequest request = new ActCouponRequest();
        request.setLimit(pageSize);
        request.setActSn(actSn);
        PagingResult<ActCouponEntity> pagingResult = actCouponService.getCouponListAllByActSn(request);
        int total = ValueUtil.getintValue(pagingResult.getTotal());
        //将当前的优惠券放入set集合
        this.transList2Set(pagingResult.getRows(),set);
        int last = orgSize + extNum - total;
        if (total > 0) {
            int page = ValueUtil.getPage(total, request.getLimit());
            for (int i = 1; i <= page; i++) {
                //从第二页开始，循环处理之后的数据信息
                request.setPage(i);
                PagingResult<ActCouponEntity> pagingResultC = actCouponService.getCouponListAllByActSn(request);
                //将当前的优惠券放入set集合
                this.transList2Set(pagingResultC.getRows(),set);
            }
        }
        //获取当前的优惠券信息
        for (int i=0;i<last;){
            String sn = actSn + SnUtil.getCouponSn();
            if(set.contains(sn)){
                continue;
            }else {
                i++;
                set.add(sn);
                rst.add(sn);
            }
        }
        return rst;
    }

    /**
     * 将优惠券信息转到set集合中
     * @param actCouponEntityList
     * @param acSet
     */
    private void transList2Set(List<ActCouponEntity>  actCouponEntityList,Set<String>  acSet){
        if (Check.NuNCollection(actCouponEntityList)){
            return;
        }
        if (Check.NuNObj(acSet)){
            return;
        }
        for (ActCouponEntity actCouponEntity : actCouponEntityList) {
            acSet.add(actCouponEntity.getCouponSn());
        }
    }




    /**
     * 获取优惠券的编号集合
     * @param size 活动码和优惠券数量
     * @return
     */
    private Set<String> generateCouponSet(String actSn,int size){
        Set<String> set = new HashSet<>();
        for (int i=0;i<size;){
            String sn = actSn + SnUtil.getCouponSn();
            if(set.contains(sn)){
                continue;
            }else {
                i++;
                set.add(sn);
            }
        }
        return set;
    }

    /**
     * 优惠定额活动，设置满减规则 
     *
     * @author lunan
     * @created 2016年8月25日 下午3:41:19
     *
     * @param act,flag
     */
    private List<ActCouponEntity> disCountQuota(ActivityFullEntity act){
	   //获取优惠定额活动规则
    	String disRule = act.getDiscountQuota();
    	disRule = disRule.trim().substring(0, disRule.length()-1);
    	//去掉末尾|
    	String[] rules = disRule.split("\\|");
    	String[] ratio = null;
    	String[] limit = null;
    	String[] actCut = null;
    	String[] rule = null;
    	for(int i=0; i<rules.length; i++){
    		rule = rules[i].split("_");
    		limit[i] = rule[0];
    		actCut[i] = rule[1];
    		ratio[i] = rule[2];
     	}
    	List<ActCouponEntity> dis = new ArrayList<>();
//    	Map<Integer,Integer> map = new HashMap<Integer,Integer>();
    	if(!Check.NuNObj(limit) && !Check.NuNObj(actCut) && !Check.NuNObj(ratio)){
    		//获取优惠券数量
    		Integer couponNum = act.getCouponNum();
    		for(int i=0; i<limit.length; i++){
    			for(int j=0; j< Math.ceil(couponNum/100*Integer.parseInt(ratio[i])); j++){
    				ActCouponEntity actCoupon = new ActCouponEntity();
    				actCoupon.setActCut(Integer.parseInt(actCut[i]));
    				actCoupon.setActLimit(Integer.parseInt(limit[i]));
    				dis.add(actCoupon);
    			}
    		}
    	}
    	return dis;
    }
}
