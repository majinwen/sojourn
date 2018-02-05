package com.ziroom.minsu.api.doc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>注释的一些备注demo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/30.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/doc/demo")
public class DocTestController {

    /**
     * <p>描述:</p>
     * @author afi
     * <p>&nbsp; &nbsp; &nbsp; &nbsp;<b>这里主要写这个借口的描述信息，把描述信息能搞多情况就搞多清楚</b></p>
     * <p>请求示例：<b>/test/afi/aaa?par1=beijing</b></p>
     * <p>返回结果示例:<b>
     *     { "code": 0, "msg": "", "data": { "total": 13051, "list": [ { "id": "8a9e9aba541427c101541427c19023b9", "suggestName": "朝阳新城六区", "areaName": "朝阳" }, { "id": "8a9e9aba541427c101541427c19023b9", "suggestName": "朝阳新城六区", "areaName": "朝阳" } ] } }
     * </b></p>
     * <div>
     * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
     *         <td colspan="4" textAlign="center" >入参明细</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>参数名</td>
     *         <td>类型</td>
     *         <td>是否必须(是或否)</td>
     *         <td>含义</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>par1</td>
     *         <td>String</td>
     *         <td>是</td>
     *         <td>参数的描述信息</td>
     *     </tr>
     * </table>
     *
     * <table width='100%' cellpadding='1' cellspacing='1' bgcolor='gray'>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px; ">
     *         <td colspan="4" textAlign="center">返回结果明细</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>参数名</td>
     *         <td>类型</td>
     *         <td>是否必须(是或否)</td>
     *         <td>含义</td>
     *     </tr>
     *     <tr bgcolor="#DDDDDD" style="font-size: 12px;">
     *         <td>par1</td>
     *         <td>String</td>
     *         <td>是</td>
     *         <td>参数的描述信息</td>
     *     </tr>
     * </table>
     * </div>
     * @return
     */
    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public void aaa(HttpServletRequest request,String par1) {

        String aa = "";
    }
}
