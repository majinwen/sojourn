package com.ziroom.minsu.troy.customer.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerBlackService;
import com.ziroom.minsu.services.customer.dto.CustomerBlackDto;
import com.ziroom.minsu.services.customer.entity.CustomerBlackVo;
import com.ziroom.minsu.services.message.api.inner.HuanxinImRecordService;

/**
 * <p>黑名单的逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/6.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("black")
public class CustomerBlackController {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CustomerBlackController.class);




    @Resource(name="customer.customerBlackService")
    private CustomerBlackService customerBlackService;
    
    @Resource(name="message.huanxinImRecordService")
    private HuanxinImRecordService huanxinImRecordService;



    @RequestMapping(value = "/getBlack", method = RequestMethod.POST)
    @ResponseBody
    public  DataTransferObject getBlack(HttpServletRequest request,String fid){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(fid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("fid为空");
            return dto;
        }

        String resultJson = customerBlackService.findCustomerBlackByUid(fid);
        dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        return dto;
    }

    /**
     * 修改或者删除功能
     * @Author lunan【lun14@ziroom.com】
     * @Date 2017/1/12 20:14
     */
    @RequestMapping(value = "/saveBlack", method = RequestMethod.POST)
    @ResponseBody
    public  DataTransferObject saveBlack(HttpServletRequest request,CustomerBlackEntity black){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(black.getUid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("uid为空");
            return dto;
        }
        //修改或者删除black
        String resultJson = customerBlackService.saveCustomerBlack(JsonEntityTransform.Object2Json(black));
        dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        if (dto.getCode() == DataTransferObject.SUCCESS){
        	 this.huanxinImRecordService.deactivateImUser(black.getUid());
        }
       
        return dto;
    }

    /**
     * 修改或者删除功能
     * @Author lunan【lun14@ziroom.com】
     * @Date 2017/1/12 20:14
     */
    @RequestMapping(value = "/upBlack", method = RequestMethod.POST)
    @ResponseBody
    public  DataTransferObject upBlack(HttpServletRequest request,CustomerBlackEntity black){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(black.getFid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("fid为空");
            return dto;
        }
        //修改或者删除black
        customerBlackService.upBlack(JsonEntityTransform.Object2Json(black));

        if (dto.getCode() == DataTransferObject.SUCCESS){
        	 this.huanxinImRecordService.activateImUser(black.getUid());
        }
        return dto;
    }

    /**
     * 黑名单
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/toBlackList")
    public ModelAndView toCuponList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/black/blackList");
        return mv;
    }


    /**
     * 黑名单列表
     * @author afi
     * @param customerBlackDto
     * @param request
     * @return
     */
    @RequestMapping("/blackDataList")
    @ResponseBody
    public PageResult inviteDataList(@ModelAttribute("paramRequest")CustomerBlackDto customerBlackDto , HttpServletRequest request){
        PageResult pageResult = new PageResult();
        //获取当前的查询结果
        String resultJson =  customerBlackService.queryCustomerBlackList(JsonEntityTransform.Object2Json(customerBlackDto));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        if (resultDto.getCode() == DataTransferObject.SUCCESS){
            Long total = resultDto.parseData("total", new TypeReference<Long>() {});
            if (Check.NuNObj(total) || total <= 0){
                return pageResult;
            }
            List<CustomerBlackVo> rows = resultDto.parseData("rows", new TypeReference<List<CustomerBlackVo>>() {});
            pageResult.setRows(rows);
            pageResult.setTotal(total);
        }
        return pageResult;
    }



}
