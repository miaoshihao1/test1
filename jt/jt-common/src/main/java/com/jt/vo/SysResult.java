package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer status;//200表示正确，201表示错误
	private String msg;//提示信息
	private Object data;//返回业务数据
	//开发工具API
	public static SysResult success() {
		return new SysResult(200,"服务器执行成功",null);
	}
	public static SysResult success(Object data) {
		return new SysResult(200,"服务器执行成功",data);
	}
	public static SysResult success(String msg,Object data) {
		return new SysResult(200,msg,data);
	}
	public static SysResult fail() {
		return new SysResult(201,"服务器执行失败",null);
	}

}
