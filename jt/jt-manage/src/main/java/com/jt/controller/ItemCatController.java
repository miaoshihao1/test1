package com.jt.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.anno.Cache_Find;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
@RequestMapping("/item/cat")
@RestController
@Transactional
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	@RequestMapping("/queryItemName")
	public String queryItemName(Long itemCatId) {
		return itemCatService.queryItemName(itemCatId);
	}
	@Cache_Find
	@RequestMapping("/list")
	public List<EasyUITree> list(@RequestParam(defaultValue ="0",name="id", required = false)
	Long parentId) {// required = false不是必须传递的数据，name/value表示前端发来的数据名称
		
		return itemCatService.findEasyUIlist(parentId);
		//return itemCatService.findEasyUITreeCache(parentId);
	}
	
}
