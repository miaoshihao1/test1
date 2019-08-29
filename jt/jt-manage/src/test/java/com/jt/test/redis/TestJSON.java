package com.jt.test.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

public class TestJSON {
	@Test
	public void toJSON() throws IOException{
		ItemDesc itemDesc1=new ItemDesc();
		itemDesc1.setItemId(1000L);
		itemDesc1.setItemDesc("商品描述信息");
		ObjectMapper mapper=new ObjectMapper();
		String json=mapper.writeValueAsString(itemDesc1);
		ItemDesc itemDesc2=new ItemDesc();
		itemDesc2.setItemId(1000L);
		itemDesc2.setItemDesc("商品描述信息");
		ItemDesc itemDesc3=new ItemDesc();
		itemDesc3.setItemId(1000L);
		itemDesc3.setItemDesc("商品描述信息");
		List<ItemDesc> list=new ArrayList<ItemDesc>();
		list.add(itemDesc1);
		list.add(itemDesc2);
		list.add(itemDesc3);
	
		System.out.println(json);
		//JSON转对象
		mapper.readValue(json, ItemDesc.class);
				
	}

}
