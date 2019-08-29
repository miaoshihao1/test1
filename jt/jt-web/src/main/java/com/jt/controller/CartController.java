package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;
@RequestMapping("/cart")
@Controller
public class CartController {
	@Reference(timeout = 3000,check = true)
	private DubboCartService cartService;
	@RequestMapping("/show")
	public String show(Model model,HttpServletRequest request) {
		Long userId=UserThreadLocal.get().getId();
		List<Cart> cartList=cartService.findCartListByUserId(userId);
		model.addAttribute("cartList",cartList);
		System.out.println(cartList);
		return "cart";
	}
	/*
	 *  如果{参数}的名称和对象中的属性一直，则可以使用对象直接取值
	 */
	@ResponseBody
	@RequestMapping("/update/num/{itemId}/{num}")
	public SysResult updateNum(Cart cart) {
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.updateNum(cart);
		return SysResult.success();

	}
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	/*
	 * 新增
	 */
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.insertCart(cart);
		return "redirect:/cart/show.html";

	}
}
