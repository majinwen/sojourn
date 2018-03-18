package com.ziroom.zrp.service.trading.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.api.CityService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.valenum.RoomSmartLockBindEnum;
import com.ziroom.zrp.service.trading.api.RentCheckinService;
import com.ziroom.zrp.service.trading.dto.PersonAndSharerDto;
import com.ziroom.zrp.service.trading.dto.SharerDto;
import com.ziroom.zrp.service.trading.dto.checkin.RentCheckinPersonDto;
import com.ziroom.zrp.service.trading.dto.checkin.RentCheckinPersonSearchDto;
import com.ziroom.zrp.service.trading.dto.smartlock.EffectiveContractDto;
import com.ziroom.zrp.service.trading.entity.RentCheckinPersonVo;
import com.ziroom.zrp.service.trading.pojo.PersonAndSharerPojo;
import com.ziroom.zrp.service.trading.service.RentCheckinPersonServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.service.ShareServiceImpl;
import com.ziroom.zrp.service.trading.utils.cert.IdcardValidatorUtils;
import com.ziroom.zrp.service.trading.valenum.CertTypeEnum;
import com.ziroom.zrp.service.trading.valenum.CustomerTypeEnum;
import com.ziroom.zrp.service.trading.valenum.base.IsDelEnum;
import com.ziroom.zrp.service.trading.valenum.base.ValidEnum;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.ziroom.zrp.trading.entity.RentDetailEntity;
import com.ziroom.zrp.trading.entity.SharerEntity;
import com.zra.common.constant.ContractMsgConstant;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>入住人信息管理</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年12月04日 18:30
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
@Component("trading.rentCheckinServiceProxy")
public class RentCheckinServiceProxy implements RentCheckinService {

    @Resource(name = "trading.rentCheckinPersonServiceImpl")
    private RentCheckinPersonServiceImpl rentCheckinPersonServiceImpl;

    @Resource(name = "trading.shareServiceImpl")
    private ShareServiceImpl sharerServiceImpl;

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    @Resource(name = "trading.shareServiceImpl")
    private ShareServiceImpl shareServiceImpl;

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;
    
    @Resource(name="houses.cityService")
    private CityService cityService;

    /**
     * 新增/更换 入住人 和 合住人
     * @param paramJson
     * @return
     */
    @Override
    public String saveCheckinAndSharer(String paramJson) {
        LogUtil.info(log, "【saveCheckinAndSharer】参数para={}", paramJson);
        DataTransferObject dto = new DataTransferObject();

        String message = "添加成功！";
        int code = DataTransferObject.SUCCESS;

        try {
            PersonAndSharerDto param = JsonEntityTransform.json2Object(paramJson,PersonAndSharerDto.class);

            final RentCheckinPersonEntity rentCheckinPersonEntity = param.getRentCheckinPersonEntity();

            if (StringUtils.isBlank(rentCheckinPersonEntity.getContractId())) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("合同id为空");
                return dto.toJsonString();
            }
            final List<SharerEntity> sharerEntities = param.getSharerEntities();

            // 先查库里，以此判断是新增还是更换入住人
            RentContractEntity rentContractEntity = rentContractServiceImpl.findContractBaseByContractId(rentCheckinPersonEntity.getContractId());

            // 新增 还是 更换入住人
            int flag = Check.NuNStr(param.getOldUid()) ? 1 : 2;
            // 初次新增 或者 企业签约 保存入住人
            if (Check.NuNStr(param.getOldUid()) ||
                    CustomerTypeEnum.ENTERPRICE.getCode() == rentContractEntity.getCustomerType() ||
                    CustomerTypeEnum.EPS.getCode() == rentContractEntity.getCustomerType()) {
                // 保存入住人合住人
                rentCheckinPersonServiceImpl.saveAndDeteletCheckinAndSharer(
                        param.getRentedetailId(),
                        param.getOldUid(),
                        rentCheckinPersonEntity,
                        sharerEntities
                );

                // 更新rentdetail表里的uid
                RentDetailEntity rentDetailEntity = new RentDetailEntity();
                rentDetailEntity.setId(param.getRentedetailId());
                rentDetailEntity.setPersonUid(rentCheckinPersonEntity.getUid());
                rentContractServiceImpl.updateUidByContractIdAndRoomId(rentDetailEntity);

            } else {
                // 个人签约不允许更换入住人
                flag = -1;
            }
            dto.putValue("flag", flag);
        } catch (Exception e) {
            message = e.getMessage();
            code = DataTransferObject.ERROR;
            log.error("params : {}", paramJson);
            log.error(e.getMessage(), e);
        } finally {
            dto.setErrCode(code);
            dto.setMsg(message);
        }

