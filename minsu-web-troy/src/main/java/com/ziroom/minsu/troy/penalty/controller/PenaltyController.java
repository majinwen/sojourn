package com.ziroom.minsu.troy.penalty.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderPenaltyService;
import com.ziroom.minsu.services.order.dto.PenaltyCancelRequest;
import com.ziroom.minsu.services.order.dto.PenaltyRequest;
import com.ziroom.minsu.services.order.entity.PenaltyVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 罚款单后台处理逻辑
 * @author jixd
 * @created 2017年05月15日 09:48:53
 * @param
 * @return
 */
@Controller
@RequestMapping("/penalty")
public class PenaltyController {

    @Resource(name="order.orderPenaltyService")
    private OrderPenaltyService orderPenaltyService;

    /**
     * 请求罚款单列表页面
     */
    @RequestMapping("/penaltyList")
    public void penaltyList(){}

    /**
     * 查询罚款单数据
     * @author jixd
     * @created 2017年05月15日 10:42:53
     * @param
     * @return
     */
    @RequestMapping("/dataList")
    @ResponseBody
    public PageResult dataList(PenaltyRequest penaltyRequest){
        if (Check.NuNObj(penaltyRequest)){
            return new PageResult();
        }
        String resultJson = orderPenaltyService.listPenaltyPageByCondition(JsonEntityTransform.Object2Json(penaltyRequest));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);

        if (resultDto.getCode() == DataTransferObject.ERROR){
            return new PageResult();
        }
        List<PenaltyVo> list = resultDto.parseData("list", new TypeReference<List<PenaltyVo>>() {});
        int total = (int)resultDto.getData().get("total");
        PageResult pageResult = new PageResult();
        pageResult.setRows(list);
        pageResult.setTotal((long)total);
        return pageResult;
    }

    /**
     * 作废罚款单
     * @author jixd
     * @created 2017年05月15日 14:39:06
     * @param
     * @return
     */
    @RequestMapping("/cancelPenalty")
    @ResponseBody
    public DataTransferObject cancelPenalty(PenaltyCancelRequest penaltyCancelRequest){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(penaltyCancelRequest)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto;
        }
        if (Check.NuNStr(penaltyCancelRequest.getPenaltySn())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("罚款单账号为空");
            return dto;
        }
        if(!Check.NuNStr(penaltyCancelRequest.getRemark()) && penaltyCancelRequest.getRemark().length()>100){
        	dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("备注长度100字以内！");
            return dto;
        }
        CurrentuserVo fullCurrentUser = UserUtil.getFullCurrentUser();
        penaltyCancelRequest.setEmpCode(fullCurrentUser.getEmpCode());
        penaltyCancelRequest.setEmpName(fullCurrentUser.getFullName());
        return JsonEntityTransform.json2DataTransferObject(orderPenaltyService.cancelPenalty(JsonEntityTransform.Object2Json(penaltyCancelRequest)));
    }

    /**
     *
     * @author jixd
     * @created 2017年05月16日 14:29:36
     * @param
     * @return
     */
    @RequestMapping("/listRelDetail")
    @ResponseBody
    public DataTransferObject listRelDetail(String penaltySn){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(penaltySn)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("罚款单号为空");
            return dto;
        }
        String resultJson = orderPenaltyService.listPenaltyPayAndIncomeRel(penaltySn);
        return JsonEntityTransform.json2DataTransferObject(resultJson);
    }

}
