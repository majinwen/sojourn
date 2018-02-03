package com.ziroom.minsu.services.customer.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryLogEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.valenum.customer.CustomerUpdateLogEnum;

/**
 * 
 * <p>用户工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class CustomerUtils {




	private static Logger logger = LoggerFactory.getLogger(CustomerUtils.class);

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
	public static void contrastCustomerBaseMsgEntityObj(Object newObj, Object oldObj,List<CustomerUpdateHistoryLogEntity> list) {  
		if (oldObj instanceof CustomerBaseMsgEntity && newObj instanceof CustomerBaseMsgEntity&&list!=null) {  
			CustomerBaseMsgEntity pojo1 = (CustomerBaseMsgEntity) newObj;  
			CustomerBaseMsgEntity pojo2 = (CustomerBaseMsgEntity) oldObj;  
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
						CustomerUpdateLogEnum customerUpdateLogEnum  = CustomerUpdateLogEnum.getCustomerUpdateLogEnum(CustomerUpdateLogEnum.Customer_Base_Msg_NickName.getTableName(), field.getName());
						if(Check.NuNObj(customerUpdateLogEnum)){
							continue;
						}
						CustomerUpdateHistoryLogEntity  customerUpdateHistoryLog = new CustomerUpdateHistoryLogEntity();
						customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						customerUpdateHistoryLog.setFieldDesc(customerUpdateLogEnum.getFieldDesc());
						customerUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(CustomerPicMsgEntity.class,customerUpdateLogEnum.getFieldName()));
						customerUpdateHistoryLog.setIsText(customerUpdateLogEnum.getIsText());
						customerUpdateHistoryLog.setNewValue(newS);
						customerUpdateHistoryLog.setOldValue(olsS);
						list.add(customerUpdateHistoryLog);
					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房东更新记录-CustomerUpdateHistoryLogEntity异常】e={}", e);
			}  
		}  
	} 
	
	
	/**
	 * 
	 * 对类型 相同 对象不同的 属性值是否一样
	 * 注： 对比  房源图片基本信息实体
	 * @author yd
	 * @created 2017年7月4日 下午7:05:57
	 *
	 * @param obj1
	 * @param obj2
	 */
	public static void contrastCustomerPicMsgEntityObj(Object newObj, Object oldObj,List<CustomerUpdateHistoryLogEntity> list) {  
		if (oldObj instanceof CustomerPicMsgEntity && newObj instanceof CustomerPicMsgEntity&&list!=null) {  
			CustomerPicMsgEntity pojo1 = (CustomerPicMsgEntity) newObj;  
			CustomerPicMsgEntity pojo2 = (CustomerPicMsgEntity) oldObj;  
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
						CustomerUpdateLogEnum customerUpdateLogEnum  = CustomerUpdateLogEnum.getCustomerUpdateLogEnum(CustomerUpdateLogEnum.Customer_Pic_Msg_Pic_Base_Url.getTableName(), field.getName());
						if(Check.NuNObj(customerUpdateLogEnum)){
							continue;
						}
						CustomerUpdateHistoryLogEntity  customerUpdateHistoryLog = new CustomerUpdateHistoryLogEntity();
						customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						customerUpdateHistoryLog.setFieldDesc(customerUpdateLogEnum.getFieldDesc());
						customerUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(CustomerPicMsgEntity.class,customerUpdateLogEnum.getFieldName()));
						customerUpdateHistoryLog.setIsText(customerUpdateLogEnum.getIsText());
						customerUpdateHistoryLog.setNewValue(newS);
						customerUpdateHistoryLog.setOldValue(olsS);
						list.add(customerUpdateHistoryLog);
					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存房东更新记录-CustomerUpdateHistoryLogEntity异常】e={}", e);
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
	public static void contrastCustomerBaseExtObj(Object newObj, Object oldObj,List<CustomerUpdateHistoryLogEntity> list) {
		if (oldObj instanceof CustomerBaseMsgExtEntity && newObj instanceof CustomerBaseMsgExtEntity&&list!=null) {  
			CustomerBaseMsgExtEntity pojo1 = (CustomerBaseMsgExtEntity) newObj;  
			CustomerBaseMsgExtEntity pojo2 = (CustomerBaseMsgExtEntity) oldObj;  
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
						CustomerUpdateLogEnum customerUpdateLogEnum  = CustomerUpdateLogEnum.getCustomerUpdateLogEnum(CustomerUpdateLogEnum.Customer_Base_Msg_Ext_Introduce.getTableName(), field.getName());
						if(Check.NuNObj(customerUpdateLogEnum)){
							continue;
						}
						CustomerUpdateHistoryLogEntity  houseUpdateHistoryLog = new CustomerUpdateHistoryLogEntity();
						houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
						houseUpdateHistoryLog.setFieldDesc(customerUpdateLogEnum.getFieldDesc());
						houseUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(CustomerBaseMsgExtEntity.class,customerUpdateLogEnum.getFieldName()));
						houseUpdateHistoryLog.setIsText(customerUpdateLogEnum.getIsText());
						houseUpdateHistoryLog.setNewValue(newS);
						houseUpdateHistoryLog.setOldValue(olsS);
						list.add(houseUpdateHistoryLog);

					}  
				}  
			} catch (Exception e) {  
				LogUtil.error(logger, "【保存用户扩展信息更新记录-CustomerBaseMsgExtEntity异常】e={}", e);
			}  
		}  
	}
	

	
	public static void main(String[] args) {
		
		/*CustomerPicMsgEntity newObj = new CustomerPicMsgEntity();
		
		CustomerPicMsgEntity oldObj = new CustomerPicMsgEntity();
		
		List<CustomerUpdateHistoryLogEntity> list = new ArrayList<CustomerUpdateHistoryLogEntity>();
		contrastCustomerBaseMsgObj(newObj, oldObj, list);*/
		System.out.println(UUIDGenerator.hexUUID());
	}
}
