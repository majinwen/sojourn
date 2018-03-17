package com.ziroom.minsu.ups.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.CurrentuserRoleEntity;
import com.ziroom.minsu.entity.sys.RoleEntity;
import com.ziroom.minsu.services.basedata.dto.CurrentuserRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserCityVo;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.ResourceVo;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.SentinelJedisUtil;
import com.ziroom.minsu.ups.common.constant.Constant;
import com.ziroom.minsu.ups.dao.CurrentuserCityDao;
import com.ziroom.minsu.ups.dao.CurrentuserDao;
import com.ziroom.minsu.ups.dao.CurrentuserRoleDao;
import com.ziroom.minsu.ups.dao.ResourceDao;
import com.ziroom.minsu.ups.dao.RoleDao;
import com.ziroom.minsu.ups.dao.SystemsDao;
import com.ziroom.minsu.ups.service.ICurrentUserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

/**
 * <p>用户服务实现类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Service("ups.currentUserService")
public class CurrentUserServiceImpl implements ICurrentUserService{

    @Autowired
    private CurrentuserDao currentuserDao;

    @Autowired
    private CurrentuserRoleDao currentuserRoleDao;
    
    @Autowired
    private CurrentuserCityDao currentuserCityDao;
    
    @Autowired
    private RoleDao roleDao;
    
    @Autowired
    private ResourceDao resourceDao;
    
    @Resource(name="sentinelJedisClient")
    private SentinelJedisUtil sentinelJedisClient;
    
    @Autowired
    private SystemsDao systemsDao;

    @Override
    public PagingResult<CurrentuserVo> findCurrentuserPageList(CurrentuserRequest currentuserRequest) {
        return currentuserDao.findCurrentuserPageList(currentuserRequest);
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.ICurrentUserService#getCurrentuserByUserAccount(java.lang.String)
	 */
	@Override
	public CurrentuserVo getCurrentuserByUserAccount(String userAccount) {
		return currentuserDao.getCurrentuserByUserAccount(userAccount);
	}

    @Override
    public CurrentuserVo currentUserInfo(String fid) {
        CurrentuserVo currentVo = currentuserDao.getCurrentuserVoByfid(fid);
        return currentVo;
    }

    @Override
    public int saveCurrentUser(CurrentuserVo vo) {
        int count = currentuserDao.insertCurrentuser(vo);
        List<String> fidList = vo.getRoleFidList();
        //插入权限
        if (!Check.NuNCollection(fidList)){
            for(String roleFid:fidList){
                Map<String, Object> paramMap=new HashMap<>();
                paramMap.put("currenuserFid", vo.getFid());
                paramMap.put("roleFid", roleFid);
                currentuserDao.insertCurrentuserRole(paramMap);
            }
        }
        //插入负责区域
        for(String code:vo.getAreaCodeList()){
            String[] codeS=code.split(":");
            CurrentuserCityEntity currentuserCityEntity=new CurrentuserCityEntity();
            currentuserCityEntity.setCurrentuserFid(vo.getFid());
            for(int i=0;i<codeS.length;i++){
                if(i==0){
                    currentuserCityEntity.setNationCode(codeS[0]);
                }
                if(i==1){
                    currentuserCityEntity.setProvinceCode(codeS[1]);
                }
                if(i==2){
                    currentuserCityEntity.setCityCode(codeS[2]);
                }
                if(i==3){
                    currentuserCityEntity.setAreaCode(codeS[3]);
                }
            }
            currentuserCityDao.insertCurrentuserCity(currentuserCityEntity);
        }
        return count;
    }

    @Override
    public int updateCurrentUser(CurrentuserVo vo) {
        CurrentuserEntity user = new CurrentuserEntity();
        BeanUtils.copyProperties(vo, user);
        //更新用户的信息
        int count = currentuserDao.updateCurrentuserByFid(user);
        //删除用户的角色信息
        currentuserRoleDao.delCurrentuserRoleByUserFid(vo.getFid());
        //重新赋值用户的角色信息
        Set<String> sysSet=new HashSet<>();
        for(String roleId:vo.getRoleFidList()){
            if(!Check.NuNObj(roleId)){
                CurrentuserRoleEntity currentuserRoleEntity = new CurrentuserRoleEntity();
                currentuserRoleEntity.setRoleFid(roleId);
                currentuserRoleEntity.setCurrenuserFid(user.getFid());
                currentuserRoleDao.insertCurrentuserRole(currentuserRoleEntity);
                sysSet.add(roleDao.getSysCodeByRole(roleId));
            }
        }
        //重新分配权限缓存修改
        for(String sysCode:sysSet){
			String listKey=RedisKeyConst.getResCacheKey(sysCode,user.getFid(),Constant.RES_LIST);
			String setKey=RedisKeyConst.getResCacheKey(sysCode,user.getFid(),Constant.RES_SET);
			String resListJson=sentinelJedisClient.getex(listKey);
			String resSetJson=sentinelJedisClient.getex(setKey);
			String sysFid= systemsDao.getSystemsEntityByCode(sysCode).getFid();
			if(!Check.NuNStr(resListJson)){
				List<ResourceVo> resourceVoList=resourceDao.findResourceByCurrentuserId(user.getFid(), sysFid);
				sentinelJedisClient.setex(listKey, Constant.RES_CACHE_TIME, JsonEntityTransform.Object2Json(resourceVoList));
			}
			if(!Check.NuNStr(resSetJson)){
				Map<String, String> paraMap=new HashMap<>();
				paraMap.put("currentuserFid", user.getFid());
				paraMap.put("systemFid", sysFid);
				List<String> resourceVoList=resourceDao.findFnResourceSetByUid(paraMap);
				Set<String> resourceVoSet=new HashSet<>();
				resourceVoSet.addAll(resourceVoList);
				sentinelJedisClient.setex(setKey, Constant.RES_CACHE_TIME, JsonEntityTransform.Object2Json(resourceVoSet));
			}
        }
        //删除用户负责区域
        currentuserCityDao.delCurrentuserCity(vo.getFid());
        //重新赋值负责区域
        for(String code:vo.getAreaCodeList()){
            String[] codeS=code.split(":");
            CurrentuserCityEntity currentuserCityEntity=new CurrentuserCityEntity();
            currentuserCityEntity.setCurrentuserFid(vo.getFid());
            for(int i=0;i<codeS.length;i++){
                if(i==0){
                    currentuserCityEntity.setNationCode(codeS[0]);
                }
                if(i==1){
                    currentuserCityEntity.setProvinceCode(codeS[1]);
                }
                if(i==2){
                    currentuserCityEntity.setCityCode(codeS[2]);
                }
                if(i==3){
                    currentuserCityEntity.setAreaCode(codeS[3]);
                }
            }
            currentuserCityDao.insertCurrentuserCity(currentuserCityEntity);
        }
        return count;
    }

    @Override
    public CurrentuserVo getCurrentuserVoByfid(String fid) {
        return currentuserDao.getCurrentuserVoByfid(fid);
    }

    @Override
    public int updateCurrentUserStatus(CurrentuserEntity currentuserEntity) {
        return currentuserDao.updateCurrentuserByFid(currentuserEntity);
    }

    @Override
    public int insertOrUpdateCurrentuser(CurrentuserEntity currentuserEntity) {
        CurrentuserVo currentuserVo = currentuserDao.getCurrentuserByUserAccount(currentuserEntity.getUserAccount());
        if (Check.NuNObj(currentuserVo)){
            return currentuserDao.insertCurrentuser(currentuserEntity);
        }else{
            currentuserEntity.setFid(currentuserVo.getFid());
           return currentuserDao.updateCurrentuserByFid(currentuserEntity);
        }
    }


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.ICurrentUserService#getCurrentuserCityListByUserFid(java.lang.String)
	 */
	@Override
	public List<CurrentuserCityVo> getCurrentuserCityListByUserFid(String fid) {
		return currentuserCityDao.getCurrentuserCity(fid);
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.ICurrentUserService#findRoleTypeByMenu(java.util.Map)
	 */
	@Override
	public int findRoleTypeByMenu(Map<String, Object> paramMap) {
		int roleType=0;
		List<Integer> roleTypeList=currentuserDao.findRoleTypeByMenu(paramMap);
		//判断角色类型
		if(CollectionUtils.isNotEmpty(roleTypeList)){
			if(roleTypeList.contains(0)){
				roleType=0;
			}else if(roleTypeList.contains(1)){
				roleType=1;
			}else if(roleTypeList.contains(2)&&roleTypeList.contains(3)){
				roleType=1;
			}else{
				roleType=roleTypeList.get(0);
			}
		}
		return roleType;
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.ICurrentUserService#findCuserCityByUserFid(java.lang.String)
	 */
	@Override
	public List<CurrentuserCityEntity> findCuserCityByUserFid(String currentuserFid) {
		return currentuserCityDao.findCuserCityByUserFid(currentuserFid);
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.ups.service.ICurrentUserService#getCurrentByUserAccount(java.lang.String)
	 */
	@Override
	public CurrentuserEntity getCurrentByUserAccount(String userAccount) {
		return currentuserDao.getCurrentByUserAccount(userAccount);
	}

    @Override
    public CurrentuserEntity getCurrentByEmpFid(String employeeFid) {
        return currentuserDao.getCurrentByEmpFid(employeeFid);
    }
}
