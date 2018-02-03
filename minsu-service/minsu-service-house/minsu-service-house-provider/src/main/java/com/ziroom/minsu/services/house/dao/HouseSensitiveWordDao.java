package com.ziroom.minsu.services.house.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseSensitiveWord;
import com.ziroom.minsu.services.house.entity.HouseResultVo;

@Repository("house.houseSensitiveWordDao")
public class HouseSensitiveWordDao {
	
    private String SQLID="house.houseSensitiveWordDao.";
	
	private static Logger logger = LoggerFactory.getLogger(HouseSensitiveWordDao.class);

	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
    public int deleteByPrimaryKey(HouseSensitiveWord record){
    	return mybatisDaoContext.save(SQLID+"deleteByPrimaryKey", record);
    };

    public int insert(HouseSensitiveWord record){
    	return mybatisDaoContext.save(SQLID+"insert", record);
    };

    public int insertSelective(HouseSensitiveWord record){
    	return mybatisDaoContext.save(SQLID+"insertSelective", record);
    };

    public HouseSensitiveWord selectByFid(String fid){
    	return mybatisDaoContext.findOne(SQLID+"selectByFid",HouseSensitiveWord.class, fid);
    };

    public int updateByFid(HouseSensitiveWord record){
    	return mybatisDaoContext.save(SQLID+"updateByFid", record);
    }

	/**
	 * 获取所有已经上架，但未校验敏感词的房源
	 *
	 * @author loushuai
	 * @created 2017年11月30日 下午5:41:56
	 *
	 * @param map
	 * @return
	 */
	public List<HouseResultVo> getAllHaveShelvesHouse(Map<String, Object> map) {
		return mybatisDaoContext.findAll(SQLID+"getAllHaveShelvesHouse",map);
	};


}