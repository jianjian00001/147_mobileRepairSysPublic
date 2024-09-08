package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 用户评价表的实体类
public class Topic {
	private String topicid = "T"+VeDate.getStringId(); // 生成主键编号
	private String ordersid; // 维修单
	private String usersid; // 用户
	private String num; // 评分
	private String contents; // 内容
	private String addtime; // 发布日期
	private String ordercode; // 映射数据
	private String username; // 映射数据
	private Orders orders;// 多对一映射类
	private Users users;// 多对一映射类

	public String getTopicid() {
		return this.topicid;
	}

	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}

	public String getOrdersid() {
		return this.ordersid;
	}

	public void setOrdersid(String ordersid) {
		this.ordersid = ordersid;
	}

	public String getUsersid() {
		return this.usersid;
	}

	public void setUsersid(String usersid) {
		this.usersid = usersid;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public Orders getOrders() {
		return this.orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getOrdercode() {
		return this.ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	// 重载方法 生成JSON类型字符串 
	@Override
	public String toString() {
		return this.toJsonString();
	}

	//直接转换成JSON字符串
	private String toJsonString() {
		JSONObject jsonString = new JSONObject();
		jsonString.put("topicid", this.topicid); // 主键编号
		jsonString.put("ordersid", this.ordersid); // 维修单
		jsonString.put("usersid", this.usersid); // 用户
		jsonString.put("num", this.num); // 评分
		jsonString.put("contents", this.contents); // 内容
		jsonString.put("addtime", this.addtime); // 发布日期
		jsonString.put("Orders", this.orders); // 多对一映射类
		jsonString.put("Users", this.users); // 多对一映射类
		jsonString.put("ordercode", this.ordercode); // 映射数据
		jsonString.put("username", this.username); // 映射数据
		return jsonString.toString();
	}




}




