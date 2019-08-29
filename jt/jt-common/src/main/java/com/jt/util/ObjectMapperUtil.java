package com.jt.util;


import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	private static final ObjectMapper MAPPER=new ObjectMapper();
	public static String toJSON(Object obj) {
		String result=null;
		try {
			result = MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
	}
	public static <T> T toObject(String jsonString,Class<T> targetClass) {
		T obj=null;
		try {
			obj = MAPPER.readValue(jsonString, targetClass);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
		return obj;		
	}

}
