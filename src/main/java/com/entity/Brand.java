package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 手机品牌表的实体类
public class Brand {
	private String brandid = "B"+VeDate.getStringId(); // 生成主键编号
	private String brandname; // 品牌名称
	private String addtime; // 创建日期
	private String memo; // 备注

	public String getBrandid() {
		return this.brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getBrandname() {
		return this.brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}


	// 重载方法 生成JSON类型字符串 
	@Override
	public String toString() {
		return this.toJsonString();
	}

	//直接转换成JSON字符串
	private String toJsonString() {
		JSONObject jsonString = new JSONObject();
		jsonString.put("brandid", this.brandid); // 主键编号
		jsonString.put("brandname", this.brandname); // 品牌名称
		jsonString.put("addtime", this.addtime); // 创建日期
		jsonString.put("memo", this.memo); // 备注
		return jsonString.toString();
	}




}




