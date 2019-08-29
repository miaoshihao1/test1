package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
/*
 * #有预编译效果，多用于传值,传字符串时会自带单引号
 * $多用于传字段，小心是SQL注入
 */
public interface ItemMapper extends BaseMapper<Item>{
@Select("SELECT * FROM tb_item ORDER BY updated DESC LIMIT #{start},#{rows};")
	List<Item> findItemByPage(@Param("start") Integer start,@Param("rows") Integer rows);

	
}
