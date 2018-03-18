package com.zra.kanban.dao;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.kanban.entity.WorkBenchSurrender;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lixn49 on 2016/12/26.
 */
@Repository
public interface WorkBenchSurrenderMapper {

	/**
	 * @Author lixn49
	 * @Date 2016/12/26 11:09
	 * @Description   根据项目id查询审核未通过的解约协议
	 */
	List<WorkBenchSurrender> selectNoAudSurrendertList(AudContractQueryDto queryDto);
}
