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
import com.entity.Operlog;
import com.service.OperlogService;
import com.util.PageHelper;
import com.util.Excel;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/operlog" , produces = "text/plain;charset=utf-8")
public class OperlogController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private OperlogService operlogService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createOperlog.action")
	public String createOperlog() {
		return "admin/addoperlog";
	}
	// 添加数据
	@RequestMapping("addOperlog.action")
	public String addOperlog(Operlog operlog) {
		int i = this.operlogService.insertOperlog(operlog);
		this.operation("新增操作日志", i);
		return "redirect:/operlog/createOperlog.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteOperlog.action")
	public String deleteOperlog(String id) {
		int i = this.operlogService.deleteOperlog(id);
		this.operation("按主键删除操作日志", i);
		return "redirect:/operlog/getAllOperlog.action";
	}

	// 批量删除数据
	@RequestMapping("deleteOperlogByIds.action")
	public String deleteOperlogByIds() {
		String[] ids = this.getRequest().getParameterValues("operlogid");
		int i = 0;
		if (ids != null) {
			for (String operlogid : ids) {
				i += this.operlogService.deleteOperlog(operlogid);
			}
		}
		this.operation("批量删除操作日志", i);
		return "redirect:/operlog/getAllOperlog.action";
	}

	// 更新数据
	@RequestMapping("updateOperlog.action")
	public String updateOperlog(Operlog operlog) {
		int i = this.operlogService.updateOperlog(operlog);
		this.operation("修改操作日志", i);
		return "redirect:/operlog/getAllOperlog.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateOperlog.action")
	@ResponseBody //将java对象转为json格式的数据
	public String xupdateOperlog(Operlog operlog) {
		int i = this.operlogService.updateOperlog(operlog);
		JSONObject json = new JSONObject();
		json.put("result", i);
		//System.out.println(json.toString());
		this.operation("Ajax修改操作日志", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllOperlog.action")
	public String getAllOperlog(String number) {
		List<Operlog> operlogList = this.operlogService.getAllOperlog();
		PageHelper.getUserPage(operlogList, "operlog", "getAllOperlog", 10, number, this.getRequest());
		this.operation("查看操作日志数据", 1);
		return "admin/listoperlog";
	}

	// 显示全部数据
	@RequestMapping("getUserOperlog.action")
	public String getUserOperlog(String number) {
		// String adminid = (String) this.getSession().getAttribute("adminid");
		Operlog operlog = new Operlog();
		List<Operlog> operlogList = this.operlogService.getOperlogByCond(operlog);
		PageHelper.getUserPage(operlogList, "operlog", "getUserOperlog", 10, number, this.getRequest());
		this.operation("查看操作日志数据", 1);
		return "admin/xlistoperlog";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody //将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Operlog> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Operlog> list = this.operlogService.getAllOperlog();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看操作日志数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryOperlogByCond.action")
	public String queryOperlogByCond(String cond, String name, String number) {
		Operlog operlog = new Operlog();
		if(cond != null){
			if ("opername".equals(cond)) {
				operlog.setOpername(name);
			}
			if ("methods".equals(cond)) {
				operlog.setMethods(name);
			}
			if ("opers".equals(cond)) {
				operlog.setOpers(name);
			}
			if ("operurl".equals(cond)) {
				operlog.setOperurl(name);
			}
			if ("ipaddr".equals(cond)) {
				operlog.setIpaddr(name);
			}
			if ("operstatus".equals(cond)) {
				operlog.setOperstatus(name);
			}
			if ("opertime".equals(cond)) {
				operlog.setOpertime(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.operlogService.getOperlogByLike(operlog), "operlog", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/queryoperlog";
	}

	// 按主键查询数据
	@RequestMapping("getOperlogById.action")
	public String getOperlogById(String id) {
		Operlog operlog = this.operlogService.getOperlogById(id);
		this.getRequest().setAttribute("operlog", operlog);
		return "admin/editoperlog";
	}

	// 按主键查询数据
	@RequestMapping("queryOperlogById.action")
	public String queryOperlogById(String id) {
		Operlog operlog = this.operlogService.getOperlogById(id);
		this.getRequest().setAttribute("operlog", operlog);
		this.operation("查看操作日志数据", 1);
		return "admin/operlog";
	}

	// 工具方法 无调用可删
	public String export(List<Operlog> operlogList) {
		Excel excel = new Excel();
		String banner = "操作日志表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "操作名称,操作方法,操作人,操作路径,IP地址,操作状态,操作日期";
		List<String[]> strList = new ArrayList<String[]>();
		for (Operlog operlog : operlogList) {
			String strTemp = operlog.getOpername() + "," + operlog.getMethods() + "," + operlog.getOpers() + "," + operlog.getOperurl() + "," + operlog.getIpaddr() + "," + operlog.getOperstatus() + "," + operlog.getOpertime();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
