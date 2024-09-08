package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 维修订单表的实体类
public class Orders {
	private String ordersid = "O"+VeDate.getStringId(); // 生成主键编号
	private String ordercode; // 维修单号
	private String usersid; // 用户
	private String brandid; // 品牌
	private String phoneid; // 手机型号
	private String iszaibao; // 是否在保
	private String total; // 总计收费
	private String addtime; // 下单日期
	private String status; // 状态
	private String adminid; // 维修人
	private String memo; // 备注
	private String username; // 映射数据
	private String brandname; // 映射数据
	private String phonename; // 映射数据
	private String realname; // 映射数据
	private Users users;// 多对一映射类
	private Brand brand;// 多对一映射类
	private Phone phone;// 多对一映射类
	private Admin admin;// 多对一映射类

	public String getOrdersid() {
		return this.ordersid;
	}

	public void setOrdersid(String ordersid) {
		this.ordersid = ordersid;
	}

	public String getOrdercode() {
		return this.ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getUsersid() {
		return this.usersid;
	}

	public void setUsersid(String usersid) {
		this.usersid = usersid;
	}

	public String getBrandid() {
		return this.brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getPhoneid() {
		return this.phoneid;
	}

	public void setPhoneid(String phoneid) {
		this.phoneid = phoneid;
	}

	public String getIszaibao() {
		return this.iszaibao;
	}

	public void setIszaibao(String iszaibao) {
		this.iszaibao = iszaibao;
	}

	public String getTotal() {
		return this.total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAdminid() {
		return this.adminid;
	}

	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Phone getPhone() {
		return this.phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBrandname() {
		return this.brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getPhonename() {
		return this.phonename;
	}

	public void setPhonename(String phonename) {
		this.phonename = phonename;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	// 重载方法 生成JSON类型字符串 
	@Override
	public String toString() {
		return this.toJsonString();
	}

	//直接转换成JSON字符串
	private String toJsonString() {
		JSONObject jsonString = new JSONObject();
		jsonString.put("ordersid", this.ordersid); // 主键编号
		jsonString.put("ordercode", this.ordercode); // 维修单号
		jsonString.put("usersid", this.usersid); // 用户
		jsonString.put("brandid", this.brandid); // 品牌
		jsonString.put("phoneid", this.phoneid); // 手机型号
		jsonString.put("iszaibao", this.iszaibao); // 是否在保
		jsonString.put("total", this.total); // 总计收费
		jsonString.put("addtime", this.addtime); // 下单日期
		jsonString.put("status", this.status); // 状态
		jsonString.put("adminid", this.adminid); // 维修人
		jsonString.put("memo", this.memo); // 备注
		jsonString.put("Users", this.users); // 多对一映射类
		jsonString.put("Brand", this.brand); // 多对一映射类
		jsonString.put("Phone", this.phone); // 多对一映射类
		jsonString.put("Admin", this.admin); // 多对一映射类
		jsonString.put("username", this.username); // 映射数据
		jsonString.put("brandname", this.brandname); // 映射数据
		jsonString.put("phonename", this.phonename); // 映射数据
		jsonString.put("realname", this.realname); // 映射数据
		return jsonString.toString();
	}




}




