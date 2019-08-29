package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;

@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService{
	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		// TODO Auto-generated method stub
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Override
	public void updateNum(Cart cart) {
		// TODO Auto-generated method stub
		Cart cartTemp=new Cart();
		cartTemp.setNum(cart.getNum()).setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
		.eq("item_id", cart.getItemId());
		cartMapper.update(cartTemp, updateWrapper);
	}

	@Override
	public void deleteCart(Cart cart) {
		// TODO Auto-generated method stub
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);
	}
	@Override
	@Transactional
	public void insertCart(Cart cart) {
		// TODO Auto-generated method stub
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		Cart cartDB=cartMapper.selectOne(queryWrapper);
		if (cartDB==null) {
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			cartMapper.insert(cart);	
		}else {
			int num=cart.getNum()+cartDB.getNum();
			Cart cartTemp=new Cart();
			cartTemp.setNum(num).setUpdated(new Date());
			UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("id", cartDB.getId());
			cartMapper.update(cartTemp, updateWrapper);
		}
	}
}
