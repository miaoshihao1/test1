package com.jt.aop;

import javax.management.RuntimeErrorException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.anno.Cache_Find;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;



@Component
@Aspect//切面=切入点+通知
public class CacheAspect {
	//环绕通知：返回值必须为Object表示,表示执行完返回用户执行对象a
	//参数类型必须为ProceedingJoinPoint
	@Autowired(required=false)
	private JedisCluster jedis;
	@Around("@annotation(cacheFind)")
	public Object around(ProceedingJoinPoint joinPoint,Cache_Find cacheFind) {
		String key=getkey(joinPoint,cacheFind);
		String resultJSON=jedis.get(key);
		Object resultData=null;
		if (StringUtils.isEmpty(resultJSON)) {
			try {
				resultData=joinPoint.proceed();
				String value=ObjectMapperUtil.toJSON(resultData);
				if (cacheFind.seconds()>0) {
					jedis.setex(key, cacheFind.seconds(), value);
				}else {
					jedis.set(key, value);
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else {
			Class returnType=getType(joinPoint);
			resultData=ObjectMapperUtil.toObject(resultJSON,returnType) ;
		}
		return resultData;
		
	}
	
	private Class getType(ProceedingJoinPoint joinPoint) {
		// TODO Auto-generated method stub
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getReturnType();
	}

	//key生成策略：如果用户有KEY，获取。没有，生成
	private String getkey(ProceedingJoinPoint joinPoint, Cache_Find cacheFind) {
		// TODO Auto-generated method stub
		String key=cacheFind.key();
		if (StringUtils.isEmpty(key)) {
			String methodName = joinPoint.getSignature().getName();
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String arg1=String.valueOf(joinPoint.getArgs()[0]);
			return className+"."+methodName+"::"+arg1;
		}else {
			return key;
		}
	}

}
