package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.Orders;
import com.entity.Phone;
import com.service.OrdersService;
import com.service.PhoneService;

//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/ajax", produces = "text/plain;charset=utf-8")
public class AjaxController extends BaseController {
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private OrdersService ordersService;

	@RequestMapping("getPhone.action")
	@ResponseBody
	public String getPhone(String brandid) {
		Phone p = new Phone();
		p.setBrandid(brandid);
		List<Phone> list = this.phoneService.getPhoneByCond(p);
		JSONArray phoneid = new JSONArray();
		JSONArray phonename = new JSONArray();
		for (Phone x : list) {
			phoneid.add(x.getPhoneid());
			phonename.add(x.getPhonename());
		}
		JSONObject json = new JSONObject();
		json.put("phoneid", phoneid.toString().replaceAll("\"", ""));
		json.put("phonename", phonename.toString().replaceAll("\"", ""));
		System.out.println(json.toString());
		return json.toString();
	}

	@RequestMapping("updateOrders.action")
	@ResponseBody
	public String updateOrders() {
		String ordersid = this.getRequest().getParameter("ordersid");
		String iszaibao = this.getRequest().getParameter("iszaibao");
		Orders orders = this.ordersService.getOrdersById(ordersid);
		if ("在保".equals(iszaibao)) {
			orders.setTotal("0");
		}
		orders.setIszaibao(iszaibao);
		orders.setStatus("待维修");
		JSONObject json = new JSONObject();
		int num = this.ordersService.updateOrders(orders);
		if (num > 0) {
			json.put("code", num);
			json.put("success", true);
			json.put("message", "更新成功");
		} else {
			json.put("code", num);
			json.put("success", false);
			json.put("message", "更新失败");
		}
		return json.toString();
	}
}
