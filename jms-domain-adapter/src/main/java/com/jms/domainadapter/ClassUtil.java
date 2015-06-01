package com.jms.domainadapter;

import java.lang.reflect.Method;

public class ClassUtil {

	public static Object invokeGetterMethod(Object obj, String methodName) throws Exception {
		Method m = obj.getClass().getMethod(methodName);
		return m.invoke(obj);
	}

	public static Object invokeSetterMethod(Object obj, String methodName,
			Class classType, Object val) throws Exception {
		Method m = obj.getClass().getMethod(methodName, classType);
		m.invoke(obj, val);
		return obj;
	}
}
