package com.zra.kanban.resources;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.kanban.entity.WorkBenchAudContract;
import com.zra.kanban.entity.WorkBenchRentCall;
import com.zra.kanban.entity.WorkBenchSurrender;
import com.zra.kanban.logic.WorkBenchAudContractLogic;
import com.zra.kanban.logic.WorkBenchRentCallLogic;
import com.zra.kanban.logic.WorkBenchSurrenderLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by lixn49 on 2016/12/26.
 */
@Component
@Path("/workBench")
@Api(value = "/workBench")
public class WorkBenchAudContractResource{

	@Autowired
	private WorkBenchAudContractLogic audContractLogic;
	@Autowired
	private WorkBenchSurrenderLogic surrenderLogic;

	@Autowired
	private WorkBenchRentCallLogic rentCallLogic;

    /**
    * @Author lixn49
    * @Date 2016/12/26 11:52
    * @Description
    */
	@POST
	@Path("/contract/list/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询审核未通过的合同列表", notes = "查询审核未通过的合同列表", response = Response.class)
	public List<WorkBenchAudContract> selectNoAudContractList(AudContractQueryDto queryDto) {
		return this.audContractLogic.selectNoAudContractList(queryDto);
	}


	/**
	 * @Author lixn49
	 * @Date 2016/12/26 11:52
	 * @Description
	 */
	@POST
	@Path("/surrender/list/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "查询审核未通过的解约协议列表", notes = "查询审核未通过的解约协议列表", response = Response.class)
	public List<WorkBenchSurrender> selectNoAudSurrendertList(AudContractQueryDto queryDto) {
		return this.surrenderLogic.selectNoAudSurrendertList(queryDto);
	}



	/**
	 * @Author lixn49
	 * @Date 2016/12/26 11:52
	 * @Description
	 */
	@POST
	@Path("/rentRemind/list/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "房租催缴列表", notes = "房租催缴列表", response = Response.class)
	public List<WorkBenchRentCall> selectVoucherRemind(AudContractQueryDto queryDto) {
		return this.rentCallLogic.selectVoucherRemind(queryDto);
	}

}
