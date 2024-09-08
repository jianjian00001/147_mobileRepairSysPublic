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
import com.entity.Topic;
import com.service.TopicService;
import com.entity.Orders;
import com.entity.Users;
import com.service.OrdersService;
import com.service.UsersService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/topic" , produces = "text/plain;charset=utf-8")
public class TopicController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private TopicService topicService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private UsersService usersService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createTopic.action")
	public String createTopic() {
		List<Orders> ordersList = this.ordersService.getAllOrders();
		this.getRequest().setAttribute("ordersList", ordersList);
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		return "admin/addtopic";
	}
	// 添加数据
	@RequestMapping("addTopic.action")
	public String addTopic(Topic topic) {
		topic.setAddtime(VeDate.getStringDateShort());
		int i = this.topicService.insertTopic(topic);
		this.operation("新增用户评价", i);
		return "redirect:/topic/createTopic.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteTopic.action")
	public String deleteTopic(String id) {
		int i = this.topicService.deleteTopic(id);
		this.operation("按主键删除用户评价", i);
		return "redirect:/topic/getAllTopic.action";
	}

	// 批量删除数据
	@RequestMapping("deleteTopicByIds.action")
	public String deleteTopicByIds() {
		String[] ids = this.getRequest().getParameterValues("topicid");
		int i = 0;
		if (ids != null) {
			for (String topicid : ids) {
				i += this.topicService.deleteTopic(topicid);
			}
		}
		this.operation("批量删除用户评价", i);
		return "redirect:/topic/getAllTopic.action";
	}

	// 更新数据
	@RequestMapping("updateTopic.action")
	public String updateTopic(Topic topic) {
		int i = this.topicService.updateTopic(topic);
		this.operation("修改用户评价", i);
		return "redirect:/topic/getAllTopic.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateTopic.action")
	@ResponseBody //将java对象转为json格式的数据
	public String xupdateTopic(Topic topic) {
		int i = this.topicService.updateTopic(topic);
		JSONObject json = new JSONObject();
		json.put("result", i);
		//System.out.println(json.toString());
		this.operation("Ajax修改用户评价", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllTopic.action")
	public String getAllTopic(String number) {
		List<Topic> topicList = this.topicService.getAllTopic();
		PageHelper.getUserPage(topicList, "topic", "getAllTopic", 10, number, this.getRequest());
		this.operation("查看用户评价数据", 1);
		return "admin/listtopic";
	}

	// 显示全部数据
	@RequestMapping("getUserTopic.action")
	public String getUserTopic(String number) {
		// String adminid = (String) this.getSession().getAttribute("adminid");
		Topic topic = new Topic();
		List<Topic> topicList = this.topicService.getTopicByCond(topic);
		PageHelper.getUserPage(topicList, "topic", "getUserTopic", 10, number, this.getRequest());
		this.operation("查看用户评价数据", 1);
		return "admin/xlisttopic";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody //将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Topic> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Topic> list = this.topicService.getAllTopic();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看用户评价数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryTopicByCond.action")
	public String queryTopicByCond(String cond, String name, String number) {
		Topic topic = new Topic();
		if(cond != null){
			if ("ordersid".equals(cond)) {
				topic.setOrdersid(name);
			}
			if ("usersid".equals(cond)) {
				topic.setUsersid(name);
			}
			if ("num".equals(cond)) {
				topic.setNum(name);
			}
			if ("contents".equals(cond)) {
				topic.setContents(name);
			}
			if ("addtime".equals(cond)) {
				topic.setAddtime(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.topicService.getTopicByLike(topic), "topic", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/querytopic";
	}

	// 按主键查询数据
	@RequestMapping("getTopicById.action")
	public String getTopicById(String id) {
		Topic topic = this.topicService.getTopicById(id);
		this.getRequest().setAttribute("topic", topic);
		List<Orders> ordersList = this.ordersService.getAllOrders();
		this.getRequest().setAttribute("ordersList", ordersList);
		List<Users> usersList = this.usersService.getAllUsers();
		this.getRequest().setAttribute("usersList", usersList);
		return "admin/edittopic";
	}

	// 按主键查询数据
	@RequestMapping("queryTopicById.action")
	public String queryTopicById(String id) {
		Topic topic = this.topicService.getTopicById(id);
		this.getRequest().setAttribute("topic", topic);
		this.operation("查看用户评价数据", 1);
		return "admin/topic";
	}

	// 工具方法 无调用可删
	public String export(List<Topic> topicList) {
		Excel excel = new Excel();
		String banner = "用户评价表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "维修单,用户,评分,内容,发布日期";
		List<String[]> strList = new ArrayList<String[]>();
		for (Topic topic : topicList) {
			String strTemp = topic.getOrdercode() + "," + topic.getUsername() + "," + topic.getNum() + "," + topic.getContents() + "," + topic.getAddtime();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
