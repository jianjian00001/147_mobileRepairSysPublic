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
import com.entity.Extendx;
import com.service.ExtendxService;
import com.entity.Orders;
import com.entity.Partx;
import com.entity.Admin;
import com.service.OrdersService;
import com.service.PartxService;
import com.service.AdminService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;

//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/extendx", produces = "text/plain;charset=utf-8")
public class ExtendxController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private ExtendxService extendxService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private PartxService partxService;
	@Autowired
	private AdminService adminService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createExtendx.action")
	public String createExtendx() {
		String id = this.getRequest().getParameter("id");
		Orders orders = this.ordersService.getOrdersById(id);
		Partx p = new Partx();
		p.setPhoneid(orders.getPhoneid());
		List<Partx> partxList = this.partxService.getPartxByCond(p);
		this.getRequest().setAttribute("partxList", partxList);
		this.getRequest().setAttribute("orders", orders);
		return "admin/addextendx";
	}

	// 添加数据
	@RequestMapping("addExtendx.action")
	public String addExtendx(Extendx extendx) {
		String adminid = (String) this.getSession().getAttribute("adminid");
		Partx p = this.partxService.getPartxById(extendx.getPartxid());
		extendx.setPrice(p.getPrice());
		double total = Double.parseDouble(p.getPrice()) * Double.parseDouble(extendx.getNum());
		extendx.setTotal("" + VeDate.getDouble(total));
		extendx.setAddtime(VeDate.getStringDateShort());
		extendx.setAdminid(adminid);
		int i = this.extendxService.insertExtendx(extendx);
		this.operation("新增配件消耗", i);
		System.out.println("   ordersid  ====>  " + extendx.getOrdersid());
		Orders orders = this.ordersService.getOrdersById(extendx.getOrdersid());
		if ("在保".equals(orders.getIszaibao())) {
			orders.setTotal("0");
			this.ordersService.updateOrders(orders);
		} else {
			total = Double.parseDouble(p.getPrice()) * Double.parseDouble(extendx.getNum())
					+ Double.parseDouble(orders.getTotal());
			orders.setTotal("" + VeDate.getDouble(total));
			this.ordersService.updateOrders(orders);
		}
		p.setStorage("" + (Integer.parseInt(p.getStorage()) - Integer.parseInt(extendx.getNum())));
		this.partxService.updatePartx(p);
		return "redirect:/extendx/createExtendx.action?id=" + extendx.getOrdersid();
	}

	// 通过主键删除数据
	@RequestMapping("deleteExtendx.action")
	public String deleteExtendx(String id) {
		int i = this.extendxService.deleteExtendx(id);
		this.operation("按主键删除配件消耗", i);
		return "redirect:/extendx/getAllExtendx.action";
	}

	// 批量删除数据
	@RequestMapping("deleteExtendxByIds.action")
	public String deleteExtendxByIds() {
		String[] ids = this.getRequest().getParameterValues("extendxid");
		int i = 0;
		if (ids != null) {
			for (String extendxid : ids) {
				i += this.extendxService.deleteExtendx(extendxid);
			}
		}
		this.operation("批量删除配件消耗", i);
		return "redirect:/extendx/getAllExtendx.action";
	}

	// 更新数据
	@RequestMapping("updateExtendx.action")
	public String updateExtendx(Extendx extendx) {
		int i = this.extendxService.updateExtendx(extendx);
		this.operation("修改配件消耗", i);
		return "redirect:/extendx/getAllExtendx.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateExtendx.action")
	@ResponseBody // 将java对象转为json格式的数据
	public String xupdateExtendx(Extendx extendx) {
		int i = this.extendxService.updateExtendx(extendx);
		JSONObject json = new JSONObject();
		json.put("result", i);
		// System.out.println(json.toString());
		this.operation("Ajax修改配件消耗", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllExtendx.action")
	public String getAllExtendx(String number) {
		List<Extendx> extendxList = this.extendxService.getAllExtendx();
		PageHelper.getUserPage(extendxList, "extendx", "getAllExtendx", 10, number, this.getRequest());
		this.operation("查看配件消耗数据", 1);
		return "admin/listextendx";
	}

	// 显示全部数据
	@RequestMapping("getUserExtendx.action")
	public String getUserExtendx(String number) {
		String adminid = (String) this.getSession().getAttribute("adminid");
		Extendx extendx = new Extendx();
		extendx.setAdminid(adminid);
		List<Extendx> extendxList = this.extendxService.getExtendxByCond(extendx);
		PageHelper.getUserPage(extendxList, "extendx", "getUserExtendx", 10, number, this.getRequest());
		this.operation("查看配件消耗数据", 1);
		return "admin/xlistextendx";
	}

	@RequestMapping("getExtendx.action")
	public String getExtendx(String id) {
		Extendx extendx = new Extendx();
		extendx.setOrdersid(id);
		List<Extendx> extendxList = this.extendxService.getExtendxByCond(extendx);
		this.getRequest().setAttribute("extendxList", extendxList);
		return "admin/zlistextendx";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody // 将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Extendx> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Extendx> list = this.extendxService.getAllExtendx();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看配件消耗数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryExtendxByCond.action")
	public String queryExtendxByCond(String cond, String name, String number) {
		Extendx extendx = new Extendx();
		if (cond != null) {
			if ("ordersid".equals(cond)) {
				extendx.setOrdersid(name);
			}
			if ("partxid".equals(cond)) {
				extendx.setPartxid(name);
			}
			if ("num".equals(cond)) {
				extendx.setNum(name);
			}
			if ("price".equals(cond)) {
				extendx.setPrice(name);
			}
			if ("total".equals(cond)) {
				extendx.setTotal(name);
			}
			if ("addtime".equals(cond)) {
				extendx.setAddtime(name);
			}
			if ("adminid".equals(cond)) {
				extendx.setAdminid(name);
			}
			if ("memo".equals(cond)) {
				extendx.setMemo(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.extendxService.getExtendxByLike(extendx), "extendx", nameList, valueList, 10, number,
				this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/queryextendx";
	}

	// 按主键查询数据
	@RequestMapping("getExtendxById.action")
	public String getExtendxById(String id) {
		Extendx extendx = this.extendxService.getExtendxById(id);
		this.getRequest().setAttribute("extendx", extendx);
		List<Orders> ordersList = this.ordersService.getAllOrders();
		this.getRequest().setAttribute("ordersList", ordersList);
		List<Partx> partxList = this.partxService.getAllPartx();
		this.getRequest().setAttribute("partxList", partxList);
		List<Admin> adminList = this.adminService.getAllAdmin();
		this.getRequest().setAttribute("adminList", adminList);
		return "admin/editextendx";
	}

	// 按主键查询数据
	@RequestMapping("queryExtendxById.action")
	public String queryExtendxById(String id) {
		Extendx extendx = this.extendxService.getExtendxById(id);
		this.getRequest().setAttribute("extendx", extendx);
		this.operation("查看配件消耗数据", 1);
		return "admin/extendx";
	}

	// 工具方法 无调用可删
	public String export(List<Extendx> extendxList) {
		Excel excel = new Excel();
		String banner = "配件消耗表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "维修单,配件,消耗数量,单价,总计,消耗日期,经手人,备注";
		List<String[]> strList = new ArrayList<String[]>();
		for (Extendx extendx : extendxList) {
			String strTemp = extendx.getOrdercode() + "," + extendx.getPartxname() + "," + extendx.getNum() + ","
					+ extendx.getPrice() + "," + extendx.getTotal() + "," + extendx.getAddtime() + ","
					+ extendx.getRealname() + "," + extendx.getMemo();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
