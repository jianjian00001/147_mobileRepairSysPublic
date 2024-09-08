package com.entity;

import com.util.VeDate;
import com.alibaba.fastjson.JSONObject;

// 手机配件表的实体类
public class Partx {
	private String partxid = "P" + VeDate.getStringId(); // 生成主键编号
	private String pno; // 配件号
	private String partxname; // 配件名称
	private String phoneid; // 适配手机
	private String price; // 销售价格
	private String addtime; // 创建日期
	private String memo; // 备注
	private String phonename; // 映射数据
	private Phone phone;// 多对一映射类
	private String storage;

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getPartxid() {
		return this.partxid;
	}

	public void setPartxid(String partxid) {
		this.partxid = partxid;
	}

	public String getPno() {
		return this.pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getPartxname() {
		return this.partxname;
	}

	public void setPartxname(String partxname) {
		this.partxname = partxname;
	}

	public String getPhoneid() {
		return this.phoneid;
	}

	public void setPhoneid(String phoneid) {
		this.phoneid = phoneid;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Phone getPhone() {
		return this.phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public String getPhonename() {
		return this.phonename;
	}

	public void setPhonename(String phonename) {
		this.phonename = phonename;
	}

	// 重载方法 生成JSON类型字符串
	@Override
	public String toString() {
		return this.toJsonString();
	}

	// 直接转换成JSON字符串
	private String toJsonString() {
		JSONObject jsonString = new JSONObject();
		jsonString.put("partxid", this.partxid); // 主键编号
		jsonString.put("pno", this.pno); // 配件号
		jsonString.put("partxname", this.partxname); // 配件名称
		jsonString.put("phoneid", this.phoneid); // 适配手机
		jsonString.put("price", this.price); // 销售价格
		jsonString.put("addtime", this.addtime); // 创建日期
		jsonString.put("memo", this.memo); // 备注
		jsonString.put("Phone", this.phone); // 多对一映射类
		jsonString.put("phonename", this.phonename); // 映射数据
		return jsonString.toString();
	}

}
