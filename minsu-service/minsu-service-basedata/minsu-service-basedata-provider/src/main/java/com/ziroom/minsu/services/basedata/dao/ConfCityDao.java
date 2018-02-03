package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.services.basedata.dto.ConfCityRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 城市信息
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/3/21.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.confCityDao")
public class ConfCityDao {

	private String SQLID = "basedata.confCityDao.";

	@Autowired
	@Qualifier("basedata.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;





    /**
     * 获取所有城市列表
     * @author afi
     * @return
     */
    public List<ConfCityEntity> getAllCityList() {
        return mybatisDaoContext.findAll(SQLID + "getAllCityList", ConfCityEntity.class);
    }


    /**
     * 获取开通的城市列表
     * @author afi
     * @return
     */
    public List<ConfCityEntity> getOpenCity() {
        return mybatisDaoContext.findAll(SQLID + "getOpenCity", ConfCityEntity.class);
    }

	/**
	 * 获取开通的国家列表(房东)
	 * @return
	 */
	public List<ConfCityEntity> getOpenNationLandlord() {
		return mybatisDaoContext.findAll(SQLID + "getOpenNationLandlord", ConfCityEntity.class);
	}

	/**
	 * 获取指定国家下开通的城市列表(房东)
	 * @author lunan
	 * @return
	 */
	public List<ConfCityEntity> getOpenCityLandlord4Nation(String code) {
		Map<String,Object> par = new HashMap<>();
		par.put("code",code);
		return mybatisDaoContext.findAll(SQLID + "getOpenCityLandlord4Nation", ConfCityEntity.class, par);
	}

    /**
     * 获取开通的城市列表(房东)
     * @author lunan
     * @return
     */
    public List<ConfCityEntity> getOpenCityLandlord() {
    	return mybatisDaoContext.findAll(SQLID + "getOpenCityLandlord", ConfCityEntity.class);
    }
    
    /**
     * 获取开通的城市列表(房客)
     * @author lunan
     * @return
     */
    public List<ConfCityEntity> getOpenCityTenant() {
    	return mybatisDaoContext.findAll(SQLID + "getOpenCityTenant", ConfCityEntity.class);
    }




	/**
	 * 获取热门城市的列表
	 * @author afi
	 * @return
	 */
	public List<ConfCityEntity> getHotCity() {
		return mybatisDaoContext.findAll(SQLID + "getHotCity", ConfCityEntity.class);
	}
	
	/**
	 * 获取热门城市的列表(房东)
	 * @author lunan
	 * @return
	 */
	public List<ConfCityEntity> getHotCityLandlord() {
		return mybatisDaoContext.findAll(SQLID + "getHotCityLandlord", ConfCityEntity.class);
	}
	
	/**
	 * 获取热门城市的列表(房客)
	 * @author lunan
	 * @return
	 */
	public List<ConfCityEntity> getHotCityTenant() {
		return mybatisDaoContext.findAll(SQLID + "getHotCityTenant", ConfCityEntity.class);
	}


	/**
	 *
	 * 左侧 资源树 查询
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @return
	 */
	public List<TreeNodeVo> findTreeNodeVoList() {
		return mybatisDaoContext.findAll(SQLID + "findConfCityVo", TreeNodeVo.class);
	}

	/**
	 * 區域 查询
	 * @author liyingjie
	 * @created 2016年3月21日 
	 * @return
	 */
	public List<TreeNodeVo> findTreeNodeVoDataList() {
		return mybatisDaoContext.findAll(SQLID + "findConfCityVoData", TreeNodeVo.class);
	}
	
	

	public List<TreeNodeVo> findConfCityOnlyVos() {
		return mybatisDaoContext.findAll(SQLID + "findConfCityOnlyVos", TreeNodeVo.class);
	}
	
	/**
	 *
	 * 插入资源记录
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param resourceEntity
	 */
	public void insertConfCityRes(ConfCityEntity confCityEntity) {
		mybatisDaoContext.save(SQLID + "insertConfCity", confCityEntity);
	}
	
	/**
	 *
	 * 分页查询资源列表
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param confCityRequest
	 * @return
	 */
	public PagingResult<ConfCityEntity> findConfCityPageList(ConfCityRequest confCityRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(confCityRequest.getLimit());
		pageBounds.setPage(confCityRequest.getPage());
		return mybatisDaoContext.findForPage(SQLID + "findConfCityByCondition", ConfCityEntity.class, confCityRequest,
				pageBounds);
	}
	
	/**
	 *
	 * 更新资源记录
	 *
	 * @author liyingjie
	 * @created 2016年3月21日 
	 *
	 * @param resourceEntity
	 */
	public void updateConfCityByFid(ConfCityEntity confCityEntity) {
		if (Check.NuNObj(confCityEntity)) {
			return;
		}
		if (Check.NuNStr(confCityEntity.getFid())) {
			throw new BusinessException("on updateConfCityByFid the fid is null ");
		}
		mybatisDaoContext.save(SQLID + "updateConfCityByFid", confCityEntity);
	}

	/**
	 * 级联查询下级行政区域列表
	 *
	 * @author liujun
	 * @created 2016-3-22 下午2:33:52
	 *
	 * @param pCode
	 * @return
	 */
	public List<ConfCityEntity> findConfCityListByPcode(String pCode) {
		return mybatisDaoContext.findAll(SQLID + "findConfCityListByPcode", ConfCityEntity.class, pCode);
	}
	
	/**
	 * 
	 * 级联获取下级行政区域列表
	 *
	 * @author jixd
	 * @created 2016年9月27日 上午12:28:04
	 *
	 * @param pCode
	 * @return
	 */
	public List<ConfCityEntity> findConfCityListByPcodeForLan(String pCode){
		return mybatisDaoContext.findAll(SQLID + "findConfCityListByPcodeForLan", ConfCityEntity.class, pCode);
	}
	
	/**
	 * 
	 * 级联下拉城市结构-开通城市
	 *
	 * @author bushujie
	 * @created 2016年3月28日 下午2:51:40
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelect(){
		return mybatisDaoContext.findAll(SQLID+"findConfCitySelect", TreeNodeVo.class);
	}
	
	/**
	 * 
	 * (房东开通)级联下拉城市结构
	 *
	 * @author lunan
	 * @created 2016年9月26日 上午9:47:26
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelectForLandlord(){
		return mybatisDaoContext.findAll(SQLID+"findConfCitySelectForLandlord", TreeNodeVo.class);
	}
	
	/**
	 * 
	 * (房客开通)级联下拉城市结构
	 *
	 * @author lunan
	 * @created 2016年9月26日 上午10:04:42
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelectForTenant(){
		return mybatisDaoContext.findAll(SQLID+"findConfCitySelectForTenant", TreeNodeVo.class);
	}
	
	/**
	 * 
	 * 级联下拉城市结构-所有城市
	 *
	 * @author bushujie
	 * @created 2016年3月28日 下午2:51:40
	 *
	 * @return
	 */
	public List<TreeNodeVo> findConfCitySelectForAll(){
		return mybatisDaoContext.findAll(SQLID+"findConfCitySelectForAll", TreeNodeVo.class);
	}
	
	/**
	 * 根据城市code获取城市名称
	 * @author jixd on 2016年4月14日
	 */
	public String getCityNameByCode(String code){
		return mybatisDaoContext.findOne(SQLID+"getCityNameByCode", String.class, code);
	}
	
	/**
	 * 根据城市codeList获取城市名称
	 * @author lishaochuan
	 * @create 2016年5月26日上午12:13:32
	 * @param code
	 * @return
	 */
	public List<ConfCityEntity> getCityNameByCodeList(List<String> codeList){
		Map<String,Object> par = new HashMap<>();
		par.put("codeList",codeList);
		return mybatisDaoContext.findAll(SQLID+"getCityNameByCodeList", ConfCityEntity.class, par);
	}

    /**
	 * 级联查询所有下级行政区域
	 *
	 * @author liujun
	 * @created 2016年5月14日
	 *
	 * @param pCode
	 */
	public List<TreeNodeVo> findConfCityChildrenByPcode(String pCode) {
		return mybatisDaoContext.findAll(SQLID+"findChildTreeVo", TreeNodeVo.class, pCode);
	}



	/**
	 * 查询自身并级联上级行政区域
	 *
	 * @author liujun
	 * @created 2016年5月19日
	 *
	 * @param code
	 * @return
	 */
	public List<TreeNodeVo> findConfCityParentByCode(String code) {
		return mybatisDaoContext.findAll(SQLID+"findParentTreeVo", TreeNodeVo.class, code);
	}
	
	/**
	 * 
	 * 根据code查询城市
	 *
	 * @author bushujie
	 * @created 2016年8月8日 下午5:44:44
	 *
	 * @param code
	 * @return
	 */
	public ConfCityEntity getConfCityByCode(String code){
		return mybatisDaoContext.findOne(SQLID+"selectByCode", ConfCityEntity.class, code);
	}

	/**
	 * 获取开通的城市列表
	 *
	 * @author liujun
	 * @created 2016年11月10日
	 *
	 * @return
	 */
	public List<ConfCityEntity> getOpenCityWithFile() {
		return mybatisDaoContext.findAll(SQLID + "getOpenCityWithFile", ConfCityEntity.class);
	}
}
