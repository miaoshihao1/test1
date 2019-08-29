package com.jt.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("tb_item_desc")
@Data
@Accessors(chain=true)
public class ItemDesc extends BasePojo{
	@TableId//(type=IdType.AUTO)不能主键自增，与Item的id值相同
	private Long itemId;				//商品id
	private String itemDesc;			//商品标题
	
	
}
