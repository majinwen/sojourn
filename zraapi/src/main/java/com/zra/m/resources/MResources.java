package com.zra.m.resources;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.business.entity.dto.BusinessOrderDto;
import com.zra.business.logic.BusinessLogic;
import com.zra.common.dto.appbase.AppBaseDto;
import com.zra.common.enums.BoSourceType;
import com.zra.common.utils.CommonUtil;
import com.zra.common.utils.PicUtils;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.SysConstant;
import com.zra.evaluate.entity.ZoEvaluationEntity;
import com.zra.evaluate.entity.mapper.ZoEvaluationItemEntity;
import com.zra.evaluate.logic.EvaluateLogic;
import com.zra.house.entity.ProAndHtInfoEntity;
import com.zra.house.entity.dto.*;
import com.zra.house.logic.HouseTypeLogic;
import com.zra.house.logic.ProjectLogic;
import com.zra.house.logic.RoomInfoLogic;
import com.zra.item.entity.MItemIconEntity;
import com.zra.item.logic.ItemLogic;
import com.zra.m.entity.dto.*;
import com.zra.m.tools.NetUtilForM;
import com.zra.m.tools.TokenResultDto;
import com.zra.m.tools.TokenUrlConstant;
import com.zra.rentcontract.logic.RentContractLogic;
import com.zra.system.logic.UserAccountLogic;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Author: wangxm113
 * CreateDate: 2017/3/10.
 */
@Component
public class MResources {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(MResources.class);

    @Autowired
    private ProjectLogic projectLogic;

    @Autowired
    private BusinessLogic businessLogic;

    @Autowired
    private RentContractLogic rentContractLogic;

    @Autowired
    private EvaluateLogic evaluateLogic;

    @Autowired
    private RoomInfoLogic roomInfoLogic;

    @Autowired
    private HouseTypeLogic houseTypeLogic;

    @Autowired
    private ItemLogic itemLogic;

    @Autowired
    private UserAccountLogic userAccountLogic;

    public List<NewProjectListDto> getNewProjectList(AppBaseDto dto) {
        List<NewProjectListDto> newProjectList = projectLogic.getNewProjectList(dto);
        if (newProjectList != null) {
            for (NewProjectListDto projectListDto : newProjectList) {
                projectListDto.setPriceTag("¥");
            }
        }
        return newProjectList;
    }

    public NewProjectDetailDto getNewProjectDetail(SearchReqDto dto) {
        NewProjectDetailDto newProjectDetail = projectLogic.getNewProjectDetail(dto);
        if (newProjectDetail != null) {
            newProjectDetail.setPriceTag("¥");
        }
        return newProjectDetail;
    }

    public MRoomDto findHouseTypeDetail(HouseTypeReqDto dto) {
        MRoomDto result = new MRoomDto();
        HouseTypeDetailDto detailDto = projectLogic.findHouseTypeDetail(dto);
        //added by wangxm113 start
        SearchRoomReqDto roomReqDto = new SearchRoomReqDto();
        roomReqDto.setHouseTypeId(dto.getHouseTypeId());
        roomReqDto.setPageSize(5);//最多显示5条
        RoomPriceDetailDto detailByCondition = projectLogic.getRoomDetailByCondition(roomReqDto);
        PriceDto priceDto = detailByCondition.getPriceDto();
        //added by wangxm113 end
//        PriceDto priceDto = roomInfoLogic.findMaxAndMinRoomPrice(null, dto.getHouseTypeId(), null, null, null, null, null, null, null, null);
        if (priceDto.getMaxPrice() == 0 && priceDto.getMinPrice() == 0) {
            priceDto = roomInfoLogic.findMaxAndMinRoomPriceOther(dto.getHouseTypeId());
        }
        detailDto.setMaxPrice(CommonUtil.doubleToString(priceDto.getMaxPrice()));
        detailDto.setMinPrice(CommonUtil.doubleToString(priceDto.getMinPrice()));
        //根据房型id获取电话
        String phone = houseTypeLogic.getPhoneByHtId(dto.getHouseTypeId());
        detailDto.setPhone(phone);
        //处理图标
        List<HouseConfigDto> houseConfigDtoList = detailDto.getHouseConfigDtoList();
        Map<String, HouseConfigDto> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (HouseConfigDto configDto : houseConfigDtoList) {
            String itemId = configDto.getItemId();
            map.put(itemId, configDto);
            sb.append("'").append(itemId).append("',");
        }
        if (sb.length() > 0) {
            sb = sb.deleteCharAt(sb.length() - 1);
            List<MItemIconEntity> list = itemLogic.getItemImgUrl(sb.toString());
            for (MItemIconEntity entity : list) {
                map.get(entity.getItemId()).setImgUrl(PicUtils.wrapPicUrl(entity.getIconURL()));
            }
        }
        result.setHouseTypeDetailDto(detailDto);
        result.setRoomPriceDetailDto(detailByCondition);
        return result;
    }

