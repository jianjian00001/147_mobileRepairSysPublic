package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Article;
import com.entity.Banner;
import com.entity.Brand;
import com.entity.Complains;
import com.entity.Extendx;
import com.entity.Orders;
import com.entity.Phone;
import com.entity.Topic;
import com.entity.Users;
import com.service.ArticleService;
import com.service.BannerService;
import com.service.BrandService;
import com.service.ComplainsService;
import com.service.ExtendxService;
import com.service.OrdersService;
import com.service.PhoneService;
import com.service.TopicService;
import com.service.UsersService;
import com.util.PageHelper;
import com.util.VeDate;

//定义为控制器
@Controller
// 设置路径
@RequestMapping("/index")
public class IndexController extends BaseController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ExtendxService extendxService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private ComplainsService complainsService;

	// TODO Auto-generated method stub
	// 公共方法 提供公共查询数据
	private void front() {
		this.getRequest().setAttribute("title", "手机售后维修管理系统");
		List<Banner> bannerList = this.bannerService.getAllBanner();
		this.getRequest().setAttribute("bannerList", bannerList);
	}

	// 首页显示
	@RequestMapping("index.action")
	public String index() {
		this.front();
		List<Banner> bannerList = this.bannerService.getAllBanner();
		List<Banner> frontList = new ArrayList<Banner>();
		for (Banner banner : bannerList) {
			List<Article> articleList = this.articleService.getArticleByBanner(banner.getBannerid());
			banner.setArticleList(articleList);
			frontList.add(banner);
		}
		this.getRequest().setAttribute("frontList", frontList);
		List<Article> topList = this.articleService.getTopArticle();
		List<Article> favList = this.articleService.getFlvArticle();
		this.getRequest().setAttribute("topList", topList);
		this.getRequest().setAttribute("favList", favList);
		return "users/index";
	}

	// 新闻公告
	@RequestMapping("article.action")
	public String article(String id, String number) {
		this.front();
		Article article = new Article();
		article.setBannerid(id);
		List<Article> articleList = this.articleService.getArticleByCond(article);
		PageHelper.getIndexPage(articleList, "article", "article", id, 10, number, this.getRequest());
		Banner banner = this.bannerService.getBannerById(id);
		this.getRequest().setAttribute("banner", banner);
		return "users/article";
	}

	// 阅读公告
	@RequestMapping("read.action")
	public String read(String id) {
		this.front();
		Article article = this.articleService.getArticleById(id);
		article.setHits("" + (Integer.parseInt(article.getHits()) + 1));
		this.articleService.updateArticle(article);
		this.getRequest().setAttribute("article", article);
		return "users/read";
	}

	// 准备登录
	@RequestMapping("preLogin.action")
	public String prelogin() {
		this.front();
		return "users/login";
	}

	// 用户登录
	@RequestMapping("login.action")
	public String login() {
		this.front();
		String username = this.getRequest().getParameter("username");
		String password = this.getRequest().getParameter("password");
		Users u = new Users();
		u.setUsername(username);
		List<Users> usersList = this.usersService.getUsersByCond(u);
		if (usersList.size() == 0) {
			this.getSession().setAttribute("message", "用户名不存在");
			return "redirect:/index/preLogin.action";
		} else {
			Users users = usersList.get(0);
			if (password.equals(users.getPassword())) {
				this.getSession().setAttribute("userid", users.getUsersid());
				this.getSession().setAttribute("username", users.getUsername());
				this.getSession().setAttribute("users", users);
				return "redirect:/index/index.action";
			} else {
				this.getSession().setAttribute("message", "密码错误");
				return "redirect:/index/preLogin.action";
			}
		}
	}

	// 准备注册
	@RequestMapping("preReg.action")
	public String preReg() {
		this.front();
		return "users/register";
	}

	// 用户注册
	@RequestMapping("register.action")
	public String register(Users users) {
		this.front();
		Users u = new Users();
		u.setUsername(users.getUsername());
		List<Users> usersList = this.usersService.getUsersByCond(u);
		if (usersList.size() == 0) {
			users.setRegdate(VeDate.getStringDateShort());
			this.usersService.insertUsers(users);
		} else {
			this.getSession().setAttribute("message", "用户名已存在");
			return "redirect:/index/preReg.action";
		}

		return "redirect:/index/preLogin.action";
	}

	// 退出登录
	@RequestMapping("exit.action")
	public String exit() {
		this.front();
		this.getSession().removeAttribute("userid");
		this.getSession().removeAttribute("username");
		this.getSession().removeAttribute("users");
		return "redirect:/index/index.action";
	}

	// 准备修改密码
	@RequestMapping("prePwd.action")
	public String prePwd() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/editpwd";
	}

	// 修改密码
	@RequestMapping("editpwd.action")
	public String editpwd() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		String password = this.getRequest().getParameter("password");
		String repassword = this.getRequest().getParameter("repassword");
		Users users = this.usersService.getUsersById(userid);
		if (password.equals(users.getPassword())) {
			users.setPassword(repassword);
			this.usersService.updateUsers(users);
		} else {
			this.getSession().setAttribute("message", "旧密码错误");
			return "redirect:/index/prePwd.action";
		}
		this.getSession().setAttribute("message", "修改成功");
		return "redirect:/index/prePwd.action";
	}

	@RequestMapping("usercenter.action")
	public String usercenter() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/usercenter";
	}

	@RequestMapping("userinfo.action")
	public String userinfo() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		this.getSession().setAttribute("users", this.usersService.getUsersById(userid));
		return "users/userinfo";
	}

	@RequestMapping("personal.action")
	public String personal(Users users) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.usersService.updateUsers(users);
		this.getSession().setAttribute("message", "修改成功");
		return "redirect:/index/userinfo.action";
	}

	// 准备意见反馈
	@RequestMapping("preComplains.action")
	public String preComplains() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		return "users/addComplains";
	}

	// 发布意见反馈
	@RequestMapping("addComplains.action")
	public String addComplains(Complains complains) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		complains.setAddtime(VeDate.getStringDateShort());
		complains.setStatus("未回复");
		complains.setUsersid(userid);
		this.complainsService.insertComplains(complains);
		return "redirect:/index/myComplains.action";
	}

	// 我的意见反馈
	@RequestMapping("myComplains.action")
	public String myComplains(String number) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Complains complains = new Complains();
		complains.setUsersid(userid);
		List<Complains> complainsList = this.complainsService.getComplainsByCond(complains);
		PageHelper.getIndexPage(complainsList, "complains", "myComplains", null, 10, number, this.getRequest());
		return "users/myComplains";
	}

	@RequestMapping("preOrders.action")
	public String preOrders() {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.getRequest().setAttribute("ordercode", "PD" + VeDate.getStringDatex());
		List<Brand> brandList = this.brandService.getAllBrand();
		this.getRequest().setAttribute("brandList", brandList);
		return "users/addOrders";
	}

	// 发布意见反馈
	@RequestMapping("addOrders.action")
	public String addOrders(Orders orders) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		Phone phone = this.phoneService.getPhoneById(orders.getPhoneid());
		String userid = (String) this.getSession().getAttribute("userid");
		orders.setAddtime(VeDate.getStringDateShort());
		orders.setStatus("待处理");
		orders.setUsersid(userid);
		orders.setTotal(phone.getPrice());
		this.ordersService.insertOrders(orders);
		phone.setNum("" + (Integer.parseInt(phone.getNum()) + 1));
		this.phoneService.updatePhone(phone);
		return "redirect:/index/myOrders.action";
	}

	// 我的意见反馈
	@RequestMapping("myOrders.action")
	public String myOrders(String number) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String userid = (String) this.getSession().getAttribute("userid");
		Orders orders = new Orders();
		orders.setUsersid(userid);
		List<Orders> ordersList = this.ordersService.getOrdersByCond(orders);
		PageHelper.getIndexPage(ordersList, "orders", "myOrders", null, 10, number, this.getRequest());
		return "users/myOrders";
	}

	@RequestMapping("deleteOrders.action")
	public String deleteOrders(String id) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.ordersService.deleteOrders(id);
		return "redirect:/index/myOrders.action";
	}

	@RequestMapping("extendx.action")
	public String extendx(String number) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String id = this.getRequest().getParameter("id");
		Extendx extendx = new Extendx();
		extendx.setOrdersid(id);
		List<Extendx> extendxList = this.extendxService.getExtendxByCond(extendx);
		this.getRequest().setAttribute("extendxList", extendxList);
		return "users/extendx";
	}

	@RequestMapping("preTopic.action")
	public String preTopic(String id) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		this.getRequest().setAttribute("id", id);
		Orders orders = this.ordersService.getOrdersById(id);
		this.getRequest().setAttribute("orders", orders);
		return "users/addTopic";
	}

	// 发布食品评价
	@RequestMapping("addTopic.action")
	public String addTopic(Topic topic) {
		this.front();
		if (this.getSession().getAttribute("userid") == null) {
			return "redirect:/index/preLogin.action";
		}
		String ordersid = this.getRequest().getParameter("ordersid");
		Orders orders = this.ordersService.getOrdersById(ordersid);
		orders.setStatus("已评价");
		this.ordersService.updateOrders(orders);
		String userid = (String) this.getSession().getAttribute("userid");
		topic.setAddtime(VeDate.getStringDateShort());
		topic.setNum(this.getRequest().getParameter("num"));
		topic.setContents(this.getRequest().getParameter("contents"));
		topic.setOrdersid(ordersid);
		topic.setUsersid(userid);
		this.topicService.insertTopic(topic);
		return "redirect:/index/myOrders.action";
	}

	// TODO Auto-generated method stub
}
