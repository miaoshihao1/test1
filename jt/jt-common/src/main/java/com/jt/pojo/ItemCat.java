package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;

@JsonIgnoreProperties(ignoreUnknown=true) //表示JSON转化时忽略未知属性
@TableName("tb_item_cat")
@Data
@Accessors(chain=true)
public class ItemCat extends BasePojo{
	private Long id;                  
	private Long parentId;            
	private String name;                
	private Integer status;             
	private Integer sortOrder;          
	private Boolean isParent;            

}
