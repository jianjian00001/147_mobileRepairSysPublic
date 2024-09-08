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
import com.entity.Phone;
import com.service.PhoneService;
import com.entity.Brand;
import com.service.BrandService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/phone" , produces = "text/plain;charset=utf-8")
public class PhoneController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private BrandService brandService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createPhone.action")
	public String createPhone() {
		List<Brand> brandList = this.brandService.getAllBrand();
		this.getRequest().setAttribute("brandList", brandList);
		return "admin/addphone";
	}
	// 添加数据
	@RequestMapping("addPhone.action")
	public String addPhone(Phone phone) {
		phone.setAddtime(VeDate.getStringDateShort());
		phone.setNum("0");
		int i = this.phoneService.insertPhone(phone);
		this.operation("新增手机", i);
		return "redirect:/phone/createPhone.action";
	}

	// 通过主键删除数据
	@RequestMapping("deletePhone.action")
	public String deletePhone(String id) {
		int i = this.phoneService.deletePhone(id);
		this.operation("按主键删除手机", i);
		return "redirect:/phone/getAllPhone.action";
	}

	// 批量删除数据
	@RequestMapping("deletePhoneByIds.action")
	public String deletePhoneByIds() {
		String[] ids = this.getRequest().getParameterValues("phoneid");
		int i = 0;
		if (ids != null) {
			for (String phoneid : ids) {
				i += this.phoneService.deletePhone(phoneid);
			}
		}
		this.operation("批量删除手机", i);
		return "redirect:/phone/getAllPhone.action";
	}

	// 更新数据
	@RequestMapping("updatePhone.action")
	public String updatePhone(Phone phone) {
		int i = this.phoneService.updatePhone(phone);
		this.operation("修改手机", i);
		return "redirect:/phone/getAllPhone.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdatePhone.action")
	@ResponseBody //将java对象转为json格式的数据
	public String xupdatePhone(Phone phone) {
		int i = this.phoneService.updatePhone(phone);
		JSONObject json = new JSONObject();
		json.put("result", i);
		//System.out.println(json.toString());
		this.operation("Ajax修改手机", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllPhone.action")
	public String getAllPhone(String number) {
		List<Phone> phoneList = this.phoneService.getAllPhone();
		PageHelper.getUserPage(phoneList, "phone", "getAllPhone", 10, number, this.getRequest());
		this.operation("查看手机数据", 1);
		return "admin/listphone";
	}

	// 显示全部数据
	@RequestMapping("getUserPhone.action")
	public String getUserPhone(String number) {
		// String adminid = (String) this.getSession().getAttribute("adminid");
		Phone phone = new Phone();
		List<Phone> phoneList = this.phoneService.getPhoneByCond(phone);
		PageHelper.getUserPage(phoneList, "phone", "getUserPhone", 10, number, this.getRequest());
		this.operation("查看手机数据", 1);
		return "admin/xlistphone";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody //将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Phone> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Phone> list = this.phoneService.getAllPhone();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看手机数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryPhoneByCond.action")
	public String queryPhoneByCond(String cond, String name, String number) {
		Phone phone = new Phone();
		if(cond != null){
			if ("phonename".equals(cond)) {
				phone.setPhonename(name);
			}
			if ("brandid".equals(cond)) {
				phone.setBrandid(name);
			}
			if ("price".equals(cond)) {
				phone.setPrice(name);
			}
			if ("addtime".equals(cond)) {
				phone.setAddtime(name);
			}
			if ("num".equals(cond)) {
				phone.setNum(name);
			}
			if ("memo".equals(cond)) {
				phone.setMemo(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.phoneService.getPhoneByLike(phone), "phone", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/queryphone";
	}

	// 按主键查询数据
	@RequestMapping("getPhoneById.action")
	public String getPhoneById(String id) {
		Phone phone = this.phoneService.getPhoneById(id);
		this.getRequest().setAttribute("phone", phone);
		List<Brand> brandList = this.brandService.getAllBrand();
		this.getRequest().setAttribute("brandList", brandList);
		return "admin/editphone";
	}

	// 按主键查询数据
	@RequestMapping("queryPhoneById.action")
	public String queryPhoneById(String id) {
		Phone phone = this.phoneService.getPhoneById(id);
		this.getRequest().setAttribute("phone", phone);
		this.operation("查看手机数据", 1);
		return "admin/phone";
	}

	// 工具方法 无调用可删
	public String export(List<Phone> phoneList) {
		Excel excel = new Excel();
		String banner = "手机表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "手机型号,手机品牌,基础价格,创建日期,维修数,备注";
		List<String[]> strList = new ArrayList<String[]>();
		for (Phone phone : phoneList) {
			String strTemp = phone.getPhonename() + "," + phone.getBrandname() + "," + phone.getPrice() + "," + phone.getAddtime() + "," + phone.getNum() + "," + phone.getMemo();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
