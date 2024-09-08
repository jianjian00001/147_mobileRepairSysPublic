package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 登录日志表的实体类
public class Loginf {
	private String loginfid = "L"+VeDate.getStringId(); // 生成主键编号
	private String logname; // 登录名
	private String ipaddr; // IP地址
	private String brower; // 浏览器
	private String os; // 操作系统
	private String logstatus; // 登录状态
	private String role; // 登录角色
	private String logtime; // 登录时间

	public String getLoginfid() {
		return this.loginfid;
	}

	public void setLoginfid(String loginfid) {
		this.loginfid = loginfid;
	}

	public String getLogname() {
		return this.logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getBrower() {
		return this.brower;
	}

	public void setBrower(String brower) {
		this.brower = brower;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getLogstatus() {
		return this.logstatus;
	}

	public void setLogstatus(String logstatus) {
		this.logstatus = logstatus;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogtime() {
		return this.logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}


	// 重载方法 生成JSON类型字符串 
	@Override
	public String toString() {
		return this.toJsonString();
	}

	//直接转换成JSON字符串
	private String toJsonString() {
		JSONObject jsonString = new JSONObject();
		jsonString.put("loginfid", this.loginfid); // 主键编号
		jsonString.put("logname", this.logname); // 登录名
		jsonString.put("ipaddr", this.ipaddr); // IP地址
		jsonString.put("brower", this.brower); // 浏览器
		jsonString.put("os", this.os); // 操作系统
		jsonString.put("logstatus", this.logstatus); // 登录状态
		jsonString.put("role", this.role); // 登录角色
		jsonString.put("logtime", this.logtime); // 登录时间
		return jsonString.toString();
	}




}




