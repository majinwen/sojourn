package com.zra.evaluate.resources;

import com.zra.evaluate.entity.dto.*;
import com.zra.evaluate.logic.EvaluateLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/11.
 */
@Component
@Path("/evaluate")
@Api(value = "/evaluate")
public class EvaluateResource {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(EvaluateResource.class);

    @Autowired
    private EvaluateLogic evaluateLogic;

    /**
     * 获取评价问题信息
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-11
     */
    @POST
    @Path("/question/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "获取评价问题信息", notes = "channelCode(String)-渠道编码" +
            "<br/>beEvaluatorId(String)-受评对象id" +
            "<br/>beEvaluatorType(String)-受评对象类型" +
            "<br/>questionType(String)-定义问题类型，问题类型的编码值", response = GetQuestionReturnDto.class)
    public GetQuestionReturnDto getProjectListByUser(GetQuestionDto dto) throws IOException {
        LOGGER.info("[M站获取评价问题]入参:" + dto.toString());
        return evaluateLogic.getQuestion(dto);
    }

    /**
     * 评价
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    @POST
    @Path("/evaluate/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "评价", notes = "beEvaluateId(String)-受评人id" +
            "<br/>businessBid(String)-商机的业务id" +
            "<br/>tokenId(String)-接口中返回的tokenId" +
            "<br/>evaluateMsg({<p/>requesterId(String)-请评对象id" +
            "<br/>requesterType(String)-请评对象类型" +
            "<br/>orderCode(String)-评价的订单号或合同号" +
            "<br/>ext(String)-拓展字段" +
            "<br/>questions([{" +
            "<p/>code(String)-问题编码" +
            "<br/>optionCode(String)-问题选择项" +
            "<br/>content(String)-评价内容" +
            "<br/>picUrl(String)-图片的路径}]" +
            "<br/>)-问题" +
            "<br/>)-数据)", response = EvaluateReturnDto.class)
    public String evaluatePlatform(EvaluateDto dto) throws IOException {
        LOGGER.info("[M站评价]入参:" + dto.toString());
        EvaluateReturnDto result = new EvaluateReturnDto();
        try {
			evaluateLogic.evaluatePlatform(dto);
			result.setStatus("success");
			result.setMessage("评价成功");
		} catch (Exception e) {
			result.setStatus("failure");
			result.setMessage("问题不能重复评价，请确认");
			LOGGER.info("[M站评价]logic返回结果:" + e);
		}
        String evaluateResult = new ObjectMapper().writeValueAsString(result);
        LOGGER.info("[M站评价]返回结果:" + evaluateResult);
        return evaluateResult;
    }

    /**
     * 获取评价历史详情
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-05
     */
    @POST
    @Path("/history/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "获取评价历史详情", notes = "requesterId(String)-请评对象uid" +
            "<br/>tokenId(String)-评价系统生成的评价唯一标识", response = EvaluateReturnDto.class)
    public EvaluateHistoryReturnDto getEvaluateHistory(EvaluateHistoryDto dto) throws IOException {
        return evaluateLogic.getEvaluateHistory(dto);
    }
}
