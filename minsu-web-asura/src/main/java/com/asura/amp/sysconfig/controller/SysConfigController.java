/**
 * @FileName: SysConfigController.java
 * @Package com.asura.management.sysconfig.controller
 * 
 * @author zhangshaobin
 * @created 2012-12-25 下午4:32:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.sysconfig.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.amp.common.entity.BaseAjaxValue;
import com.asura.amp.common.entity.Failure;
import com.asura.amp.common.entity.Success;
import com.asura.amp.sysconfig.entity.SysConfig;
import com.asura.amp.sysconfig.service.SysConfigService;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;
import com.asura.framework.base.util.Check;
import com.asura.framework.conf.publish.ConfigPublisher;

/**
 * <p>系统配置Controller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
@Controller("sysConfigController")
@RequestMapping("/sysconfig/config")
public class SysConfigController {

	@Autowired
	private SysConfigService sysConfigService;

	private final  static Logger logger = LoggerFactory.getLogger(SysConfigController.class);

	/**
	 * 发布者
	 */
	private ConfigPublisher pub = ConfigPublisher.getInstance();

	/**
	 * 系统配置页面初始化
	 *
	 * @author zhangshaobin
	 * @created 2013-4-15 下午7:30:41
	 *
	 * @param search_type
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(Model model) {
		return "/sysconfig/init";
	}

	/**
	 * 查询配置项信息
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:28:55
	 *
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String search(HttpServletRequest request, SearchModel searchModel, Model model) {
		String resId = request.getParameter("resId");
		if (searchModel.getPage() == 0) {
			searchModel.setPage(1);
		}
		PagingResult<SysConfig> sysc = this.sysConfigService.findSysConfigByPager(searchModel);
		List<SysConfig> scs = sysc.getRows();
		for (SysConfig sc : scs) {
			String zkValue = pub.getConfigValue(sc.getType(), sc.getCode());
			if (zkValue.equals(sc.getValue())) {
				sc.setZkValue(zkValue);
			} else {
				sc.setZkValue("<span style='color: red'>不同</span>:" + zkValue);
			}
		}

		model.addAttribute("PAGING_RESULT", sysc);
		model.addAttribute("resId", resId);

		return "/sysconfig/config/search";
	}

	/**
	 * 添加配置项页面初始化
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:28:55
	 *
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/addinit")
	public String sysConfigAddInit(SysConfig config) {
		return "/sysconfig/config/add";
	}

	/**
	 * 添加配置项
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:28:55
	 *
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public BaseAjaxValue sysConfigAdd(SysConfig sysconfig) {

		String type = sysconfig.getType();
		String code = sysconfig.getCode();

		if (Check.NuNStr(type)) {
			return new Failure("系统配置添加失败，类型（type）不能为空。");
		} else { // type做trim
			sysconfig.setType(type.trim());
		}
		if (Check.NuNStr(code)) {
			return new Failure("系统配置添加失败，Code不能为空。");
		} else {
			sysconfig.setCode(code.trim());
		}

		int id = this.sysConfigService.saveSysConfig(sysconfig);

		LogUtil.info(logger,"【zk添加配置】id={},type={},code={},value={}",id,type, code, sysconfig.getValue());
		if (id > 0) {
			// 放入zk
			pub.setConfig(type, code, sysconfig.getValue());
			return new Success("系统配置添加成功");
		} else {
			return new Failure("添加失败。出现重复type code");
		}

	}

	/**
	 * 更新配置项页面初始化
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:28:55
	 *
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/editinit")
	public String sysConfigEditInit(SysConfig sysconfig) {
		return "/sysconfig/config/edit";
	}

	/**
	 * 更新配置项
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:28:55
	 *
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public BaseAjaxValue sysConfigEdit(SysConfig sysconfig) {
		int count = sysConfigService.updateSysConfig(sysconfig);
		if (count > 0) {
			// 更新zk数据
			pub.setConfig(sysconfig.getType(), sysconfig.getCode(), sysconfig.getValue());
			return new Success("系统配置更新成功");
		} else {
			return new Failure("修改失败");
		}
	}

	/**
	 * 删除配置项
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:28:55
	 *
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public BaseAjaxValue sysConfigDelete(String type, String code) {
		int count = sysConfigService.deleteSysConfig(type, code);
		if (count > 0) {
			// 删除zk数据
			pub.deleteConfig(type, code);
			return new Success("系统配置删除成功");
		} else {
			return new Failure("删除失败");
		}
	}

	/**
	 * 配置项MC数据重建
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:28:55
	 *
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/rebuild")
	@ResponseBody
	public BaseAjaxValue sysConfigRebuild() {

		List<SysConfig> configs = sysConfigService.findAllSysConfig();
		for (SysConfig sysconfig : configs) {
			pub.setConfig(sysconfig.getType(), sysconfig.getCode(), sysconfig.getValue());
		}
		return new Success("重置系统配置数据缓存成功！");
	}
}
