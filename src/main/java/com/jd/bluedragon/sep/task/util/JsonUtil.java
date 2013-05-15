package com.jd.bluedragon.sep.task.util;

import com.alibaba.fastjson.JSON;

/**
 * Json工具类
 */
public class JsonUtil {



	/**
	 * json格式串转换为对象
	 * @param str
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToBean(String str, Class<T> clazz){
        return JSON.parseObject(str,clazz);
	}
	
	/**
	 * 对象转换为json格式串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if(obj == null){
			return null;
		}
		return JSON.toJSONString(obj);
	}


}
