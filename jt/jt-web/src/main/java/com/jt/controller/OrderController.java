package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.OrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
@Controller
@RequestMapping("/order")
public class OrderController {
	@Reference(timeout = 3000,check = false)
	private OrderService orderService;
	@Reference(timeout = 3000,check = false)
	private DubboCartService cartService;
	@RequestMapping("/create")
	public String create(Model model) {
		Long userId=UserThreadLocal.get().getId();
		List<Cart> carts = cartService.findCartListByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
		
	}
	@ResponseBody
	@RequestMapping("/submit")
	public SysResult saveOrder(Order order) {
		Long userId=UserThreadLocal.get().getId();
		order.setUserId(userId);
		String orderId=orderService.saveOrder(order);
		return SysResult.success(orderId);
		
	}
	@RequestMapping("/success")
	public String findOrderById(String id,Model model) {
		Order order=orderService.findOrderById(id);
		model.addAttribute("order",order);
		return "success";
	}
}
