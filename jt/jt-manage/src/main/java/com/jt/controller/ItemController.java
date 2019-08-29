package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;
@RequestMapping("/item")
@RestController
@Transactional
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	/*
	 * 根据分页查询商品信息
	 */
	@RequestMapping("/query")
	public EasyUITable findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	/*
	 * 商品新增
	 */
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
			itemService.saveItem(item,itemDesc);	
			return SysResult.success();
	}
	/*
	 * 商品修改
	 */
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		itemService.updateItem(item ,itemDesc);
		return SysResult.success();
		
	}
	/*
	 * 商品下架
	 * SpringMVC自动把字符串转为数组接收
	 */
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
	//	String[] strIds = ids.split(",");
		int status=2;//表示下架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		//	String[] strIds = ids.split(",");
		int status=1;//表示上架
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	@RequestMapping("/delete")
	public SysResult delete(Long[] ids) {
		itemService.deleteItems(ids);
		return SysResult.success();
	}
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		ItemDesc itemDesc=itemService.findItemDescById(itemId);
		
		return SysResult.success(itemDesc);
		
	}

}
