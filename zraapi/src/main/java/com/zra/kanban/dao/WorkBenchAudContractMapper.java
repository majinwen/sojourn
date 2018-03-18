package com.zra.kanban.dao;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.kanban.entity.WorkBenchAudContract;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lixn49 on 2016/12/26.
 */
@Repository
public interface WorkBenchAudContractMapper {

    /**
    * @Author lixn49
    * @Date 2016/12/26 11:09
    * @Description   根据项目id查询审核未通过的合同
    */
	List<WorkBenchAudContract> selectNoAudContractList(AudContractQueryDto queryDto);
}
