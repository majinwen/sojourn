package com.ziroom.minsu.services.cms.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ShortChainMapEntity;
import com.ziroom.minsu.services.cms.dao.ShortChainMapDao;

/**
 * 
 * <p>短链service</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Service("cms.shortChainMapImpl")
public class ShortChainMapServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortChainMapServiceImpl.class);
	
	private static final String DEFAULT_CODE_DIGIT = "00000000";
	
    @Resource(name = "cms.shortChainMapDao")
    private ShortChainMapDao shortChainMapDao;
    
    /**
     * 根据校验码查询短链集合
     *
     * @author liujun
     * @created 2016年10月27日
     *
     * @param checkCode
     * @return
     */
    public List<ShortChainMapEntity> findShortChainMapByCheckCode(String checkCode) {
    	return shortChainMapDao.findByCheckCode(checkCode);
    }

	/**
	 * 根据短链编号查询短链信息
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param uniqueCode
	 * @return
	 */
	public ShortChainMapEntity findShortChainMapByUniqueCode(String uniqueCode) {
		return shortChainMapDao.findByUniqueCode(uniqueCode);
	}

	/**
	 * 组装短链信息实体
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param originalLink
	 * @param createId
	 * @param checkCode 
	 * @return
	 */
	public ShortChainMapEntity assembleEntity(String originalLink, String createId, String checkCode) {
		ShortChainMapEntity shortChainMapEntity = new ShortChainMapEntity();
		shortChainMapEntity.setOriginalLink(originalLink);
		shortChainMapEntity.setCheckCode(checkCode);
		shortChainMapEntity.setCreateId(createId);
		shortChainMapEntity.setCreateDate(new Date());
		return shortChainMapEntity;
	}

	/**
	 * 1.新增短链信息并返回自增id
	 * 2.更新短链信息
	 * 3.返回短链
	 * 注意:方法名不能以get,find开头,否则事物是只读的(read-only),不能执行新增和更新操作
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param shortChainMapEntity
	 * @param apiUrl 短链服务api url
	 * @return
	 */
	public String generateShortLink(ShortChainMapEntity shortChainMapEntity, String apiUrl) {
		// 新增短链信息并返回自增id
		shortChainMapDao.insertShortChainMapEntity(shortChainMapEntity);
		Integer id = shortChainMapEntity.getId();
		if (Check.NuNObj(id)) {
			LogUtil.error(LOGGER, "save shortChainMapEntity:{}", JsonEntityTransform.Object2Json(shortChainMapEntity));
			throw new BusinessException("[短链]主键id不能为空");
		}
		
		String uniqueCode = this.getUniqueCodeById(id);
		String shortLink = this.assembleShortLink(apiUrl, uniqueCode);
		shortChainMapEntity.setUniqueCode(uniqueCode);
		shortChainMapEntity.setShortLink(shortLink);
		int upNum = shortChainMapDao.updateById(shortChainMapEntity);
		if (upNum != 1) {
			LogUtil.error(LOGGER, "update shortChainMapEntity:{}", JsonEntityTransform.Object2Json(shortChainMapEntity));
			throw new BusinessException("[短链]更新失败");
		}
		return shortLink;
	}

	/**
	 * 根据自增id生成短链编号
	 * 1.自增id位数小于8位,高位补0
	 * 2.自增id位数大于等于8位,直接返回
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param id 
	 * @return
	 */
	private String getUniqueCodeById(Integer id) {
		LogUtil.info(LOGGER, "id:{}", id);
		if(String.valueOf(id).length() < DEFAULT_CODE_DIGIT.length()){
			String covering = DEFAULT_CODE_DIGIT.substring(String.valueOf(id).length());
			StringBuilder sb = new StringBuilder(DEFAULT_CODE_DIGIT.length());
			sb.append(covering).append(id);
			return sb.toString();
		} else {
			return String.valueOf(id);
		}
	}
	
	/**
	 * 根据原链接,短链编号生成短链
	 *
	 * @author liujun
	 * @created 2016年10月28日
	 *
	 * @param apiUrl
	 * @param uniqueCode eg:http://minsu.activity.d.ziroom.com/uniqueCode/{uniqueCode}
	 * @return shortLink eg:http://minsu.activity.d.ziroom.com/uniqueCode/00000001
	 */
	private String assembleShortLink(String apiUrl, String uniqueCode) {
		LogUtil.info(LOGGER, "domain:{}, uniqueCode:{}", apiUrl, uniqueCode);
		if(Check.NuNStr(apiUrl)){
			throw new BusinessException("[短链]服务域名不能为空");
		}
		apiUrl = apiUrl.replace("{uniqueCode}", uniqueCode);
		LogUtil.info(LOGGER, "shortLink:{}", apiUrl);
		return apiUrl;
	}

}
