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
import com.entity.Brand;
import com.service.BrandService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/brand" , produces = "text/plain;charset=utf-8")
public class BrandController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private BrandService brandService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createBrand.action")
	public String createBrand() {
		return "admin/addbrand";
	}
	// 添加数据
	@RequestMapping("addBrand.action")
	public String addBrand(Brand brand) {
		brand.setAddtime(VeDate.getStringDateShort());
		int i = this.brandService.insertBrand(brand);
		this.operation("新增手机品牌", i);
		return "redirect:/brand/createBrand.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteBrand.action")
	public String deleteBrand(String id) {
		int i = this.brandService.deleteBrand(id);
		this.operation("按主键删除手机品牌", i);
		return "redirect:/brand/getAllBrand.action";
	}

	// 批量删除数据
	@RequestMapping("deleteBrandByIds.action")
	public String deleteBrandByIds() {
		String[] ids = this.getRequest().getParameterValues("brandid");
		int i = 0;
		if (ids != null) {
			for (String brandid : ids) {
				i += this.brandService.deleteBrand(brandid);
			}
		}
		this.operation("批量删除手机品牌", i);
		return "redirect:/brand/getAllBrand.action";
	}

	// 更新数据
	@RequestMapping("updateBrand.action")
	public String updateBrand(Brand brand) {
		int i = this.brandService.updateBrand(brand);
		this.operation("修改手机品牌", i);
		return "redirect:/brand/getAllBrand.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateBrand.action")
	@ResponseBody //将java对象转为json格式的数据
	public String xupdateBrand(Brand brand) {
		int i = this.brandService.updateBrand(brand);
		JSONObject json = new JSONObject();
		json.put("result", i);
		//System.out.println(json.toString());
		this.operation("Ajax修改手机品牌", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllBrand.action")
	public String getAllBrand(String number) {
		List<Brand> brandList = this.brandService.getAllBrand();
		PageHelper.getUserPage(brandList, "brand", "getAllBrand", 10, number, this.getRequest());
		this.operation("查看手机品牌数据", 1);
		return "admin/listbrand";
	}

	// 显示全部数据
	@RequestMapping("getUserBrand.action")
	public String getUserBrand(String number) {
		// String adminid = (String) this.getSession().getAttribute("adminid");
		Brand brand = new Brand();
		List<Brand> brandList = this.brandService.getBrandByCond(brand);
		PageHelper.getUserPage(brandList, "brand", "getUserBrand", 10, number, this.getRequest());
		this.operation("查看手机品牌数据", 1);
		return "admin/xlistbrand";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody //将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Brand> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Brand> list = this.brandService.getAllBrand();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看手机品牌数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryBrandByCond.action")
	public String queryBrandByCond(String cond, String name, String number) {
		Brand brand = new Brand();
		if(cond != null){
			if ("brandname".equals(cond)) {
				brand.setBrandname(name);
			}
			if ("addtime".equals(cond)) {
				brand.setAddtime(name);
			}
			if ("memo".equals(cond)) {
				brand.setMemo(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.brandService.getBrandByLike(brand), "brand", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/querybrand";
	}

	// 按主键查询数据
	@RequestMapping("getBrandById.action")
	public String getBrandById(String id) {
		Brand brand = this.brandService.getBrandById(id);
		this.getRequest().setAttribute("brand", brand);
		return "admin/editbrand";
	}

	// 按主键查询数据
	@RequestMapping("queryBrandById.action")
	public String queryBrandById(String id) {
		Brand brand = this.brandService.getBrandById(id);
		this.getRequest().setAttribute("brand", brand);
		this.operation("查看手机品牌数据", 1);
		return "admin/brand";
	}

	// 工具方法 无调用可删
	public String export(List<Brand> brandList) {
		Excel excel = new Excel();
		String banner = "手机品牌表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "品牌名称,创建日期,备注";
		List<String[]> strList = new ArrayList<String[]>();
		for (Brand brand : brandList) {
			String strTemp = brand.getBrandname() + "," + brand.getAddtime() + "," + brand.getMemo();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
