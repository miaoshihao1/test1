package com.jt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;
@Transactional
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		if (rows==0) {
			throw new RuntimeException("");
		}
		EasyUITable easyUITable = new EasyUITable();
		Integer i=itemMapper.selectCount(null);
		Integer pagenum=i/rows+1;
		System.out.println(pagenum);
		if (pagenum<page) {
			throw new RuntimeException("");
		}
	    int start=(page-1)*rows;
		List<Item> list=itemMapper.findItemByPage(start,rows);
		easyUITable.setTotal(i).setRows(list);
		return easyUITable;
	}
	@Transactional
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		// TODO Auto-generated method stub
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		// TODO Auto-generated method stub
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId()).setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}
//批量更新
	@Override
	public void updateStatus(Long[] ids, int status) {
		// TODO Auto-generated method stub
		Item item=new Item();
		item.setStatus(status).setUpdated(new Date());
		UpdateWrapper<Item> updateWrapper=new UpdateWrapper<>();
		List<Long> idList=Arrays.asList(ids);
		updateWrapper.in("id", idList);
		itemMapper.update(item, updateWrapper);
	}
//批量删除
	@Transactional
	@Override
	public void deleteItems(Long[] ids) {
		// TODO Auto-generated method stub
		List<Long> idList=Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		// TODO Auto-generated method stub
		
		return itemDescMapper.selectById(itemId);
	}
	
	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectById(itemId);
	}

	
	
	
	
	
}
