package com.jt.pojo;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.experimental.Accessors;
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("tb_cart")
@Data
public class Cart extends BasePojo{
	private Long id;                  
	private Long userId;            
	private Long itemId;             
	private String itemTitle;           
	private String itemImage;          
	private Long itemPrice;          
	private Integer num;                
}
