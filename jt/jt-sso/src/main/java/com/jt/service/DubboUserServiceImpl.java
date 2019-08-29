package com.jt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class DubboUserServiceImpl implements DubboUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		//密码加密，登录时记得加密
		String md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass).setCreated(new Date()).setUpdated(user.getCreated());
		userMapper.insert(user); 

	}



	@Override
	public String doLogin(User user) {
		// TODO Auto-generated method stub
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		String md5Pas=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pas);
		User userDB = userMapper.selectOne(queryWrapper);
		String key=null;
		if (userDB!=null) {
			key=DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
			//脱敏处理
			userDB.setPassword("123456");
			//userDB化为JSON
			String userJSON=ObjectMapperUtil.toJSON(userDB);
			jedisCluster.setex(key, 7*24*3600, userJSON);
			
		}
		return key;
	}



}
