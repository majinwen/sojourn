package com.ziroom.minsu.troy.test.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziroom.minsu.services.basedata.api.inner.CityArchiveService;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.common.utils.HtmlGenerator;
import com.ziroom.minsu.services.order.api.inner.LogTestService;
import com.ziroom.minsu.services.search.api.inner.SearchService;

/**
 * <p>注释的模板</p>
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
@RequestMapping("/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private BufferedWriter bw = null;

    @Resource(name = "search.searchServiceApi")
    private SearchService searchService;


    @Resource(name="order.logTestService")
    private LogTestService logTestService;

    @Resource(name="basedata.cityArchiveService")
    private CityArchiveService cityArchiveService;

    @RequestMapping("staticHtml")
    @ResponseBody
    public void staticHtml(HttpServletRequest request,CityArchivesRequest cityRequest) throws Exception {
        //webappName
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
//        String url=basePath+"具体页面命名";
        //String url = "http://topic.ziroomstay.com/zhuanti/minsu/template/huanxinzhilv.html?id=50000234&shareFlag=2";
        //String page = HtmlGenerator.createHtmlPage("http://www.ziroom.com/");
       // HtmlGenerator.writeHtml("c:/b.html",page);
    }

    /**
     * 将解析结果写入指定的静态HTML文件中，实现静态HTML生成
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/14 15:50
     */
    private synchronized void writeHtml(String htmlFileName,String page) throws Exception{
        /*bw = new BufferedWriter(new FileWriter(htmlFileName));
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(htmlFileName),"UTF-8");
        HtmlGenerator hg = new HtmlGenerator("");
        bw.write(page);
        if(bw!=null)bw.close();*/

        bw = new BufferedWriter(new FileWriter(htmlFileName));
        OutputStreamWriter bw = new OutputStreamWriter(new FileOutputStream(htmlFileName),"UTF-8");
        page = page.replaceAll("关于自如", "测试的名字");
        bw.write(page);
        if(bw!=null)bw.close();
    }


    //将解析结果写入指定的静态HTML文件中，实现静态HTML生成
    //writeHtml(htmlFileName,page);

    /**
     * <p>描述:</p>
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
    @RequestMapping(value = "/aa", method = RequestMethod.GET)
    public @ResponseBody
    Object  aaa(HttpServletRequest request,String bb) {
        String aa= searchService.getIkList(bb);
        return    aa;
    }


    @RequestMapping(value = "/bb", method = RequestMethod.GET)
    public @ResponseBody
    Object  bb(HttpServletRequest request,String orderSn) {
        String aa= logTestService.test(orderSn);
        return    aa;
    }

}
