package com.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.entity.Loginf;
import com.service.LoginfService;
import com.util.PageHelper;
import com.util.Excel;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/loginf" , produces = "text/plain;charset=utf-8")
public class LoginfController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private LoginfService loginfService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createLoginf.action")
	public String createLoginf() {
		return "admin/addloginf";
	}
	// 添加数据
	@RequestMapping("addLoginf.action")
	public String addLoginf(Loginf loginf) {
		int i = this.loginfService.insertLoginf(loginf);
		this.operation("新增登录日志", i);
		return "redirect:/loginf/createLoginf.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteLoginf.action")
	public String deleteLoginf(String id) {
		int i = this.loginfService.deleteLoginf(id);
		this.operation("按主键删除登录日志", i);
		return "redirect:/loginf/getAllLoginf.action";
	}

	// 批量删除数据
	@RequestMapping("deleteLoginfByIds.action")
	public String deleteLoginfByIds() {
		String[] ids = this.getRequest().getParameterValues("loginfid");
		int i = 0;
		if (ids != null) {
			for (String loginfid : ids) {
				i += this.loginfService.deleteLoginf(loginfid);
			}
		}
		this.operation("批量删除登录日志", i);
		return "redirect:/loginf/getAllLoginf.action";
	}

	// 更新数据
	@RequestMapping("updateLoginf.action")
	public String updateLoginf(Loginf loginf) {
		int i = this.loginfService.updateLoginf(loginf);
		this.operation("修改登录日志", i);
		return "redirect:/loginf/getAllLoginf.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateLoginf.action")
	@ResponseBody //将java对象转为json格式的数据
	public String xupdateLoginf(Loginf loginf) {
		int i = this.loginfService.updateLoginf(loginf);
		JSONObject json = new JSONObject();
		json.put("result", i);
		//System.out.println(json.toString());
		this.operation("Ajax修改登录日志", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllLoginf.action")
	public String getAllLoginf(String number) {
		List<Loginf> loginfList = this.loginfService.getAllLoginf();
		PageHelper.getUserPage(loginfList, "loginf", "getAllLoginf", 10, number, this.getRequest());
		this.operation("查看登录日志数据", 1);
		return "admin/listloginf";
	}

	// 显示全部数据
	@RequestMapping("getUserLoginf.action")
	public String getUserLoginf(String number) {
		// String adminid = (String) this.getSession().getAttribute("adminid");
		Loginf loginf = new Loginf();
		List<Loginf> loginfList = this.loginfService.getLoginfByCond(loginf);
		PageHelper.getUserPage(loginfList, "loginf", "getUserLoginf", 10, number, this.getRequest());
		this.operation("查看登录日志数据", 1);
		return "admin/xlistloginf";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody //将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Loginf> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Loginf> list = this.loginfService.getAllLoginf();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看登录日志数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryLoginfByCond.action")
	public String queryLoginfByCond(String cond, String name, String number) {
		Loginf loginf = new Loginf();
		if(cond != null){
			if ("logname".equals(cond)) {
				loginf.setLogname(name);
			}
			if ("ipaddr".equals(cond)) {
				loginf.setIpaddr(name);
			}
			if ("brower".equals(cond)) {
				loginf.setBrower(name);
			}
			if ("os".equals(cond)) {
				loginf.setOs(name);
			}
			if ("logstatus".equals(cond)) {
				loginf.setLogstatus(name);
			}
			if ("role".equals(cond)) {
				loginf.setRole(name);
			}
			if ("logtime".equals(cond)) {
				loginf.setLogtime(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.loginfService.getLoginfByLike(loginf), "loginf", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/queryloginf";
	}

	// 按主键查询数据
	@RequestMapping("getLoginfById.action")
	public String getLoginfById(String id) {
		Loginf loginf = this.loginfService.getLoginfById(id);
		this.getRequest().setAttribute("loginf", loginf);
		return "admin/editloginf";
	}

	// 按主键查询数据
	@RequestMapping("queryLoginfById.action")
	public String queryLoginfById(String id) {
		Loginf loginf = this.loginfService.getLoginfById(id);
		this.getRequest().setAttribute("loginf", loginf);
		this.operation("查看登录日志数据", 1);
		return "admin/loginf";
	}

	// 工具方法 无调用可删
	public String export(List<Loginf> loginfList) {
		Excel excel = new Excel();
		String banner = "登录日志表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "登录名,IP地址,浏览器,操作系统,登录状态,登录角色,登录时间";
		List<String[]> strList = new ArrayList<String[]>();
		for (Loginf loginf : loginfList) {
			String strTemp = loginf.getLogname() + "," + loginf.getIpaddr() + "," + loginf.getBrower() + "," + loginf.getOs() + "," + loginf.getLogstatus() + "," + loginf.getRole() + "," + loginf.getLogtime();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