    public MBusinessEntryDto getProAndHtInfo() {
        MBusinessEntryDto result = new MBusinessEntryDto();
        List<ProAndHtInfoEntity> proAndHtList = projectLogic.getProAndHtInfo();
        Map<String, MProjectInfoDto> proMap = new HashMap<>();
        Map<String, List<MHouseTypeDto>> map = new HashMap<>();
        for (ProAndHtInfoEntity entity : proAndHtList) {
            //项目信息
            String proId = entity.getProId();
            MProjectInfoDto projectInfoDto = proMap.get(proId);
            if (projectInfoDto == null) {
                MProjectInfoDto proDto = new MProjectInfoDto();
                proDto.setProId(proId);
                proDto.setProName(entity.getProName());
                proMap.put(proId, proDto);
            }
            //户型信息
            MHouseTypeDto dto = new MHouseTypeDto();
            dto.sethTBid(entity.getHtId());
            dto.sethTName(entity.getHtName());
            List<MHouseTypeDto> dtoList = map.get(proId);
            if (dtoList == null) {
                dtoList = new ArrayList<>();
                dtoList.add(dto);
                map.put(proId, dtoList);
            } else {
                dtoList.add(dto);
            }
        }
        //封装结果
        List<MProjectInfoDto> proList = new ArrayList<>();
        for (Map.Entry<String, List<MHouseTypeDto>> entry : map.entrySet()) {
            String proId = entry.getKey();
            MProjectInfoDto projectInfoDto = proMap.get(proId);
            proList.add(projectInfoDto);
            projectInfoDto.setHouseTypeList(entry.getValue());
        }

        result.setProjectInfoList(proList);
        result.setCountryList(CommonUtil.getAllCountryNameList());
        result.setComeSource(PropUtils.getString("COME_SOURCE"));
        return result;
    }

    public void insertBusiness(BusinessOrderDto dto) {
        //根据token获取用户信息，如果传过来token则去获取用户uuid，失败也无需处理，因为无需登录也可以提交约看
        if (dto.getToken() != null && !dto.getToken().isEmpty()) {
            TokenResultDto userInfoByToken = NetUtilForM.getUserInfoByToken(dto.getToken());
            String uid = null;
            if (TokenUrlConstant.TOKEN_SUCCESS_CODE.equals(userInfoByToken.getCode())) {
                uid = userInfoByToken.getResp().getUid();
            }
            dto.setUuid(uid);
        }
        businessLogic.insertBusinessApplyFromMsite(dto);
    }

    public List<MZOProDto> getProAndZOInfo() {
        List<MZOProDto> proAndZOInfo = projectLogic.getProAndZOInfo();
        for (MZOProDto dto : proAndZOInfo) {
            dto.setProShowImg(PicUtils.wrapPicUrl(dto.getProShowImg()));
        }
        return proAndZOInfo;
    }

    public List<MZOListDto> getZOInfoList(String projectId) {
        int hasLabelsNum = Integer.valueOf(PropUtils.getString(SysConstant.M_ZO_HAS_LABEL_NUM));
        List<MZOListDto> zoInfoList = projectLogic.getZOInfoList(projectId);
        Iterator<MZOListDto> iterator = zoInfoList.iterator();
        while (iterator.hasNext()) {
            MZOListDto dto = iterator.next();
            //判断是否是zo
            boolean zo = userAccountLogic.isZOOther(dto.getEmployeeId());
            if (!zo) {
                iterator.remove();
                continue;
            }
            dto.setProId(projectId);
            dto.setZoBigImg(PicUtils.wrapPicUrl(dto.getZoBigImg()));
            //正面的标签(0:正面 1:反面 null:所有)
            List<ZOLabelDto> labelList = projectLogic.getHasLabelsByZOIdAndType(dto.getZoId(), "0", hasLabelsNum);
            dto.setPositiveLabelList(labelList);
        }
        return zoInfoList;
    }

    private <T> List<T> getRandomList(List<T> list, int num) {
        if (list != null && list.size() > num) {
            while (list.size() > num) {
                int randNumber = getRandom(0, list.size() - 1);
                list.remove(randNumber);
            }
            return list;
        } else {
            return list;
        }
    }

    private int getRandom(int min, int max) {
        Random random = new Random();
        int randNumber = random.nextInt(max - min + 1) + min;
        return randNumber;
    }

