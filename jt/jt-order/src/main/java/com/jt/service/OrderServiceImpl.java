package com.jt.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Override
	public String saveOrder(Order order) {
		// TODO Auto-generated method stub
		String orderId=""+order.getUserId()+System.currentTimeMillis();
		Date date=new Date();
		order.setStatus(1).setOrderId(orderId).setCreated(date).setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库");
		OrderShipping orderShipping=order.getOrderShipping();
		orderShipping.setOrderId(orderId).setCreated(date).setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流信息入库");
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId).setCreated(date).setUpdated(date);
		}
		System.out.println();
		return orderId;
	}
	@Override
	public Order findOrderById(String id) {
		// TODO Auto-generated method stub
		/*
		 * Order order=orderMapper.selectById(id); OrderShipping
		 * shipping=orderShippingMapper.selectById(id); QueryWrapper<OrderItem>
		 * queryWrapper=new QueryWrapper<>(); queryWrapper.eq("order_id", id);
		 * List<OrderItem> list = orderItemMapper.selectList(queryWrapper);
		 * order.setOrderShipping(shipping).setOrderItems(list);
		 */
		Order order=orderMapper.findOrderById(id);
		return order;
	}
	
	
}
