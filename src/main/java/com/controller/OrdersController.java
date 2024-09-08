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
import com.entity.Orders;
import com.service.OrdersService;
import com.entity.Users;
import com.entity.Brand;
import com.entity.Phone;
import com.entity.Admin;
import com.service.UsersService;
import com.service.BrandService;
import com.service.PhoneService;
import com.service.AdminService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;

//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/orders", produces = "text/plain;charset=utf-8")
public class OrdersController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private AdminService adminService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createOrders.action")
	public String createOrders() {
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		List<Brand> brandList = this.brandService.getAllBrand();
		this.getRequest().setAttribute("brandList", brandList);
		List<Phone> phoneList = this.phoneService.getAllPhone();
		this.getRequest().setAttribute("phoneList", phoneList);
		List<Admin> adminList = this.adminService.getAllAdmin();
		this.getRequest().setAttribute("adminList", adminList);
		this.getRequest().setAttribute("ordercode", "O" + VeDate.getStringId());
		return "admin/addorders";
	}

	// 添加数据
	@RequestMapping("addOrders.action")
	public String addOrders(Orders orders) {
		orders.setTotal("0");
		orders.setAddtime(VeDate.getStringDateShort());
		orders.setStatus("");
		int i = this.ordersService.insertOrders(orders);
		this.operation("新增维修订单", i);
		return "redirect:/orders/createOrders.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteOrders.action")
	public String deleteOrders(String id) {
		int i = this.ordersService.deleteOrders(id);
		this.operation("按主键删除维修订单", i);
		return "redirect:/orders/getAllOrders.action";
	}

	// 批量删除数据
	@RequestMapping("deleteOrdersByIds.action")
	public String deleteOrdersByIds() {
		String[] ids = this.getRequest().getParameterValues("ordersid");
		int i = 0;
		if (ids != null) {
			for (String ordersid : ids) {
				i += this.ordersService.deleteOrders(ordersid);
			}
		}
		this.operation("批量删除维修订单", i);
		return "redirect:/orders/getAllOrders.action";
	}

	// 更新数据
	@RequestMapping("updateOrders.action")
	public String updateOrders(Orders orders) {
		int i = this.ordersService.updateOrders(orders);
		this.operation("修改维修订单", i);
		return "redirect:/orders/getAllOrders.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateOrders.action")
	@ResponseBody // 将java对象转为json格式的数据
	public String xupdateOrders(Orders orders) {
		int i = this.ordersService.updateOrders(orders);
		JSONObject json = new JSONObject();
		json.put("result", i);
		// System.out.println(json.toString());
		this.operation("Ajax修改维修订单", i);
		return json.toString();
	}

	// 更新状态
	@RequestMapping("status.action")
	public String status(String id) {
		String status = "维修中";
		String adminid = (String) this.getSession().getAttribute("adminid");
		Orders orders = this.ordersService.getOrdersById(id);
		orders.setStatus(status);
		orders.setAdminid(adminid);
		int i = this.ordersService.updateOrders(orders);
		this.operation("更新维修订单状态", i);
		return "redirect:/orders/getUserOrders.action";
	}

	@RequestMapping("over.action")
	public String over(String id) {
		String status = "维修完成";
		Orders orders = this.ordersService.getOrdersById(id);
		orders.setStatus(status);
		int i = this.ordersService.updateOrders(orders);
		this.operation("更新维修订单状态", i);
		return "redirect:/orders/getUserOrders.action";
	}

	// 显示全部数据
	@RequestMapping("getAllOrders.action")
	public String getAllOrders(String number) {
		List<Orders> ordersList = this.ordersService.getAllOrders();
		PageHelper.getUserPage(ordersList, "orders", "getAllOrders", 10, number, this.getRequest());
		this.operation("查看维修订单数据", 1);
		return "admin/listorders";
	}

	@RequestMapping("getStatusOrders.action")
	public String getStatusOrders(String number) {
		// String adminid = (String) this.getSession().getAttribute("adminid");
		Orders orders = new Orders();
		orders.setStatus("待维修");
		List<Orders> ordersList = this.ordersService.getOrdersByCond(orders);
		PageHelper.getUserPage(ordersList, "orders", "getStatusOrders", 10, number, this.getRequest());
		this.operation("查看维修订单数据", 1);
		return "admin/zlistorders";
	}

	// 显示全部数据
	@RequestMapping("getUserOrders.action")
	public String getUserOrders(String number) {
		String adminid = (String) this.getSession().getAttribute("adminid");
		Orders orders = new Orders();
		orders.setAdminid(adminid);
		List<Orders> ordersList = this.ordersService.getOrdersByCond(orders);
		PageHelper.getUserPage(ordersList, "orders", "getUserOrders", 10, number, this.getRequest());
		this.operation("查看维修订单数据", 1);
		return "admin/xlistorders";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody // 将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Orders> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Orders> list = this.ordersService.getAllOrders();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看维修订单数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryOrdersByCond.action")
	public String queryOrdersByCond(String cond, String name, String number) {
		Orders orders = new Orders();
		if (cond != null) {
			if ("ordercode".equals(cond)) {
				orders.setOrdercode(name);
			}
			if ("usersid".equals(cond)) {
				orders.setUsersid(name);
			}
			if ("brandid".equals(cond)) {
				orders.setBrandid(name);
			}
			if ("phoneid".equals(cond)) {
				orders.setPhoneid(name);
			}
			if ("iszaibao".equals(cond)) {
				orders.setIszaibao(name);
			}
			if ("total".equals(cond)) {
				orders.setTotal(name);
			}
			if ("addtime".equals(cond)) {
				orders.setAddtime(name);
			}
			if ("status".equals(cond)) {
				orders.setStatus(name);
			}
			if ("adminid".equals(cond)) {
				orders.setAdminid(name);
			}
			if ("memo".equals(cond)) {
				orders.setMemo(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.ordersService.getOrdersByLike(orders), "orders", nameList, valueList, 10, number,
				this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/queryorders";
	}

	// 按主键查询数据
	@RequestMapping("getOrdersById.action")
	public String getOrdersById(String id) {
		Orders orders = this.ordersService.getOrdersById(id);
		this.getRequest().setAttribute("orders", orders);
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		List<Brand> brandList = this.brandService.getAllBrand();
		this.getRequest().setAttribute("brandList", brandList);
		List<Phone> phoneList = this.phoneService.getAllPhone();
		this.getRequest().setAttribute("phoneList", phoneList);
		List<Admin> adminList = this.adminService.getAllAdmin();
		this.getRequest().setAttribute("adminList", adminList);
		return "admin/editorders";
	}

	// 按主键查询数据
	@RequestMapping("queryOrdersById.action")
	public String queryOrdersById(String id) {
		Orders orders = this.ordersService.getOrdersById(id);
		this.getRequest().setAttribute("orders", orders);
		this.operation("查看维修订单数据", 1);
		return "admin/orders";
	}

	// 工具方法 无调用可删
	public String export(List<Orders> ordersList) {
		Excel excel = new Excel();
		String banner = "维修订单表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "维修单号,用户,品牌,手机型号,是否在保,总计收费,下单日期,状态,维修人,备注";
		List<String[]> strList = new ArrayList<String[]>();
		for (Orders orders : ordersList) {
			String strTemp = orders.getOrdercode() + "," + orders.getUsername() + "," + orders.getBrandname() + ","
					+ orders.getPhonename() + "," + orders.getIszaibao() + "," + orders.getTotal() + ","
					+ orders.getAddtime() + "," + orders.getStatus() + "," + orders.getRealname() + ","
					+ orders.getMemo();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
