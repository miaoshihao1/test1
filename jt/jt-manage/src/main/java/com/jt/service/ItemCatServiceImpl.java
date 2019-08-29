package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITable;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
@Transactional
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;
	//@Autowired
	private ShardedJedis jedis;
	@Override
	public String queryItemName(Long cid) {
		// TODO Auto-generated method stub

		ItemCat itemCat = itemCatMapper.selectById(cid);

		return itemCat.getName();
	}
	public List<ItemCat> findItemCatList(Long parentId){
		// TODO Auto-generated method stub
		QueryWrapper<ItemCat> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		List<ItemCat> selectList = itemCatMapper.selectList(queryWrapper);
		return selectList;

	}
	@Override
	public List<EasyUITree> findEasyUIlist(Long parentId) {
		// TODO Auto-generated method stub
		List<ItemCat> itemList=findItemCatList(parentId);
		List<EasyUITree> treeList=new ArrayList<>();
		for (ItemCat itemCat : itemList) {
			EasyUITree easyUITree=new EasyUITree();
			String state=
					itemCat.getIsParent()?"closed":"open";
			easyUITree.setId(itemCat.getId()).setText(itemCat.getName()).setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}
	/*
	 * 通过生成的key查询redis缓存服务器
	 * null表示没有数据，需要查询数据库，之后将数据转到redis中，方便下次调用
	 * ！null，表示有数据，将数据
	 */
	@Override
	public List<EasyUITree> findEasyUITreeCache(Long parentId) {
		// TODO Auto-generated method stub
		List<EasyUITree> treeList=new ArrayList<>();
		String key="UTRN_CAT_"+parentId;
		String result = jedis.get(key);
		if (StringUtils.isEmpty(result)) {
			treeList=findEasyUIlist(parentId);
			String value=ObjectMapperUtil.toJSON(treeList);
			jedis.set(key, value);
			System.out.println("查询后台数据库");
		}else {
			treeList=ObjectMapperUtil.toObject(result, treeList.getClass());
		}
		return treeList;
	}











}
