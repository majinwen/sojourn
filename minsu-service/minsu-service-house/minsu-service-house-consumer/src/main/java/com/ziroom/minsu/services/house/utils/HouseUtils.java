package com.ziroom.minsu.services.house.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryLogEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.entity.RoomMsgVo;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * <p>房源工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class HouseUtils {




	private static Logger logger = LoggerFactory.getLogger(HouseUtils.class);
	/**
	 * 
	 * 生成房源houseSn或者房间roomSn
	 * 说明：
	 * 1.添加房源基础信息时 生成houseSn
	 * 2.在添加房间时，生成roomSn
	 * 3.在修改房源物理信息时候，修改houseSn和roomSn
	 *
	 * @author yd
	 * @created 2016年6月18日 上午11:04:39
	 *
	 * @param nationCode
	 * @param cityCode
	 * @param rendWay
	 * @param oldhouseSn
	 * @return
	 */
	public static String getHouseOrRoomSn(String nationCode,String cityCode,int rentWay,String oldhouseSn){


		StringBuffer houseSn  = new StringBuffer("");
		if((rentWay!=RentWayEnum.HOUSE.getCode()&&rentWay!=RentWayEnum.ROOM.getCode())
				||Check.NuNStr(cityCode)){
			return houseSn.toString();
		}

		if(!Check.NuNStr(oldhouseSn)){
			return oldhouseSn;
		}

		//4位城市编码
		if(!Check.NuNStr(cityCode)){
			if(cityCode.length()>=4){//够四位
				houseSn.append(cityCode.substring(0, 4));
			}else {
				houseSn.append(cityCode)
				.append(RandomStringUtils.randomNumeric(4-cityCode.length()));//补够4位
			}

		}else if(!Check.NuNStr(nationCode)){
			if(nationCode.length()>=4){//够四位
				houseSn.append(nationCode.substring(0, 4));
			}else {
				houseSn.append(nationCode)
				.append(RandomStringUtils.randomNumeric(4-nationCode.length()));//补够4位
			}
		}else{
			houseSn.append(RandomStringUtils.randomNumeric(4));
		}

		houseSn.append(get8RandomNumber());

		if(rentWay==RentWayEnum.HOUSE.getCode()){
			houseSn.append(HouseConstant.HOUSE_RENTWAY_ID);
		}
		if(rentWay==RentWayEnum.ROOM.getCode()){
			houseSn.append(HouseConstant.ROOM_RENTWAY_ID);
		}
		return houseSn.toString();
	}


	/**
	 * 
	 * 获取8位随机数
	 *
	 * @author yd
	 * @created 2017年7月5日 上午10:21:11
	 *
	 * @return
	 */
	public static String get8RandomNumber(){
		StringBuffer result = new StringBuffer();
		String dateStr = System.currentTimeMillis()+"";
		String dateHashStr = MurmurHash3.murmurhash3_x86_32(dateStr.getBytes(), 6, 2, new Random().nextInt())+"";
		if(!Check.NuNStr(dateHashStr)){
			for(int i=0;i<dateHashStr.length();i++){
				if('0'<=dateHashStr.charAt(i) && '9'>=dateHashStr.charAt(i) ){
					result.append(dateHashStr.charAt(i));
				}
				if(result.length()>=4){
					break;
				}
			}
		}
		if(result.length()<4){
			result.append(RandomStringUtils.randomNumeric(4-result.length()));//补够4位 
		}
		result.append(RandomStringUtils.randomNumeric(4));	

		return result.toString();
	}

	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 对比  房源基本信息实体
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param obj1
	 * @param obj2
	 */
	public static void contrastHouseBaseMsgObj(Object newObj, Object oldObj,List<HouseUpdateHistoryLogEntity> list) {  
		if (oldObj instanceof HouseBaseMsgEntity && newObj instanceof HouseBaseMsgEntity&&list!=null) {  
			HouseBaseMsgEntity pojo1 = (HouseBaseMsgEntity) newObj;  
			HouseBaseMsgEntity pojo2 = (HouseBaseMsgEntity) oldObj;  
			try {  
				Class<?> clazz = pojo1.getClass();  
				Field[] fields = pojo1.getClass().getDeclaredFields();  
				for (Field field : fields) {  
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method getMethod = clazz.getMethod(methodName);
					Object o1 = getMethod.invoke(pojo1);  
					Object o2 = getMethod.invoke(pojo2);  
					
					//如果 新数据 为null 不做处理
					if(o1 == null){
						continue;
					}
					String newS = o1.toString();
					String olsS = o2 == null ? "" : o2.toString();//避免空指针异常  
					if (!newS.equals(olsS)) {
						HouseUpdateLogEnum houseUpdateLogEnum  = HouseUpdateLogEnum.getHouseUpdateLogEnum(HouseUpdateLogEnum.House_Base_Msg_House_Sn.getTableName(), field.getName());
						if(Check.NuNObj(houseUpdateLogEnum)){
							continue;
						}
						HouseUpdateHistoryLogEntity  houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(houseUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseMsgEntity.class,houseUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(houseUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房源更新记录-HouseBaseMsgEntity异常】e={}", e);
			}  
		}  
	} 

	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 房间基本信息实体
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param newObj
	 * @param oldObj
	 */
	public static void contrastHouseRoomMsgObj(Object newObj, Object oldObj,List<HouseUpdateHistoryLogEntity> list,int rentWay) {  
		if (oldObj instanceof HouseRoomMsgEntity && newObj instanceof HouseRoomMsgEntity&&list!=null) {  
			HouseRoomMsgEntity pojo1 = (HouseRoomMsgEntity) newObj;  
			HouseRoomMsgEntity pojo2 = (HouseRoomMsgEntity) oldObj;  
			try {  
				Class<?> clazz = pojo1.getClass();  
				Field[] fields = pojo1.getClass().getDeclaredFields();  
				for (Field field : fields) {  
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method getMethod = clazz.getMethod(methodName);
					Object o1 = getMethod.invoke(pojo1);  
					Object o2 = getMethod.invoke(pojo2);  
					//如果 新数据 为null 不做处理
					if(o1 == null){
						continue;
					}
					String newS = o1.toString();
					String olsS = o2 == null ? "" : o2.toString();//避免空指针异常  
					if (!newS.equals(olsS)) {
						HouseUpdateLogEnum houseUpdateLogEnum  = HouseUpdateLogEnum.getHouseUpdateLogEnum(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getTableName(), field.getName());
						if(Check.NuNObj(houseUpdateLogEnum)){
							continue;
						}
						HouseUpdateHistoryLogEntity  houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(houseUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseRoomMsgEntity.class,houseUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(houseUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						if(rentWay == RentWayEnum.ROOM.getCode()){
							houseUpdateHistoryLog.setRoomFid(pojo2.getFid());
						}
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房间更新记录-HouseRoomMsgEntity异常】e={}", e);
			}  
		}  
	}



	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 房源描述基本信息实体
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param newObj
	 * @param oldObj
	 */
	public static void contrastHouseDescObj(Object newObj, Object oldObj,List<HouseUpdateHistoryLogEntity> list) {  
		if (oldObj instanceof HouseDescEntity && newObj instanceof HouseDescEntity&&list!=null) {  
			HouseDescEntity pojo1 = (HouseDescEntity) newObj;  
			HouseDescEntity pojo2 = (HouseDescEntity) oldObj;  
			try {  
				Class<?> clazz = pojo1.getClass();  
				Field[] fields = pojo1.getClass().getDeclaredFields();  
				for (Field field : fields) {  
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method getMethod = clazz.getMethod(methodName);
					Object o1 = getMethod.invoke(pojo1);  
					Object o2 = getMethod.invoke(pojo2);  
					//如果 新数据 为null 不做处理
					if(o1 == null){
						continue;
					}
					String newS = o1.toString();
					String olsS = o2 == null ? "" : o2.toString();//避免空指针异常  
					if (!newS.equals(olsS)) {
						HouseUpdateLogEnum houseUpdateLogEnum  = HouseUpdateLogEnum.getHouseUpdateLogEnum(HouseUpdateLogEnum.House_Desc_House_Desc.getTableName(), field.getName());
						if(Check.NuNObj(houseUpdateLogEnum)){
							continue;
						}
						HouseUpdateHistoryLogEntity  houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(houseUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseDescEntity.class,houseUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(houseUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房源描述更新记录-HouseDescEntity异常】e={}", e);
			}  
		}  
	}


	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 房源扩展信息实体
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param newObj
	 * @param oldObj
	 */
	public static void contrastHouseBaseExtObj(Object newObj, Object oldObj,List<HouseUpdateHistoryLogEntity> list) {
		if (oldObj instanceof HouseBaseExtEntity && newObj instanceof HouseBaseExtEntity&&list!=null) {  
			HouseBaseExtEntity pojo1 = (HouseBaseExtEntity) newObj;  
			HouseBaseExtEntity pojo2 = (HouseBaseExtEntity) oldObj;  
			try {  
				Class<?> clazz = pojo1.getClass();  
				Field[] fields = pojo1.getClass().getDeclaredFields();  
				for (Field field : fields) {  
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method getMethod = clazz.getMethod(methodName);
					Object o1 = getMethod.invoke(pojo1);  
					Object o2 = getMethod.invoke(pojo2);  
					//如果 新数据 为null 不做处理
					if(o1 == null){
						continue;
					}
					String newS = o1.toString();
					String olsS = o2 == null ? "" : o2.toString();//避免空指针异常  
					if (!newS.equals(olsS)) {
						HouseUpdateLogEnum houseUpdateLogEnum  = HouseUpdateLogEnum.getHouseUpdateLogEnum(HouseUpdateLogEnum.House_Base_Ext_Deposit_Rules_Code.getTableName(), field.getName());
						if(Check.NuNObj(houseUpdateLogEnum)){
							continue;
						}
						HouseUpdateHistoryLogEntity  houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(houseUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseExtEntity.class,houseUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(houseUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存扩展信息更新记录-HouseBaseExtEntity异常】e={}", e);
			}  
		}  
	}
	
	
	

	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 房源物理信息实体
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param newObj
	 * @param oldObj
	 */
	public static void contrastHousePhyMsgObj(Object newObj, Object oldObj,List<HouseUpdateHistoryLogEntity> list) {
		if (oldObj instanceof HousePhyMsgEntity && newObj instanceof HousePhyMsgEntity&&list!=null) {  
			HousePhyMsgEntity pojo1 = (HousePhyMsgEntity) newObj;  
			HousePhyMsgEntity pojo2 = (HousePhyMsgEntity) oldObj;  
			try {  
				Class<?> clazz = pojo1.getClass();  
				Field[] fields = pojo1.getClass().getDeclaredFields();  
				for (Field field : fields) {  
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method getMethod = clazz.getMethod(methodName);
					Object o1 = getMethod.invoke(pojo1);  
					Object o2 = getMethod.invoke(pojo2);  
					//如果 新数据 为null 不做处理
					if(o1 == null){
						continue;
					}
					String newS = o1.toString();
					String olsS = o2 == null ? "" : o2.toString();//避免空指针异常  
					if (!newS.equals(olsS)) {
						HouseUpdateLogEnum houseUpdateLogEnum  = HouseUpdateLogEnum.getHouseUpdateLogEnum(HouseUpdateLogEnum.House_Phy_Msg_Nation_Code.getTableName(), field.getName());
						if(Check.NuNObj(houseUpdateLogEnum)){
							continue;
						}
						HouseUpdateHistoryLogEntity  houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(houseUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HousePhyMsgEntity.class,houseUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(houseUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房源物理信息更新记录-HousePhyMsgEntity异常】e={}", e);
			}  
		}  
	}
	
	
	

	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 房源配置信息实体
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param newObj
	 * @param oldObj
	 */
	public static void contrastHouseConfMsgObj(Object newObj, Object oldObj,List<HouseUpdateHistoryLogEntity> list) {
		if (oldObj instanceof HouseConfMsgEntity && newObj instanceof HouseConfMsgEntity&&list!=null) {  
			HouseConfMsgEntity pojo1 = (HouseConfMsgEntity) newObj;  
			HouseConfMsgEntity pojo2 = (HouseConfMsgEntity) oldObj;  
			try {  
				Class<?> clazz = pojo1.getClass();  
				Field[] fields = pojo1.getClass().getDeclaredFields();  
				for (Field field : fields) {  
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method getMethod = clazz.getMethod(methodName);
					Object o1 = getMethod.invoke(pojo1);  
					Object o2 = getMethod.invoke(pojo2);  
					//如果 新数据 为null 不做处理
					if(o1 == null){
						continue;
					}
					String newS = o1.toString();  
					String olsS = o2 == null ? "" : o2.toString();//避免空指针异常  
					if (!newS.equals(olsS)) {
						HouseUpdateLogEnum houseUpdateLogEnum  = HouseUpdateLogEnum.getHouseUpdateLogEnum(HouseUpdateLogEnum.House_Conf_Msg_Dic_Code.getTableName(), field.getName());
						if(Check.NuNObj(houseUpdateLogEnum)){
							continue;
						}
						HouseUpdateHistoryLogEntity  houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(houseUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseConfMsgEntity.class,houseUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(houseUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房配置理信息更新记录-HouseConfMsgEntity异常】e={}", e);
			}  
		}  
	}
	
	
	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 房间扩展信息
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param newObj
	 * @param oldObj
	 */
	public static void contrastHouseRoomExtObj(Object newObj, Object oldObj,List<HouseUpdateHistoryLogEntity> list) {
		if (oldObj instanceof HouseRoomExtEntity && newObj instanceof HouseRoomExtEntity&&list!=null) {  
			HouseRoomExtEntity pojo1 = (HouseRoomExtEntity) newObj;  
			HouseRoomExtEntity pojo2 = (HouseRoomExtEntity) oldObj;  
			try {  
				Class<?> clazz = pojo1.getClass();  
				Field[] fields = pojo1.getClass().getDeclaredFields();  
				for (Field field : fields) {  
					String fieldName = field.getName();
					if ("serialVersionUID".equals(fieldName)) {
						continue;
					}
					String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1, fieldName.length());
					Method getMethod = clazz.getMethod(methodName);
					Object o1 = getMethod.invoke(pojo1);  
					Object o2 = getMethod.invoke(pojo2);  
					//如果 新数据 为null 不做处理
					if(o1 == null){
						continue;
					}
					String newS = o1.toString();  
					String olsS = o2 == null ? "" : o2.toString();//避免空指针异常  
					if (!newS.equals(olsS)) {
						HouseUpdateLogEnum houseUpdateLogEnum  = HouseUpdateLogEnum.getHouseUpdateLogEnum(HouseUpdateLogEnum.House_Room_Ext_Order_Type.getTableName(), field.getName());
						if(Check.NuNObj(houseUpdateLogEnum)){
							continue;
						}
						HouseUpdateHistoryLogEntity  houseUpdateHistoryLog = new HouseUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(houseUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseRoomExtEntity.class,houseUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(houseUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setRoomFid(pojo2.getRoomFid());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房间扩展信息更新记录-HouseRoomExtEntity异常】e={}", e);
			}  
		}  
	}
	
	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		/*HouseBaseMsgEntity newObj = new HouseBaseMsgEntity();
		
		HouseBaseMsgEntity oldObj = new HouseBaseMsgEntity();
		
		List<HouseUpdateHistoryLogEntity> list = new ArrayList<HouseUpdateHistoryLogEntity>();
		contrastHouseBaseMsgObj(newObj, oldObj, list);*/
		RoomMsgVo roomMsgVo = new RoomMsgVo();
		roomMsgVo.setRoomName("1111111");
		roomMsgVo.setHouseRoomFid("2222222222222222222222222");
		roomMsgVo.setHouseBaseFid("33333333333333333333");
		List<HouseUpdateFieldAuditManagerEntity> listHouseUpdateFieldAuditManagerEntity = new ArrayList<HouseUpdateFieldAuditManagerEntity>();
		HouseUpdateFieldAuditManagerEntity house = new HouseUpdateFieldAuditManagerEntity();
		listHouseUpdateFieldAuditManagerEntity.add(house);
		house.setFid("3c44d62c9b837a173f2e18fb75bde3f7");
		house.setFieldPath("com.ziroom.minsu.entity.house.HouseRoomMsgEntity.roomName");
		house.setFieldDesc("房间名称");
		
		HouseRoomMsgEntity houseRoNew = new HouseRoomMsgEntity();
		BeanUtils.copyProperties(roomMsgVo, houseRoNew);
		FilterAuditField(houseRoNew, listHouseUpdateFieldAuditManagerEntity);
		BeanUtils.copyProperties(houseRoNew, roomMsgVo);
		System.out.println(JsonEntityTransform.Object2Json(roomMsgVo));
		
	}

	/**
	 * @description: 过滤房源需要审核的字段，将审核字段设置为null
	 * @author: lusp
	 * @date: 2017/8/8 14:26
	 * @params: obj,houseUpdateFieldAuditManagerEntities
	 * @return: obj
	 */
	public static <T extends Object> T FilterAuditField(T obj,List<HouseUpdateFieldAuditManagerEntity> houseUpdateFieldAuditManagerEntities)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException {
		if(Check.NuNCollection(houseUpdateFieldAuditManagerEntities)||Check.NuNObj(obj)){
			return obj;
		}
		Class<?> clazz = obj.getClass();
		String className = clazz.getName();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields){
			String fieldName = field.getName();
			if("serialVersionUID".equals(fieldName)){
				continue;
			}
			String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			Class<?> type = field.getType();
			Method method = clazz.getMethod(methodName,type);
			String fieldFullName = className+"."+fieldName;
			for(HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManagerEntity:houseUpdateFieldAuditManagerEntities){
				if(houseUpdateFieldAuditManagerEntity.getFieldPath().trim().equals(fieldFullName.trim())){
					method.invoke(obj,new Object[]{null});
					break;
				}
			}
		}
		return obj;
	}


	/**
	 * @description: 过滤房源待审核的字段，将待审核记录中的newValue赋给对应实体
	 * @author: lusp
	 * @date: 2017/8/9 22:15
	 * @params: obj,houseUpdateFieldAuditManagerEntities
	 * @return: obj
	 */
	public static <T extends Object> T FilterNotAuditField(T obj,List<HouseFieldAuditLogVo> houseFieldAuditLogVos)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException,ParseException {
		if(Check.NuNObj(houseFieldAuditLogVos)||Check.NuNObj(obj)){
			return obj;
		}
		Class<?> clazz = obj.getClass();
		String className = clazz.getName();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields){
			String fieldName = field.getName();
			if("serialVersionUID".equals(fieldName)){
				continue;
			}
			String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			Class<?> type = field.getType();
			Method method = clazz.getMethod(methodName,type);
			String fieldFullName = className+"."+fieldName;
			for(HouseFieldAuditLogVo houseFieldAuditLogVo:houseFieldAuditLogVos){
				if(houseFieldAuditLogVo.getFieldPath().trim().equals(fieldFullName.trim())){
					method.invoke(obj, StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
					break;
				}
			}
		}
		return obj;
	}


}
