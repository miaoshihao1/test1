package com.jt.thro;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestControllerAdvice//定义全局异常处理机制,controller层
public class SysResultController {
	//发生什么异常都使用词处理方式
	@ExceptionHandler(RuntimeException.class)
	public SysResult sysResultException(Exception exception) {
		exception.printStackTrace();
		log.error("服务器异常"+exception.getMessage());
		return SysResult.fail();
	}

}
