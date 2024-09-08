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
import com.entity.Article;
import com.service.ArticleService;
import com.entity.Banner;
import com.service.BannerService;
import com.util.PageHelper;
import com.util.VeDate;
import com.util.Excel;
//定义为控制器
@Controller
// 设置路径
@RequestMapping(value = "/article" , produces = "text/plain;charset=utf-8")
public class ArticleController extends BaseController {
	// @Autowired的作用是自动注入依赖的ServiceBean
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BannerService bannerService;

	// TODO Auto-generated method stub

	// 准备添加数据
	@RequestMapping("createArticle.action")
	public String createArticle() {
		List<Banner> bannerList = this.bannerService.getAllBanner();
		this.getRequest().setAttribute("bannerList", bannerList);
		return "admin/addarticle";
	}
	// 添加数据
	@RequestMapping("addArticle.action")
	public String addArticle(Article article) {
		article.setAddtime(VeDate.getStringDateShort());
		article.setHits("0");
		int i = this.articleService.insertArticle(article);
		this.operation("新增网站内容", i);
		return "redirect:/article/createArticle.action";
	}

	// 通过主键删除数据
	@RequestMapping("deleteArticle.action")
	public String deleteArticle(String id) {
		int i = this.articleService.deleteArticle(id);
		this.operation("按主键删除网站内容", i);
		return "redirect:/article/getAllArticle.action";
	}

	// 批量删除数据
	@RequestMapping("deleteArticleByIds.action")
	public String deleteArticleByIds() {
		String[] ids = this.getRequest().getParameterValues("articleid");
		int i = 0;
		if (ids != null) {
			for (String articleid : ids) {
				i += this.articleService.deleteArticle(articleid);
			}
		}
		this.operation("批量删除网站内容", i);
		return "redirect:/article/getAllArticle.action";
	}

	// 更新数据
	@RequestMapping("updateArticle.action")
	public String updateArticle(Article article) {
		int i = this.articleService.updateArticle(article);
		this.operation("修改网站内容", i);
		return "redirect:/article/getAllArticle.action";
	}

	// AJAX更新数据
	@RequestMapping("xupdateArticle.action")
	@ResponseBody //将java对象转为json格式的数据
	public String xupdateArticle(Article article) {
		int i = this.articleService.updateArticle(article);
		JSONObject json = new JSONObject();
		json.put("result", i);
		//System.out.println(json.toString());
		this.operation("Ajax修改网站内容", i);
		return json.toString();
	}

	// 显示全部数据
	@RequestMapping("getAllArticle.action")
	public String getAllArticle(String number) {
		List<Article> articleList = this.articleService.getAllArticle();
		PageHelper.getUserPage(articleList, "article", "getAllArticle", 10, number, this.getRequest());
		this.operation("查看网站内容数据", 1);
		return "admin/listarticle";
	}

	// AJAX显示全部数据
	@RequestMapping(value = "getDataList.action", produces = "application/json; charset=utf-8")
	@ResponseBody //将java对象转为json格式的数据
	public Map<String, Object> getDataList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
		// 定义一个Map对象 用来返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Article> pager = com.github.pagehelper.PageHelper.startPage(page, limit);// 定义当前页和分页条数
		List<Article> list = this.articleService.getAllArticle();
		// 返回的map中定义Layui的数据格式
		map.put("count", pager.getTotal());
		map.put("data", list);
		map.put("code", 0);
		map.put("msg", "");
		this.operation("Ajax查看网站内容数据", 1);
		return map;
	}

	// 按条件查询数据 (模糊查询)
	@RequestMapping("queryArticleByCond.action")
	public String queryArticleByCond(String cond, String name, String number) {
		Article article = new Article();
		if(cond != null){
			if ("title".equals(cond)) {
				article.setTitle(name);
			}
			if ("bannerid".equals(cond)) {
				article.setBannerid(name);
			}
			if ("image".equals(cond)) {
				article.setImage(name);
			}
			if ("istop".equals(cond)) {
				article.setIstop(name);
			}
			if ("isflv".equals(cond)) {
				article.setIsflv(name);
			}
			if ("contents".equals(cond)) {
				article.setContents(name);
			}
			if ("addtime".equals(cond)) {
				article.setAddtime(name);
			}
			if ("hits".equals(cond)) {
				article.setHits(name);
			}
		}

		List<String> nameList = new ArrayList<String>();
		List<String> valueList = new ArrayList<String>();
		nameList.add(cond);
		valueList.add(name);
		PageHelper.getPage(this.articleService.getArticleByLike(article), "article", nameList, valueList, 10, number, this.getRequest(), "query");
		name = null;
		cond = null;
		return "admin/queryarticle";
	}

	// 按主键查询数据
	@RequestMapping("getArticleById.action")
	public String getArticleById(String id) {
		Article article = this.articleService.getArticleById(id);
		this.getRequest().setAttribute("article", article);
		List<Banner> bannerList = this.bannerService.getAllBanner();
		this.getRequest().setAttribute("bannerList", bannerList);
		return "admin/editarticle";
	}

	// 按主键查询数据
	@RequestMapping("queryArticleById.action")
	public String queryArticleById(String id) {
		Article article = this.articleService.getArticleById(id);
		this.getRequest().setAttribute("article", article);
		this.operation("查看网站内容数据", 1);
		return "admin/article";
	}

	// 工具方法 无调用可删
	public String export(List<Article> articleList) {
		Excel excel = new Excel();
		String banner = "网站内容表";
		String paths = this.getSession().getServletContext().getRealPath("/");
		String s1 = "标题,栏目,是否置顶,是否轮播,发布日期,点击数";
		List<String[]> strList = new ArrayList<String[]>();
		for (Article article : articleList) {
			String strTemp = article.getTitle() + "," + article.getBannername() + "," + article.getIstop() + "," + article.getIsflv() + "," + article.getAddtime() + "," + article.getHits();
			String[] str = strTemp.split(",");
			strList.add(str);
		}
		String[] strTitle = s1.split(",");
		String url = excel.getExcel(strList, strTitle, banner, paths);
		return url;
	}

	// TODO Auto-generated method stub

}
