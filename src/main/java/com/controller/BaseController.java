package com.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.entity.Loginf;
import com.entity.Operlog;
import com.service.LoginfService;
import com.service.OperlogService;
import com.util.VeDate;

/**
 * Controller 基类
 * 其他Controller继承此类的同时也继承了此类里面的各个方法 
 * 省掉了重新定义 实例化的麻烦
 */
@Controller
public class BaseController {

	private final static String LOGSUCCESS = "登录成功";
	private final static String LOGFAIL = "登录失败";
	private final static String OPERSUCCESS = "操作成功";
	private final static String OPERFAIL = "操作失败";

	@Autowired
	private OperlogService operlogService;
	@Autowired
	private LoginfService loginfService;

	/* 日志 */
	protected final Log log = LogFactory.getLog(getClass());

	/* 获取基本环境 */
	public Map<String, String[]> getParameters() {// 封装为Map的requestParameters
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest().getParameterMap();
	}

	/* 获取Request对象方法 */ 
	public HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	/* 获取Session对象方法  */ 
	public HttpSession getSession() {
		HttpSession session = null;
		try {
			session = this.getRequest().getSession();
		} catch (Exception e) {
		}
		return session;
	}

	/* 保存用户操作记录  */
	public void operation(String opername, int num) {
		String path = this.getRequest().getContextPath();
		String basePath = this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" + this.getRequest().getServerPort() + path + "/";
		Operlog operlog = new Operlog();
		operlog.setIpaddr(this.getRequest().getRemoteAddr());
		operlog.setMethods(this.getRequest().getMethod());
		operlog.setOpername(opername);
		if (num == 0) {
			operlog.setOperstatus(BaseController.OPERFAIL);
		} else {
			operlog.setOperstatus(BaseController.OPERSUCCESS);
		}
		operlog.setOpers("" + this.getSession().getAttribute("realname"));
		operlog.setOperurl("/" + this.getRequest().getRequestURL().toString().replace(basePath, ""));
		operlog.setOpertime(VeDate.getStringDate());
		this.operlogService.insertOperlog(operlog);
	}

	/* 保存用户登录记录  */
	public void loginfo(String username, String role, int num) {
		String userbrowser = this.getRequest().getParameter("userbrowser"); // 得到用户的浏览器名
		String useros = System.getProperty("os.name"); // 得到用户的操作系统名
		Loginf loginf = new Loginf();
		loginf.setBrower(userbrowser);
		loginf.setIpaddr(this.getRequest().getRemoteAddr());
		loginf.setLogname(username);
		loginf.setOs(useros);
		loginf.setRole(role);
		loginf.setLogtime(VeDate.getStringDate());
		if (num == 0) {
			loginf.setLogstatus(BaseController.LOGFAIL);
		} else {
			loginf.setLogstatus(BaseController.LOGSUCCESS);
		}
		this.loginfService.insertLoginf(loginf);
	}
}





