        return dto.toJsonString();
    }

    /**
     * 查询入住人根据合同Id
     *
     * @param contractId
     * @return
     * @throws Exception
     */
    @Override
    public String findByContractId(String contractId) throws Exception {
        LogUtil.info(log, "【findByContractId】参数para={}", contractId);
        DataTransferObject dto = new DataTransferObject();
        RentCheckinPersonEntity personEntity = rentCheckinPersonServiceImpl.findCheckinPersonByContractId(contractId);
        dto.putValue("personEntity", RentCheckinPersonDto.of(personEntity));
        return dto.toJsonString();
    }

    /* (non-Javadoc)
     * @see com.ziroom.zrp.service.trading.api.RentCheckinService#searchByCriteria(java.lang.String)
     */
    @Override
    public String searchByCriteria(String paramJson) {
        log.info("searchByCriteria方法参数："+paramJson);
        DataTransferObject  dto=new  DataTransferObject();
        try {
            RentCheckinPersonSearchDto searchDto=JsonEntityTransform.json2Object(paramJson, RentCheckinPersonSearchDto.class);
            PagingResult<RentCheckinPersonVo> result= rentCheckinPersonServiceImpl.searchByCriteria(searchDto);
            dto.putValue("list", result.getRows());
            dto.putValue("count", result.getTotal());
        } catch (Exception e) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误！");
            log.error(e.getMessage(), e);
        }
        return dto.toJsonString();
    }

    /**
     * 删除合住人
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月07日 14:26
     */
    @Override
    public String delSharer(String paramJson) {
        LogUtil.info(log, "【delSharer】参数para={}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        SharerEntity sharerEntity = JsonEntityTransform.json2Object(paramJson, SharerEntity.class);
        if (Check.NuNObj(sharerEntity) || Check.NuNStr(sharerEntity.getFid())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        sharerEntity.setFisdel(IsDelEnum.YES.getCode());
        sharerEntity.setFvalid(ValidEnum.NO.getCode());
        int count = sharerServiceImpl.updateByFid(sharerEntity);
        dto.putValue("count", count);
        return dto.toJsonString();
    }

    /**
     * 添加合住人
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月07日 14:27
     */
    @Override
    public String saveSharer(String paramJson) {
        LogUtil.info(log,"【saveOrUpdateSharer】参数para={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        SharerEntity sharerEntity = JsonEntityTransform.json2Object(paramJson, SharerEntity.class);
        if (Check.NuNStr(sharerEntity.getFname())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("真实姓名为空");
            return dto.toJsonString();
        }
        if (Check.NuNObj(sharerEntity.getFcerttype())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("证件类型为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(sharerEntity.getFcertnum())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("证件号为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(sharerEntity.getFmobile())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("联系方式为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(sharerEntity.getFcontractid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("合同id为空");
            return dto.toJsonString();
        }

        String fid = sharerServiceImpl.saveSharer(sharerEntity);
        dto.putValue("fid",fid);

        return dto.toJsonString();
    }

    /**
     * 历史入住人
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月07日 16:22
     */
    @Override
    public String selectHistoryPersonAndSharer(String paramJson) {
        LogUtil.info(log,"【selectHistoryPersonAndSharer】参数para={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        RentCheckinPersonSearchDto param = JsonEntityTransform.json2Object(paramJson, RentCheckinPersonSearchDto.class);

        if(Check.NuNObj(param) || Check.NuNStr(param.getContractId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }
        if(Check.NuNObj(param.getPage())){
            param.setPage(1);
        }
        if(Check.NuNObj(param.getRows())){
            param.setRows(10);
        }

        PagingResult<PersonAndSharerPojo> result = rentCheckinPersonServiceImpl.selectHistoryPersonAndSharer(param);
        dto.putValue("list", result.getRows());
        dto.putValue("count", result.getTotal());

        return dto.toJsonString();
    }

    /**
     * 根据合同与uid查询入住人详情
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月08日 11:00
     */
    @Override
    public String selectHistoryCheckinPerson(String paramJson) {
        LogUtil.info(log,"【selectHistoryCheckinPerson】参数para={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        RentCheckinPersonDto param = JsonEntityTransform.json2Object(paramJson, RentCheckinPersonDto.class);
        if(Check.NuNObj(param) || Check.NuNObj(param.getId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }

        RentCheckinPersonEntity rentCheckinPersonEntity = rentCheckinPersonServiceImpl.selectHistoryCheckinPerson(param.getId());
        dto.putValue("data", rentCheckinPersonEntity);
        return dto.toJsonString();
    }

    /**
     * 查询合住人详情
     *
     * @param paramJson
     * @return
     * @author zhangyl2
     * @created 2017年12月08日 11:00
     */
    @Override
    public String selectSharer(String paramJson) {
        LogUtil.info(log,"【selectSharer】参数para={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        SharerDto param = JsonEntityTransform.json2Object(paramJson, SharerDto.class);
        if(Check.NuNObj(param) || Check.NuNStr(param.getFid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数错误");
            return dto.toJsonString();
        }
        SharerEntity sharerEntity = sharerServiceImpl.findByFid(param.getFid());
        dto.putValue("data", sharerEntity);
        return dto.toJsonString();
    }
    /**
     * 查询客户有效的合同
     * @param uid 客户标识
     * @return
     * @author cuigh6
     * @Date 2017年12月11日
     */
    @Override
    public String getValidContractList(String uid) {
        LogUtil.info(log, "【getValidContractList】请求参数:uid={}", uid);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(uid)) {
            dto.setMsg("uid为空");
            dto.setErrCode(DataTransferObject.ERROR);
            return dto.toJsonString();
        }
        try {
	        List<EffectiveContractDto> list = new ArrayList<>();
	        // 查询已签约状态的合同列表
	        List<RentContractEntity> rentContractEntities = this.rentCheckinPersonServiceImpl.findCheckInPersonContractList(uid);
	        // 校验房间是否绑定智能锁
	        rentContractEntities.forEach(v->{
	            String room = roomService.getRoomByFid(v.getRoomId());
	            RoomInfoEntity roomInfoEntity = JsonEntityTransform.json2DataTransferObject(room).parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
	            });
	            String roomExt=roomService.getRoomInfoExtByRoomId(v.getRoomId());
	            RoomInfoExtEntity roomInfoExtEntity=null;
				try {
					roomInfoExtEntity=SOAResParseUtil.getValueFromDataByKey(roomExt, "roomExt", RoomInfoExtEntity.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
	            EffectiveContractDto contractDto = new EffectiveContractDto();
	            contractDto.setRoomId(v.getRoomId());
	            contractDto.setRoomCode(v.getHouseRoomNo());
	            contractDto.setCityCode(v.getCityid());
	            contractDto.setContractCode(v.getConRentCode());
	            contractDto.setProjectId(v.getProjectId());
	            contractDto.setProjectName(v.getProName());
	            contractDto.setCustomerMobile(v.getCustomerMobile());
	            contractDto.setCustomerName(v.getCustomerName());
	            contractDto.setBusiLine("ZRYU01");// todo 优化
	            contractDto.setIsSmartLock(YesOrNoEnum.NO.getCode());
	            contractDto.setIsSmartWatermeter(YesOrNoEnum.NO.getCode());
	            contractDto.setIsSmartWattmeter(YesOrNoEnum.NO.getCode());
	            contractDto.setCustomerType(v.getCustomerType());
	            contractDto.setContractId(v.getContractId());
	            //判断是否绑定智能设备
	            if (roomInfoEntity.getIsBindLock() == RoomSmartLockBindEnum.YBD.getCode()) {//判断是否绑定智能锁
	            	contractDto.setIsSmartLock(YesOrNoEnum.YES.getCode());
	            }
	            if(!Check.NuNObj(roomInfoExtEntity)){
	            	if(roomInfoExtEntity.getIsBindAmmeter()==YesOrNoEnum.YES.getCode()){//判断是否绑定智能电表
	            		contractDto.setIsSmartWattmeter(YesOrNoEnum.YES.getCode());
	            	}
	            	if(roomInfoExtEntity.getIsBindWatermeter()==YesOrNoEnum.YES.getCode()){//判断是否绑定智能水表
	            		contractDto.setIsSmartWatermeter(YesOrNoEnum.YES.getCode());
	            	}
	            }
	            //查询城市名称
	            if(!Check.NuNStr(v.getCityid())){
	            	String cityJson=cityService.findById(v.getCityid());
	            	try {
						CityEntity cityEntity=SOAResParseUtil.getValueFromDataByKey(cityJson, "data", CityEntity.class);
						if(!Check.NuNObj(cityEntity)){
							contractDto.setCityCode(cityEntity.getCitycode());
							contractDto.setCityName(cityEntity.getCityname());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	            list.add(contractDto);
	        });
	        dto.putValue("list", list);
        } catch (Exception e) {
        	LogUtil.info(log, "【getValidContractList】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
		}
        return dto.toJsonString();
    }

    @Override
    public String validSignPerson(String contractId){
        LogUtil.info(log, "【validSignPerson】入参:{}", contractId);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(contractId)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
        }
        try{
            RentCheckinPersonEntity rentCheckinPersonEntity = rentCheckinPersonServiceImpl
                    .findCheckinPersonByContractId(contractId);
            LogUtil.info(log, "【validSignPerson】查询合同信息返回:{}", JSONObject.toJSONString(rentCheckinPersonEntity));
            if(Check.NuNObj(rentCheckinPersonEntity) || Check.NuNObj(rentCheckinPersonEntity.getCertType())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到签约人信息！");
                return dto.toJsonString();
            }
            int isTrue = 0;
            if(!Check.NuNObj(rentCheckinPersonEntity.getCertType()) && CertTypeEnum.CERTCARD.getCode() == rentCheckinPersonEntity.getCertType()){
                //只验证身份证，其他类型默认通过
                isTrue= IdcardValidatorUtils.isReasonableAge(rentCheckinPersonEntity.getCertNum());
            }else{
                isTrue = 1;
            }

            if(isTrue != 1){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg(ContractMsgConstant.SIGN_PERSON_AGE_LIMIT);
                return dto.toJsonString();
            }else{
                return dto.toJsonString();
            }
        }catch(Exception e){
            LogUtil.info(log, "【validSignPerson】出错:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
        }
    }

    /**
     * 查询合同入住人信息
     *
     * @param paramJson 合同标识
     * @return
     * @author cuigh6
     * @Date 2017年10月
     *
     */
    @Override
    public String findCheckInPersonInfo(String paramJson) {
        LogUtil.info(log,"【findCheckInPersonInfo】入参={}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        RentCheckinPersonEntity entity = JsonEntityTransform.json2Entity(paramJson, RentCheckinPersonEntity.class);
        if (Check.NuNObj(entity) || Check.NuNStr(entity.getContractId())) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        RentCheckinPersonEntity checkinPersonEntity;
        if(Check.NuNStr(entity.getUid())){
            checkinPersonEntity = rentCheckinPersonServiceImpl.findCheckinPersonByContractId(entity.getContractId());
        }else{
            checkinPersonEntity = rentCheckinPersonServiceImpl.findCheckinPerson(entity.getContractId(), entity.getUid());
        }
        List<SharerEntity> sharerEntities = shareServiceImpl.listSharerByContractId(entity.getContractId());
        dto.putValue("rentCheckInPerson", checkinPersonEntity);
        dto.putValue("sharers", sharerEntities);
        return dto.toJsonString();
    }
}
