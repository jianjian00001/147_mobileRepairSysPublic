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
import com.entity.Complains;
import com.service.ComplainsService;
import com.entity.Users;
import com.service.UsersService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/complains" , produces = "text/plain;charset=utf-8")
public class ComplainsController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private ComplainsService complainsService;
	@Autowired
	private UsersService usersService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createComplains.action")
	public String createComplains() {
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		return "admin/addcomplains";
	}
	// 添加数据
	@RequestMapping("addComplains.action")
	public String addComplains(Complains complains) {
		complains.setUsersid("");
		complains.setAddtime(VeDate.getStringDateShort());
		complains.setStatus("");
		int i = this.complainsService.insertComplains(complains);
		this.operation("新增意见反馈", i);
		return "redirect:/complains/createComplains.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteComplains.action")
	public String deleteComplains(String id) {
		int i = this.complainsService.deleteComplains(id);
		this.operation("按主键删除意见反馈", i);
		return "redirect:/complains/getAllComplains.action";
	}

	// 批量删除数据
	@RequestMapping("deleteComplainsByIds.action")
	public String deleteComplainsByIds() {
		String[] ids = this.getRequest().getParameterValues("complainsid");
		int i = 0;
		if (ids != null) {
			for (String complainsid : ids) {
				i += this.complainsService.deleteComplains(complainsid);
			}
		}
		this.operation("批量删除意见反馈", i);
		return "redirect:/complains/getAllComplains.action";
	}

	// 更新数据
	@RequestMapping("updateComplains.action")
	public String updateComplains(Complains complains) {
		int i = this.complainsService.updateComplains(complains);
		this.operation("修改意见反馈", i);
		return "redirect:/complains/getAllComplains.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateComplains.action")
	@ResponseBody //将java对象转为json格式的数据
	public String xupdateComplains(Complains complains) {
		int i = this.complainsService.updateComplains(complains);
		JSONObject json = new JSONObject();
		json.put("result", i);
		//System.out.println(json.toString());
		this.operation("Ajax修改意见反馈", i);
		return json.toString();
	}

	// 更新状态
	@RequestMapping("status.action")
	public String status(String id) {
		String status = "";
		Complains complains = this.complainsService.getComplainsById(id);
		if (status.equals(complains.getStatus())) {
			status = "";
		}
		complains.setStatus(status);
		int i = this.complainsService.updateComplains(complains);
		this.operation("更新意见反馈状态", i);
		return "redirect:/complains/getAllComplains.action";
	}

	// 显示全部数据
	@RequestMapping("getAllComplains.action")
	public String getAllComplains(String number) {
		List<Complains> complainsList = this.complainsService.getAllComplains();
		PageHelper.getUserPage(complainsList, "complains", "getAllComplains", 10, number, this.getRequest());
		this.operation("查看意见反馈数据", 1);
		return "admin/listcomplains";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody //将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Complains> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Complains> list = this.complainsService.getAllComplains();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看意见反馈数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryComplainsByCond.action")
	public String queryComplainsByCond(String cond, String name, String number) {
		Complains complains = new Complains();
		if(cond != null){
			if ("usersid".equals(cond)) {
				complains.setUsersid(name);
			}
			if ("title".equals(cond)) {
				complains.setTitle(name);
			}
			if ("contents".equals(cond)) {
				complains.setContents(name);
			}
			if ("addtime".equals(cond)) {
				complains.setAddtime(name);
			}
			if ("status".equals(cond)) {
				complains.setStatus(name);
			}
			if ("reps".equals(cond)) {
				complains.setReps(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.complainsService.getComplainsByLike(complains), "complains", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/querycomplains";
	}

	// 按主键查询数据
	@RequestMapping("getComplainsById.action")
	public String getComplainsById(String id) {
		Complains complains = this.complainsService.getComplainsById(id);
		this.getRequest().setAttribute("complains", complains);
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		return "admin/editcomplains";
	}

	// 按主键查询数据
	@RequestMapping("queryComplainsById.action")
	public String queryComplainsById(String id) {
		Complains complains = this.complainsService.getComplainsById(id);
		this.getRequest().setAttribute("complains", complains);
		this.operation("查看意见反馈数据", 1);
		return "admin/complains";
	}

	// 工具方法 无调用可删
	public String export(List<Complains> complainsList) {
		Excel excel = new Excel();
		String banner = "意见反馈表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "用户,标题,内容,发布日期,状态,回复内容";
		List<String[]> strList = new ArrayList<String[]>();
		for (Complains complains : complainsList) {
			String strTemp = complains.getUsername() + "," + complains.getTitle() + "," + complains.getContents() + "," + complains.getAddtime() + "," + complains.getStatus() + "," + complains.getReps();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
