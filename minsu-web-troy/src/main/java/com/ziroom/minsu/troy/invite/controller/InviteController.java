package com.ziroom.minsu.troy.invite.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.InviteEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.cms.api.inner.InviteService;
import com.ziroom.minsu.services.cms.dto.InviteListRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.troy.invite.dto.InvoteInfo;
import com.ziroom.minsu.troy.invite.service.CustomerQueryService;
import com.ziroom.minsu.troy.invite.vo.InviteEntityVo;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/3.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("invite")
public class InviteController {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(InviteController.class);


    @Resource(name="cms.inviteService")
    private InviteService inviteService;

    @Resource(name="api.customerQueryService")
    private CustomerQueryService customerQueryService;
    /**
     * 邀请码关系查询
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/toInvoteList")
    public ModelAndView toCuponList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/invite/inviteList");
        return mv;
    }


    /**
     * 邀请码关系查询
     * @author afi
     * @param paramRequest
     * @param request
     * @return
     */
    @RequestMapping("/inviteDataList")
    @ResponseBody
    public PageResult inviteDataList(@ModelAttribute("paramRequest") InvoteInfo paramRequest , HttpServletRequest request){
        //拼装查询条件
        InviteListRequest inviteListRequest = new InviteListRequest();
        inviteListRequest.setPage(paramRequest.getPage());
        inviteListRequest.setLimit(paramRequest.getLimit());
        PageResult pageResult = new PageResult();
        //获取邀请人信息
        if (!Check.NuNStr(paramRequest.getName()) || !Check.NuNStr(paramRequest.getTel())){
            List<CustomerBaseMsgEntity>  userList = customerQueryService.getCustomerList(paramRequest.getName(),paramRequest.getTel());
            if (Check.NuNCollection(userList)){
                return pageResult;
            }else {
                List<String> uids = new ArrayList<>();
                for (CustomerBaseMsgEntity entity : userList) {
                    uids.add(entity.getUid());
                }
                inviteListRequest.setUids(uids);
            }
        }
        //获取受邀请人信息
        if (!Check.NuNStr(paramRequest.getInvoteName()) || !Check.NuNStr(paramRequest.getInvoteTel())){
            List<CustomerBaseMsgEntity>  list = customerQueryService.getCustomerList(paramRequest.getInvoteName(),paramRequest.getInvoteTel());
            if (Check.NuNCollection(list)){
                return pageResult;
            }else {
                List<String> invoteUids = new ArrayList<>();
                for (CustomerBaseMsgEntity entity : list) {
                    invoteUids.add(entity.getUid());
                }
                inviteListRequest.setInviteUids(invoteUids);
            }
        }
        inviteListRequest.setInviteCode(paramRequest.getInviteCode());
        //获取当前的查询结果
        String resultJson = inviteService.getInviteListByCondition(JsonEntityTransform.Object2Json(inviteListRequest));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        if (resultDto.getCode() == DataTransferObject.SUCCESS){
            PagingResult<InviteEntity>  pagingResult = resultDto.parseData("pagingResult", new TypeReference<PagingResult<InviteEntity>>() {});
            if (pagingResult.getTotal() == 0){
                return pageResult;
            }
            List<InviteEntity> list = pagingResult.getRows();
            if (!Check.NuNCollection(list)){
                Set<String> uids = new HashSet<>();
                for (InviteEntity inviteEntity : list) {
                    if (!Check.NuNStr(inviteEntity.getUid())){
                        uids.add(inviteEntity.getUid());
                    }
                    if (!Check.NuNStr(inviteEntity.getInviteUid())){
                        uids.add(inviteEntity.getInviteUid());
                    }
                }
                Map<String,CustomerBaseMsgEntity> userMap = customerQueryService.getCustomerMap(uids);

                List<InviteEntityVo>  inviteEntityVoList = new ArrayList<>();
                for (InviteEntity inviteEntity : list) {
                    InviteEntityVo vo = new InviteEntityVo();
                    BeanUtils.copyProperties(inviteEntity, vo);
                    CustomerBaseMsgEntity user = userMap.get(vo.getUid());
                    if (!Check.NuNObj(user)){
                        vo.setUidName(user.getRealName());
                        vo.setUidTel(user.getCustomerMobile());
                    }
                    if (!Check.NuNObj(vo.getInviteUid())){
                        CustomerBaseMsgEntity invite = userMap.get(vo.getInviteUid());
                        if (!Check.NuNObj(invite)){
                            vo.setInviteUidName(invite.getRealName());
                            vo.setInviteUidTel(invite.getCustomerMobile());
                        }
                    }
                    inviteEntityVoList.add(vo);
                }
                pageResult.setRows(inviteEntityVoList);
                pageResult.setTotal(pagingResult.getTotal());
            }
            return pageResult;
        }else {
            return new PageResult();
        }
    }


}
