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
import com.entity.Partx;
import com.service.PartxService;
import com.entity.Phone;
import com.service.PhoneService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;

//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/partx", produces = "text/plain;charset=utf-8")
public class PartxController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private PartxService partxService;
	@Autowired
	private PhoneService phoneService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createPartx.action")
	public String createPartx() {
		List<Phone> phoneList = this.phoneService.getAllPhone();
		this.getRequest().setAttribute("phoneList", phoneList);
		this.getRequest().setAttribute("pno", "P" + VeDate.getStringId());
		return "admin/addpartx";
	}

	// 添加数据
	@RequestMapping("addPartx.action")
	public String addPartx(Partx partx) {
		partx.setAddtime(VeDate.getStringDateShort());
		int i = this.partxService.insertPartx(partx);
		this.operation("新增手机配件", i);
		return "redirect:/partx/createPartx.action";
	}

	// 通过主键删除数据
	@RequestMapping("deletePartx.action")
	public String deletePartx(String id) {
		int i = this.partxService.deletePartx(id);
		this.operation("按主键删除手机配件", i);
		return "redirect:/partx/getAllPartx.action";
	}

	// 批量删除数据
	@RequestMapping("deletePartxByIds.action")
	public String deletePartxByIds() {
		String[] ids = this.getRequest().getParameterValues("partxid");
		int i = 0;
		if (ids != null) {
			for (String partxid : ids) {
				i += this.partxService.deletePartx(partxid);
			}
		}
		this.operation("批量删除手机配件", i);
		return "redirect:/partx/getAllPartx.action";
	}

	// 更新数据
	@RequestMapping("updatePartx.action")
	public String updatePartx(Partx partx) {
		int i = this.partxService.updatePartx(partx);
		this.operation("修改手机配件", i);
		return "redirect:/partx/getAllPartx.action";
	}

	@RequestMapping("editPartx.action")
	@ResponseBody // 将java对象转为json格式的数据
	public String editPartx() {
		String partxid = this.getRequest().getParameter("partxid");
		String storage = this.getRequest().getParameter("storage");
		Partx partx = this.partxService.getPartxById(partxid);
		partx.setStorage(storage);
		int i = this.partxService.updatePartx(partx);
		this.operation("补充货源", i);
		JSONObject json = new JSONObject();
		if (i > 0) {
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		System.out.println(json.toString());
		return json.toString();
	}

	// AJAX更新数据
	@RequestMapping("xupdatePartx.action")
	@ResponseBody // 将java对象转为json格式的数据
	public String xupdatePartx(Partx partx) {
		int i = this.partxService.updatePartx(partx);
		JSONObject json = new JSONObject();
		json.put("result", i);
		// System.out.println(json.toString());
		this.operation("Ajax修改手机配件", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllPartx.action")
	public String getAllPartx(String number) {
		List<Partx> partxList = this.partxService.getAllPartx();
		PageHelper.getUserPage(partxList, "partx", "getAllPartx", 10, number, this.getRequest());
		this.operation("查看手机配件数据", 1);
		return "admin/listpartx";
	}

	// 显示全部数据
	@RequestMapping("getUserPartx.action")
	public String getUserPartx(String number) {
		// String adminid = (String) this.getSession().getAttribute("adminid");
		Partx partx = new Partx();
		List<Partx> partxList = this.partxService.getPartxByCond(partx);
		PageHelper.getUserPage(partxList, "partx", "getUserPartx", 10, number, this.getRequest());
		this.operation("查看手机配件数据", 1);
		return "admin/xlistpartx";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody // 将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Partx> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Partx> list = this.partxService.getAllPartx();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看手机配件数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryPartxByCond.action")
	public String queryPartxByCond(String cond, String name, String number) {
		Partx partx = new Partx();
		if (cond != null) {
			if ("pno".equals(cond)) {
				partx.setPno(name);
			}
			if ("partxname".equals(cond)) {
				partx.setPartxname(name);
			}
			if ("phoneid".equals(cond)) {
				partx.setPhoneid(name);
			}
			if ("price".equals(cond)) {
				partx.setPrice(name);
			}
			if ("addtime".equals(cond)) {
				partx.setAddtime(name);
			}
			if ("memo".equals(cond)) {
				partx.setMemo(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.partxService.getPartxByLike(partx), "partx", nameList, valueList, 10, number,
				this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/querypartx";
	}

	// 按主键查询数据
	@RequestMapping("getPartxById.action")
	public String getPartxById(String id) {
		Partx partx = this.partxService.getPartxById(id);
		this.getRequest().setAttribute("partx", partx);
		List<Phone> phoneList = this.phoneService.getAllPhone();
		this.getRequest().setAttribute("phoneList", phoneList);
		return "admin/editpartx";
	}

	@RequestMapping("getPartx.action")
	public String getPartx(String id) {
		Partx partx = this.partxService.getPartxById(id);
		this.getRequest().setAttribute("partx", partx);
		List<Phone> phoneList = this.phoneService.getAllPhone();
		this.getRequest().setAttribute("phoneList", phoneList);
		return "admin/xeditpartx";
	}

	// 按主键查询数据
	@RequestMapping("queryPartxById.action")
	public String queryPartxById(String id) {
		Partx partx = this.partxService.getPartxById(id);
		this.getRequest().setAttribute("partx", partx);
		this.operation("查看手机配件数据", 1);
		return "admin/partx";
	}

	// 工具方法 无调用可删
	public String export(List<Partx> partxList) {
		Excel excel = new Excel();
		String banner = "手机配件表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "配件号,配件名称,适配手机,销售价格,创建日期,备注";
		List<String[]> strList = new ArrayList<String[]>();
		for (Partx partx : partxList) {
			String strTemp = partx.getPno() + "," + partx.getPartxname() + "," + partx.getPhonename() + ","
					+ partx.getPrice() + "," + partx.getAddtime() + "," + partx.getMemo();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
