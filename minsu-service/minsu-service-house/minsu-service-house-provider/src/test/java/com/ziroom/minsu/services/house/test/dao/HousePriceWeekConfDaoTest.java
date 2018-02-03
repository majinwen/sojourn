package com.ziroom.minsu.services.house.test.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.services.house.dao.HousePriceWeekConfDao;
import com.ziroom.minsu.services.house.dto.HousePriceWeekConfDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 按照星期设置特殊价格测试类
 * 
 * @author zl
 * @created 2016年9月9日
 *
 */
public class HousePriceWeekConfDaoTest extends BaseTest{

	@Resource(name="house.housePriceWeekConfDao")
	private HousePriceWeekConfDao housePriceWeekConfDao;


	/**
	 * 新增特殊价格
	 * @throws Exception
	 */
    @Test
    public void  saveHousePriceWeekConf() throws Exception{
    	
    	HousePriceWeekConfDto weekPriceConfDto = new HousePriceWeekConfDto();
    	weekPriceConfDto.setCreateUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		weekPriceConfDto.setRentWay(1);
//		weekPriceConfDto.setHouseBaseFid("8a9084df56fad7b00156fd3e8a2006a1");
		weekPriceConfDto.setHouseRoomFid("8a9e98925745a676015746b966d00032");
		  
		weekPriceConfDto.setPriceVal(7777);
		Set<Integer> weekSet = new HashSet<>();
		weekSet.add(5);
		weekSet.add(6); 
		
		weekPriceConfDto.setSetWeeks(weekSet);  
		
		int n=0;
//		if(RentWayEnum.HOUSE.getCode()==weekPriceConfDto.getRentWay()){
//    		n=housePriceWeekConfDao.saveHousePriceWeekConf(weekPriceConfDto.getCreateUid(), weekPriceConfDto.getHouseBaseFid(), null, weekPriceConfDto.getWeeksList(), weekPriceConfDto.getPriceVal());
//		} else if(RentWayEnum.ROOM.getCode()==weekPriceConfDto.getRentWay()) {
//			n=housePriceWeekConfDao.saveHousePriceWeekConf(weekPriceConfDto.getCreateUid(), null, weekPriceConfDto.getHouseRoomFid(), weekPriceConfDto.getWeeksList(), weekPriceConfDto.getPriceVal());
//		}
    	 
    	System.err.println(n);
    }
   
    
    
    /**
     * 查询某个房源某天的特殊价格
     * @throws Exception
     */
    @Test
    public void  findHousePriceWeekConfByDate() throws Exception{
    	
    	HousePriceWeekConfEntity entity = housePriceWeekConfDao.findHousePriceWeekConfByDate("8a9084df56fad7b00156fd3e8a2006a1", null, DateUtil.parseDate("2016-10-09", "yyyy-MM-dd"));
    	if(Check.NuNObj(entity)){
    		System.err.println("not exit");
    	}else {
			System.err.println(JsonEntityTransform.Object2Json(entity));
		} 
    }
    
    
    /**
     * 查询某个房源的特殊价格
     * @throws Exception
     */
    @Test
    public void  findSpecialPriceList() throws Exception{
    	
    	List<HousePriceWeekConfEntity> list = housePriceWeekConfDao.findSpecialPriceList("8a9084df56fad7b00156fd3e8a2006a1", null,1);
    	if(Check.NuNObj(list)){
    		System.err.println("not exit");
    	}else {
			System.err.println(JsonEntityTransform.Object2Json(list));
		} 
    }
    
    @Test
    public void  updateHousePriceWeekConfByFid(){
    	HousePriceWeekConfEntity housePriceWeekConf = new HousePriceWeekConfEntity();
    	housePriceWeekConf.setFid("8a9084df5746344f0157467e498504b1");
		int upNum = housePriceWeekConfDao.updateHousePriceWeekConfByFid(housePriceWeekConf);
    	System.err.println(upNum);
    }
 
}
