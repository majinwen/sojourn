package com.ziroom.zrp.service.houses.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.DictionaryEntity;
import com.ziroom.zrp.service.houses.api.DictionaryService;
import com.ziroom.zrp.service.houses.service.DictionaryServiceImpl;

@Component("houses.dictionaryServiceProxy")
public class DictionaryServiceProxy implements DictionaryService{
	private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryServiceProxy.class);
	@Resource(name="houses.dictionaryServiceImpl")
	private DictionaryServiceImpl dictionaryServiceImpl;
	
	@Override
	public String findDictionaryByEnumName(String enumName){
		LogUtil.info(LOGGER, "【findDictionaryByEnumName】入参：{}", enumName);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(enumName)){
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空！");
            return dto.toJsonString();
		}
		try{
			List<DictionaryEntity> dictionaryEntitys = dictionaryServiceImpl.findDictionaryByEnumName(enumName);
			LogUtil.info(LOGGER, "【findDictionaryByEnumName】查询字典表返回", JSONObject.toJSON(dictionaryEntitys));
			if(dictionaryEntitys.size() == 0){
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("未查询到字典信息");
	            return dto.toJsonString();
			}
			dto.putValue("dictionaryEntitys", dictionaryEntitys);
			return dto.toJsonString();
		}catch(Exception e){
			LogUtil.info(LOGGER, "【findDictionaryByEnumName】出错：{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常！");
            return dto.toJsonString();
		}
	}

}
