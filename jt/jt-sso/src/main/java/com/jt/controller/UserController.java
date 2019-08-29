package com.jt.controller;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;
@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		JSONPObject jsonpObject;
		try {

			boolean flag=userService.findCheckUser(param,type);
			jsonpObject=new JSONPObject(callback, SysResult.success("OK", flag));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jsonpObject=new JSONPObject(callback, SysResult.fail());	
		}
		return jsonpObject;

	}
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,
			String callback) {
		JSONPObject jsonpObject=null;
		String userJSON=jedisCluster.get(ticket);
		if (StringUtils.isEmpty(userJSON)) {
			jsonpObject=new JSONPObject(callback, SysResult.fail());
		}else {
			jsonpObject=new JSONPObject(callback, SysResult.success(userJSON));
		}
		return jsonpObject;

	}


}
