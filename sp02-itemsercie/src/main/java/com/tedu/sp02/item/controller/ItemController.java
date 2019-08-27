package com.tedu.sp02.item.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tedu.sp01.pojo.Item;
import com.tedu.sp01.service.ItemService;
import com.tedu.web.util.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@Value("${server.port}")
	private int port;
	//限定只接get方法
	@GetMapping("/{orderId}")
	public JsonResult<List<Item>> getItems(@PathVariable String orderId) throws Exception {
		double random = Math.random();
		log.info(random+"server.port="+port+", orderId="+orderId);
        ///--设置随机延迟
		long t = new Random().nextInt(5000);
		if(random<0.6) { 
			log.info(random+"item-service-"+port+" - 暂停 "+t);
			Thread.sleep(t);
		}
		///~~		
		List<Item> items = itemService.getItems(orderId);
		return JsonResult.ok(items).msg("port="+port);
	}
	
	
	//限定只接post方法
	@PostMapping("/decreaseNumber")
	public JsonResult decreaseNumber(@RequestBody List<Item> items) {
		itemService.decreaseNumbers(items);
		return JsonResult.ok();
	}
}