package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	@Reference(timeout=3000,check=true)
	private DubboUserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("/{moduleName}")
	public String login(@PathVariable String moduleName) {
		return moduleName;
	}
	@ResponseBody
	@RequestMapping("/doRegister")
	public SysResult insertUser(User user) {
		System.out.println(user);
		userService.insertUser(user);
		return SysResult.success();
	}
	@ResponseBody
	@RequestMapping("/doLogin")
	public SysResult doLogin(User user,HttpServletResponse response) {
		//获取服务器加密密钥
		String ticket=userService.doLogin(user);
		if (StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}
		Cookie cookie = new Cookie("JT_TICKET", ticket);
		//超时时间
		cookie.setMaxAge(7*24*3600);
		//设定使用权限，一般都是/，可以写/user表示/user下的才可以用
		cookie.setPath("/");
		//只有jt.com能使用cookie
		cookie.setDomain("jt.com");
		response.addCookie(cookie);
		return SysResult.success();
	}
	/*
	 * 1.删除redis
	 * 2.删除cookie
	 * 3.重定向
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String ticket=null; 
		if (cookies.length>0) {
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					ticket=cookie.getValue();
					break;
				}
			}
		}
		System.out.println(ticket);
		System.out.println(jedisCluster.getClusterNodes());
		if (!StringUtils.isEmpty(ticket)) {
			jedisCluster.del(ticket);
		}
		 
		//不能写null，浏览器可能报错
		Cookie cookie=new Cookie("JT_TICKET", "");
		//cookie没有删除操作，设置最大时间来删除
		cookie.setMaxAge(0);
		//设定使用权限，一般都是/，可以写/user表示/user下的才可以用
		cookie.setPath("/");
		//只有jt.com能使用cookie
		cookie.setDomain("jt.com");
		response.addCookie(cookie);
		return "redirect:/";

	}
}
