package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 操作日志表的实体类
public class Operlog {
	private String operlogid = "O"+VeDate.getStringId(); // 生成主键编号
	private String opername; // 操作名称
	private String methods; // 操作方法
	private String opers; // 操作人
	private String operurl; // 操作路径
	private String ipaddr; // IP地址
	private String operstatus; // 操作状态
	private String opertime; // 操作日期

	public String getOperlogid() {
		return this.operlogid;
	}

	public void setOperlogid(String operlogid) {
		this.operlogid = operlogid;
	}

	public String getOpername() {
		return this.opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public String getMethods() {
		return this.methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getOpers() {
		return this.opers;
	}

	public void setOpers(String opers) {
		this.opers = opers;
	}

	public String getOperurl() {
		return this.operurl;
	}

	public void setOperurl(String operurl) {
		this.operurl = operurl;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getOperstatus() {
		return this.operstatus;
	}

	public void setOperstatus(String operstatus) {
		this.operstatus = operstatus;
	}

	public String getOpertime() {
		return this.opertime;
	}

	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}


	// 重载方法 生成JSON类型字符串 
	@Override
	public String toString() {
		return this.toJsonString();
	}

	//直接转换成JSON字符串
	private String toJsonString() {
		JSONObject jsonString = new JSONObject();
		jsonString.put("operlogid", this.operlogid); // 主键编号
		jsonString.put("opername", this.opername); // 操作名称
		jsonString.put("methods", this.methods); // 操作方法
		jsonString.put("opers", this.opers); // 操作人
		jsonString.put("operurl", this.operurl); // 操作路径
		jsonString.put("ipaddr", this.ipaddr); // IP地址
		jsonString.put("operstatus", this.operstatus); // 操作状态
		jsonString.put("opertime", this.opertime); // 操作日期
		return jsonString.toString();
	}




}




