package com.ziroom.minsu.services.search.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>前十的房源信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/24.
 * @version 1.0
 * @since 1.0
 */
public class AdHouseInfo extends BaseEntity{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdHouseInfo.class);


    Map<String,Map<String,Object>> cityMap = new ConcurrentHashMap<>();


    public static String ad = "ad";

    public static String adHouseInfo = "adHouseInfo";


    private static String city_land = "cityLand";

    private static String city_house_count = "cityHouseCount";

    private static String weight_set = "weightSet";

    private static String house_weight = "houseWeight";

    public static String house_map = "houseMap";


    public static String city_new_count = "cityNewCount";

    private static String new_map = "newMap";

    private static Integer house_max = 10;

    private static Integer new_max = 5;


    public static String frist_key = "frist";

    public static String second_key = "frist";
    
    private static Integer recommend_max = 10;
    
    private static Integer recommend_city_max = 5;
    
    //当前热度较高的城市
    private Map<String,Integer> recCityHotMap = new ConcurrentHashMap<>();
    //当前每个城市中热门的景点商圈下的房源
    private Map<String,List<HouseInfo>> recReginHotCityMap = new ConcurrentHashMap<>();
    private Set<String> reginHotLand = new HashSet<>();
    //当前每个城市中的特色房源
    private Map<String,List<HouseInfo>> recFeatureHouseCityMap = new ConcurrentHashMap<>();    
    private Set<String> featureHouseLand = new HashSet<>();
    
    
    

    /**
     * 获取当前的前置信息
     * @return
     */
    public Map<String,Map<String,Object>> getCityMap(){
        return cityMap;
    }

    public Map<String, Integer> getRecCityHotMap() {
		return recCityHotMap;
	}

	public Map<String, List<HouseInfo>> getRecReginHotCityMap() {
		return recReginHotCityMap;
	}
	
	public void updateRecReginHotCityMap(String cityCode,List<HouseInfo> list) {
		if (Check.NuNStr(cityCode) || Check.NuNCollection(list)) {
			return;
		}
		recReginHotCityMap.put(cityCode, list);
	}

	public Map<String, List<HouseInfo>> getRecFeatureHouseCityMap() {
		return recFeatureHouseCityMap;
	}
	
	public void updateRecFeatureHouseCityMap(String cityCode,List<HouseInfo> list) {
		if (Check.NuNStr(cityCode) || Check.NuNCollection(list)) {
			return;
		}
		recFeatureHouseCityMap.put(cityCode, list);
	}

	public void pushHouse(HouseInfo houseInfo){
        try {
            if (Check.NuNObj(houseInfo)){
                return;
            }
            if (Check.NuNStr(houseInfo.getCityCode())){
                return;
            }
            if (cityMap.containsKey(houseInfo.getCityCode())){
                Map<String,Object> city = cityMap.get(houseInfo.getCityCode());

                HouseInfo frist = (HouseInfo)city.get(frist_key);
                HouseInfo second = (HouseInfo)city.get(second_key);
                if (Check.NuNObj(frist) && houseInfo.getIsNew() == YesOrNoEnum.YES.getCode()){
                    frist = houseInfo;
                    city.put(frist_key,frist);
                }else if (Check.NuNObj(second) && houseInfo.getIsNew() == YesOrNoEnum.YES.getCode()){
                    second = houseInfo;
                    city.put(second_key,second);
                }else if (houseInfo.getIsNew() == YesOrNoEnum.YES.getCode()){
                    int fristW = ValueUtil.getintValue(frist.getWeights());
                    int secondW = ValueUtil.getintValue(second.getWeights());
                    int w = ValueUtil.getintValue(houseInfo.getWeights());
                    if (fristW > w && secondW > w){
                    }else if (fristW > w && secondW < w){
                        second = houseInfo;
                        city.put(second_key,second);
                    }else if (fristW < w && secondW > w){
                        frist = houseInfo;
                        city.put(frist_key,frist);
                    }
                }

                //当前房东已经存在
                Map<String,String> cityLand =(Map<String,String>)city.get(city_land);
                if (cityLand.containsKey(houseInfo.getLandlordUid())){
                    return;
                }
                //当前的房源数量
                Integer houseCount = (Integer) city.get(city_house_count);
                TreeSet<Integer> weightSet = (TreeSet<Integer>)city.get(weight_set);
                int houseWeight = ValueUtil.getintValue(houseInfo.getWeights());
                Integer removeWeight = null;
                if (houseCount > house_max){
                    int min = weightSet.first();
                    if (min > houseWeight){
                        //当前的权重在10个之外
                        return;
                    }
                    removeWeight = min;
                }
                //如果是新房源
                if (houseInfo.getIsNew() == YesOrNoEnum.YES.getCode()){
                    Integer newCount =  (Integer)  city.get(city_new_count);
                    if (newCount > new_max){
                        //超过了新房源数量
                        return;
                    }
                }
                //当前的房东
                cityLand.put(houseInfo.getLandlordUid(),"1");
                //需要移除的非空校验
                if (!Check.NuNObj(removeWeight)){
                    //设置权重和房源关系
                    Map<Integer,String> houseWeightMap =(Map<Integer,String>)city.get(house_weight);
                    String removeId = houseWeightMap.get(removeWeight);
                    //获取房源信息
                    Map<String,HouseInfo> houseMap = (Map<String,HouseInfo>)city.get(house_map);
                    HouseInfo removeHouse = houseMap.get(removeId);
                    Map<String,HouseInfo> newMap = (Map<String,HouseInfo>)city.get(new_map);
                    if (!Check.NuNObj(removeHouse)){
                        if (removeHouse.getIsNew() == YesOrNoEnum.YES.getCode()){
                            newMap.remove(removeId);
                            city.put(city_new_count,ValueUtil.getintValue(city.get(city_new_count)) - 1);
                        }
                    }
                    houseMap.remove(removeId);
                }else {
                    //当前的房源数量加1
                    city.put(city_house_count,ValueUtil.getintValue(city.get(city_house_count)) + 1);
                }

                //设置权重和房源关系
                Map<Integer,String> houseWeightMap =(Map<Integer,String>)city.get(house_weight);
                houseWeightMap.put(houseWeight,houseInfo.getId());

                //获取房源信息
                Map<String,HouseInfo> houseMap = (Map<String,HouseInfo>)city.get(house_map);
                houseMap.put(houseInfo.getId(),houseInfo);

                Map<String,HouseInfo> newMap = (Map<String,HouseInfo>)city.get(new_map);
                if (houseInfo.getIsNew() == YesOrNoEnum.YES.getCode()){
                    newMap.put(houseInfo.getId(),houseInfo);
                    city.put(city_new_count,ValueUtil.getintValue(city.get(city_new_count)) + 1);
                }
                city.put(new_map,newMap);
            }else {
                //当前城市还未初始化
                Map<String,Object> city = new HashMap<>();

                Map<String,String> cityLand = new HashMap<>();
                cityLand.put(houseInfo.getLandlordUid(),"1");
                city.put(city_land,cityLand);
                city.put(city_house_count,1);

                TreeSet<Integer> weightSet = new TreeSet<>();
                int houseWeight = ValueUtil.getintValue(houseInfo.getWeights());
                weightSet.add(houseWeight);
                city.put(weight_set,weightSet);

                Map<Integer,String> houseWeightMap = new HashMap<>();
                houseWeightMap.put(houseWeight,houseInfo.getId());
                city.put(house_weight,houseWeightMap);

                Map<String,HouseInfo> houseMap = new HashMap<>();
                houseMap.put(houseInfo.getId(),houseInfo);
                city.put(house_map,houseMap);

                Map<String,HouseInfo> newMap = new HashMap<>();
                if (houseInfo.getIsNew() == YesOrNoEnum.YES.getCode()){
                    newMap.put(houseInfo.getId(),houseInfo);
                    city.put(city_new_count,1);
                    city.put(frist_key,houseInfo);
                }else {
                    city.put(city_new_count,0);
                }
                city.put(new_map,newMap);
                cityMap.put(houseInfo.getCityCode(),city);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(LOGGER,"ad errore:{}",e);

        }

    }
    
    /**
     * 推荐房源 
     *
     * @author zl
     * @created 2016年12月7日 下午3:07:58
     *
     * @param houseInfo
     */
    public void pushRecHouse(HouseInfo houseInfo){
    	if (Check.NuNObj(houseInfo) || Check.NuNStr(houseInfo.getCityCode())){
            return;
        } 
        
        pushRecReginHotHouse(houseInfo);
        
        pushRecFeatureHouse(houseInfo);
        
        pushRecCityHotMap(houseInfo);
        
    }
    
    /**
     *  处理房源热度
     *
     * @author zl
     * @created 2016年12月6日 下午5:25:45
     *
     * @param houseInfo
     */
    public void pushRecReginHotHouse(HouseInfo houseInfo){
    	if (Check.NuNObj(houseInfo) || Check.NuNStr(houseInfo.getCityCode()) || reginHotLand.contains(houseInfo.getLandlordUid())){
            return;
        } 
        
        List<HouseInfo> list = recReginHotCityMap.get(houseInfo.getCityCode());
        if (Check.NuNCollection(list)) {
			list = new ArrayList<>();
		}
        list.add(houseInfo);
        
        if (list.size()>1) {			
        	sortHouseInfoByCurrentReginHotAndWeightDesc(list);
		}
        
        if (list.size()>recommend_max) {
        	list=list.subList(0, recommend_max);
		}
        
        if(list.contains(houseInfo)){        	
        	reginHotLand.add(houseInfo.getLandlordUid());
        }
        
        if (list.size()>0) {
        	for (int i = 0; i < list.size(); i++) {
        		HouseInfo info = list.get(i);
        		info.setCurrentReginMaxHotIndex(recommend_max-i);
        		list.set(i, info);
        	}
        	recReginHotCityMap.put(houseInfo.getCityCode(), list);
        }
        
    }
    
    /**
     * 处理特色房源
     *
     * @author zl
     * @created 2016年12月6日 下午5:25:51
     *
     * @param houseInfo
     */
    public void pushRecFeatureHouse(HouseInfo houseInfo){
    	if (Check.NuNObj(houseInfo) || Check.NuNStr(houseInfo.getCityCode()) || featureHouseLand.contains(houseInfo.getLandlordUid())){
            return;
        } 
        
        if (houseInfo.getIsFeatureHouse()!=YesOrNoEnum.YES.getCode()) {
        	 return;
        }
       
        List<HouseInfo> list = recFeatureHouseCityMap.get(houseInfo.getCityCode());
        if (Check.NuNCollection(list)) {
			list = new ArrayList<>();
		}
        
        list.add(houseInfo);
        
        if (list.size()>1) {			
        	sortHouseInfoByWeightsDesc(list);
		}
        
        if (list.size()>recommend_max) {
        	list=list.subList(0, recommend_max);
		}
        
        if(list.contains(houseInfo)){    
        	featureHouseLand.add(houseInfo.getLandlordUid());        	
        }
        
        if (list.size()>0) {
        	for (int i = 0; i < list.size(); i++) {
            	HouseInfo info = list.get(i);
            	info.setFeatureHouseIndex(recommend_max-i);
            	list.set(i, info);
    		}
        	recFeatureHouseCityMap.put(houseInfo.getCityCode(), list);
        }
        
    }
    
    /**
     * 处理城市热度 
     *
     * @author zl
     * @created 2016年12月7日 上午11:52:01
     *
     * @param houseInfo
     */
    public void pushRecCityHotMap(HouseInfo houseInfo){ 
        if (Check.NuNObj(houseInfo) || Check.NuNStr(houseInfo.getCityCode()) || Check.NuNObj(houseInfo.getCurrentCityHot())){
            return;
        }
        
        recCityHotMap.put(houseInfo.getCityCode(), houseInfo.getCurrentCityHot());
        
        if (recCityHotMap.size()>1) {
        	sortRecCityHotMapDesc(recCityHotMap); 
		}
        
        if (recCityHotMap.size()>recommend_city_max) {
        	Iterator<Map.Entry<String, Integer>> iter = recCityHotMap.entrySet().iterator();
        	int i=1;
    		while (iter.hasNext()) {
    			iter.next();
    			if (i>recommend_city_max) {
    				 iter.remove();
				}
    			i++;
    		} 
		}
        
    }
    
    
    /**
     * 对房源列表降序排列 
     *
     * @author zl
     * @created 2016年12月6日 下午5:25:04
     *
     * @param list
     */
    public void sortHouseInfoByWeightsDesc(List<HouseInfo> list){
    	if(Check.NuNCollection(list)){
    		return;
    	}
    	Collections.sort(list, new Comparator<HouseInfo>() {

			@Override
			public int compare(HouseInfo o1, HouseInfo o2) {
				return o2.getWeights().compareTo(o1.getWeights());  
			}
		});
    	
    }
    
    /**
     * 按照当前所属景点商圈的热度和权重降序排列 
     *
     * @author zl
     * @created 2016年12月7日 上午11:24:29
     *
     * @param list
     */
    public void sortHouseInfoByCurrentReginHotAndWeightDesc(List<HouseInfo> list){
    	if(Check.NuNCollection(list)){
    		return;
    	}
    	Collections.sort(list, new Comparator<HouseInfo>() {

			@Override
			public int compare(HouseInfo o1, HouseInfo o2) {
				if (o1.getCurrentReginMaxHot()==o2.getCurrentReginMaxHot()) {
					return o2.getWeights().compareTo(o1.getWeights());  
				}else{
					return o2.getCurrentReginMaxHot().compareTo(o1.getCurrentReginMaxHot());  
				} 
			}
		});    	
    }
    
    /**
     * 按照当前城市的热度降序排列 
     *
     * @author zl
     * @created 2016年12月7日 上午11:44:39
     *
     * @param recCityHotMap
     */
    public void sortRecCityHotMapDesc(Map<String,Integer> recCityHotMap){
    	
    	if(Check.NuNMap(recCityHotMap)) return;
    	
    	List<Map.Entry<String, Integer>> cityHotMapList = new ArrayList<Map.Entry<String, Integer>>(recCityHotMap.entrySet());  
    	
    	Collections.sort(cityHotMapList, new Comparator<Map.Entry<String, Integer>>() {  
      	  
            @Override  
            public int compare(Entry<String, Integer> arg0,  
                    Entry<String, Integer> arg1) {  
                return arg1.getValue().compareTo(arg0.getValue());  
            }  
        });  
        
        Map<String, Integer> resultMap = new LinkedHashMap<String, Integer>();  
        Iterator<Map.Entry<String, Integer>> iter = cityHotMapList.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Integer> tmpEntry = iter.next();
			resultMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
		} 
    	
		recCityHotMap=resultMap;    	
    }
    
    
    


}
