package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 手机表的实体类
public class Phone {
	private String phoneid = "P"+VeDate.getStringId(); // 生成主键编号
	private String phonename; // 手机型号
	private String brandid; // 手机品牌
	private String price; // 基础价格
	private String addtime; // 创建日期
	private String num; // 维修数
	private String memo; // 备注
	private String brandname; // 映射数据
	private Brand brand;// 多对一映射类

	public String getPhoneid() {
		return this.phoneid;
	}

	public void setPhoneid(String phoneid) {
		this.phoneid = phoneid;
	}

	public String getPhonename() {
		return this.phonename;
	}

	public void setPhonename(String phonename) {
		this.phonename = phonename;
	}

	public String getBrandid() {
		return this.brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getBrandname() {
		return this.brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}


	// 重载方法 生成JSON类型字符串 
	@Override
	public String toString() {
		return this.toJsonString();
	}

	//直接转换成JSON字符串
	private String toJsonString() {
		JSONObject jsonString = new JSONObject();
		jsonString.put("phoneid", this.phoneid); // 主键编号
		jsonString.put("phonename", this.phonename); // 手机型号
		jsonString.put("brandid", this.brandid); // 手机品牌
		jsonString.put("price", this.price); // 基础价格
		jsonString.put("addtime", this.addtime); // 创建日期
		jsonString.put("num", this.num); // 维修数
		jsonString.put("memo", this.memo); // 备注
		jsonString.put("Brand", this.brand); // 多对一映射类
		jsonString.put("brandname", this.brandname); // 映射数据
		return jsonString.toString();
	}




}




