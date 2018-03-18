package com.zra.kanban.service;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.kanban.dao.WorkBenchAudContractMapper;
import com.zra.kanban.entity.WorkBenchAudContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixn49 on 2016/12/26.
 * 查找未经审核通过的合同
 */
@Service
public class WorkBenchAudContractService {

	@Autowired
	private WorkBenchAudContractMapper audContractMapper;
    /**
    * @Author lixn49
    * @Date 2016/12/26 11:32
    * @Description   根据项目id 未审核通过的合同
    */
	public List<WorkBenchAudContract> selectNoAudContractList(AudContractQueryDto queryDto){
       return  audContractMapper.selectNoAudContractList(queryDto);
	}
}
