package com.jt.mapper;

import java.util.List;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;
/*
 * #有预编译效果，多用于传值,传字符串时会自带单引号
 * $多用于传字段，小心是SQL注入
 */
public interface ItemCatMapper extends BaseMapper<ItemCat>{

	
}
