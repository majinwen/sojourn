package com.ziroom.minsu.api.search.controller;

import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>监控</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/18.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/null")
public class MonitorController extends AbstractController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String monitor(HttpServletRequest request) {


        return "ok";
    }

}
