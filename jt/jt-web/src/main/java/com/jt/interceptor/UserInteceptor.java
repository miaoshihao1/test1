package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.druid.util.StringUtils;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

@Component
public class UserInteceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// true表示放行，false表示拦截
		 Cookie[] cookies=request.getCookies();
		 String ticket=null;
		 if (cookies.length>0) {
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					ticket=cookie.getValue();
					break;
				}
			}
		}
		if (!StringUtils.isEmpty(ticket)) {
			String userJSON=jedisCluster.get(ticket);
			if (!StringUtils.isEmpty(userJSON)) {
				User user=ObjectMapperUtil.toObject(userJSON, User.class);
				UserThreadLocal.set(user);
				return true;
			}
		}
		response.sendRedirect("/user/login.html");
		return false;
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		UserThreadLocal.remove();
	}
}
