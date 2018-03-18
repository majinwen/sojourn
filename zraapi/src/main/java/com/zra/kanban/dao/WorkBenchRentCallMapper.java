package com.zra.kanban.dao;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.kanban.entity.WorkBenchRentCall;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lixn49 on 2016/12/29.
 *
 */
@Repository
public interface WorkBenchRentCallMapper {
	/**
	 * @Author lixn49
	 * @Date 2016/12/26 11:09
	 * @Description   房租催缴
	 */
	List<WorkBenchRentCall> selectVoucherRemind(AudContractQueryDto queryDto);
}
