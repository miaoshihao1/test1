package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)//运行期生效
@Target(ElementType.METHOD)
public @interface Cache_Find {
	String key() default "";//用户可以不写
	int seconds() default 0; //0表示用户不需要超时时间-++，不等于0则说明用户自己定义了超时时间
}
