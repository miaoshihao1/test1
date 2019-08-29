package com.jt.service;

import java.util.List;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITable;
import com.jt.vo.EasyUITree;

public interface ItemCatService {

	String queryItemName(Long cid);

	List<EasyUITree> findEasyUIlist(Long parentId);
	public List<ItemCat> findItemCatList(Long parentId);
	public List<EasyUITree> findEasyUITreeCache(Long parentId);
		
	
}
