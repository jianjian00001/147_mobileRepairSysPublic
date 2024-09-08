package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 配件消耗表的实体类
public class Extendx {
	private String extendxid = "E"+VeDate.getStringId(); // 生成主键编号
	private String ordersid; // 维修单
	private String partxid; // 配件
	private String num; // 消耗数量
	private String price; // 单价
	private String total; // 总计
	private String addtime; // 消耗日期
	private String adminid; // 经手人
	private String memo; // 备注
	private String ordercode; // 映射数据
	private String partxname; // 映射数据
	private String realname; // 映射数据
	private Orders orders;// 多对一映射类
	private Partx partx;// 多对一映射类
	private Admin admin;// 多对一映射类

	public String getExtendxid() {
		return this.extendxid;
	}

	public void setExtendxid(String extendxid) {
		this.extendxid = extendxid;
	}

	public String getOrdersid() {
		return this.ordersid;
	}

	public void setOrdersid(String ordersid) {
		this.ordersid = ordersid;
	}

	public String getPartxid() {
		return this.partxid;
	}

	public void setPartxid(String partxid) {
		this.partxid = partxid;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public Orders getOrders() {
		return this.orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Partx getPartx() {
		return this.partx;
	}

	public void setPartx(Partx partx) {
		this.partx = partx;
	}

	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getOrdercode() {
		return this.ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getPartxname() {
		return this.partxname;
	}

	public void setPartxname(String partxname) {
		this.partxname = partxname;
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
		jsonString.put("extendxid", this.extendxid); // 主键编号
		jsonString.put("ordersid", this.ordersid); // 维修单
		jsonString.put("partxid", this.partxid); // 配件
		jsonString.put("num", this.num); // 消耗数量
		jsonString.put("price", this.price); // 单价
		jsonString.put("total", this.total); // 总计
		jsonString.put("addtime", this.addtime); // 消耗日期
		jsonString.put("adminid", this.adminid); // 经手人
		jsonString.put("memo", this.memo); // 备注
		jsonString.put("Orders", this.orders); // 多对一映射类
		jsonString.put("Partx", this.partx); // 多对一映射类
		jsonString.put("Admin", this.admin); // 多对一映射类
		jsonString.put("ordercode", this.ordercode); // 映射数据
		jsonString.put("partxname", this.partxname); // 映射数据
		jsonString.put("realname", this.realname); // 映射数据
		return jsonString.toString();
	}




}