    public MZOListDto getZOLabelToEva(String token, String zoId) {
        //根据token获取用户信息
        TokenResultDto userInfoByToken = NetUtilForM.getUserInfoByToken(token);
        if (!TokenUrlConstant.TOKEN_SUCCESS_CODE.equals(userInfoByToken.getCode())) {
            throw new RuntimeException(userInfoByToken.getMessage());
        } else {//验证是否是自如寓客户
            getIfEvaluate(userInfoByToken.getResp().getPhone());
        }
        int labelsNum = Integer.valueOf(PropUtils.getString(SysConstant.M_ZO_LABEL_COUNT));
        MZOListDto dto = projectLogic.getZOInfoByZOId(zoId);
        dto.setZoSmallImg(PicUtils.wrapPicUrl(dto.getZoSmallImg()));
        List<ZOLabelDto> positiveList = projectLogic.getLabelsByType("0");//正面
        List<ZOLabelDto> negativeList = projectLogic.getLabelsByType("1");//负面
        List<ZOLabelDto> randomList = getRandomList(positiveList, labelsNum);
        List<ZOLabelDto> randomList1 = getRandomList(negativeList, labelsNum);
        dto.setPositiveLabelList(randomList);
        dto.setNegativeLabelList(randomList1);
        return dto;
    }

    public void getIfEvaluate(String phone) {
        Integer coun = rentContractLogic.getIfEvaluate(phone);
        if (coun == null || coun <= 0) {
            throw new RuntimeException("非自如寓客户，不能评价");
        }
    }

    public void saveEvaluate(MEvaluateSaveDto dto) {
        ZoEvaluationEntity zoEvaluationEntity = new ZoEvaluationEntity();
        //根据token获取用户信息
        TokenResultDto userInfoByToken = NetUtilForM.getUserInfoByToken(dto.getToken());
        if (!TokenUrlConstant.TOKEN_SUCCESS_CODE.equals(userInfoByToken.getCode())) {
            throw new RuntimeException(userInfoByToken.getMessage());
        } else {
            zoEvaluationEntity.setCusName(userInfoByToken.getResp().getUsername());
            zoEvaluationEntity.setCusPhone(userInfoByToken.getResp().getPhone());
        }

        List<String> negativeList = dto.getNegativeList();
        if (negativeList != null && !negativeList.isEmpty()) {
            String[] split = negativeList.get(0).split(",");
            negativeList.clear();
            for (String s : split) {
                negativeList.add(s);
            }
        }
        List<String> positiveList = dto.getPositiveList();
        if (positiveList != null && !positiveList.isEmpty()) {
            String[] split = positiveList.get(0).split(",");
            positiveList.clear();
            for (String s : split) {
                positiveList.add(s);
            }
        }
        if (negativeList == null) {
            negativeList = new ArrayList<>();
        }
        negativeList.addAll(positiveList);
        if(negativeList == null || negativeList.size() < 1){
            throw new RuntimeException("至少要选择一个标签！");
        }
        String cityId = projectLogic.getCityIdByProjectId(dto.getProId());
        String evaluateId = UUID.randomUUID().toString();
        zoEvaluationEntity.setCreateTime(new Date());
        zoEvaluationEntity.setUpdateTime(new Date());
        zoEvaluationEntity.setEvaContent(dto.getContent());
        zoEvaluationEntity.setId(evaluateId);
        zoEvaluationEntity.setIsdel("0");
        zoEvaluationEntity.setProjectId(dto.getProId());
        zoEvaluationEntity.setValid("1");
        zoEvaluationEntity.setProjectZoId(dto.getZoId());
        zoEvaluationEntity.setCityId(cityId);
        Integer i = evaluateLogic.saveEvaluate(zoEvaluationEntity);
        if (i == null || i != 1) {
            try {
                LOGGER.info("[M站提交评价]保存失败！参数信息:" + new ObjectMapper().writeValueAsString(dto));
            } catch (IOException e) {
                LOGGER.error("[M站提交评价]保存失败！Token:" + dto.getToken(), e);
            }
        }

        List<ZoEvaluationItemEntity> list = new ArrayList<>();
        for (String s : negativeList) {
            ZoEvaluationItemEntity zoEvaluationItemEntity = new ZoEvaluationItemEntity();
            zoEvaluationItemEntity.setId(UUID.randomUUID().toString());
            zoEvaluationItemEntity.setValid("1");
            zoEvaluationItemEntity.setCreateTime(new Date());
            zoEvaluationItemEntity.setIsDel("0");
            zoEvaluationItemEntity.setUpdateTime(new Date());
            zoEvaluationItemEntity.setLabelId(s);
            zoEvaluationItemEntity.setZoEvaId(evaluateId);
            zoEvaluationItemEntity.setCityId(cityId);
            list.add(zoEvaluationItemEntity);
        }

        i = evaluateLogic.saveEvaluateItem(list);
        if (i == null || i < 1) {
            LOGGER.info("[M站提交评价]保存评价标签失败！Token:" + dto.getToken());
        }
    }

    public void checkToken(String token) {
        //验证token
        TokenResultDto userInfoByToken = NetUtilForM.checkTokenIsValid(token);
        if (!TokenUrlConstant.TOKEN_SUCCESS_CODE.equals(userInfoByToken.getCode())) {
            throw new RuntimeException(userInfoByToken.getMessage());
        }
    }
}
