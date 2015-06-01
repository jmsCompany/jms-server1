package com.jms.domainadapter;

import java.lang.reflect.Method;
import java.util.Date;

public class BeanUtil {
	
	public static Object shallowCopy(Object sourceObj,Class targetClass) throws Exception
	{
		String propertyName = null;
		Object target =targetClass.newInstance();
		if(sourceObj==null)
			return target;
		for(Method getterMethod: sourceObj.getClass().getMethods())
		{
			if(getterMethod.getReturnType().isPrimitive()
			 ||getterMethod.getReturnType().getName().startsWith("java.lang.")
			 ||getterMethod.getReturnType().equals(Date.class)
			)
			{
				String methodName = getterMethod.getName();
				if(methodName.startsWith("get")&&!methodName.equals("getClass"))
				{
					propertyName = methodName.substring(3);
					for(Method setterMethod: targetClass.getMethods())
					{
						if(setterMethod.getName().equals("set" + propertyName))
						{
							Object val = ClassUtil.invokeGetterMethod(sourceObj, "get" + propertyName);
							ClassUtil.invokeSetterMethod(target, "set"+propertyName,getterMethod.getReturnType(), val);
						}
					}
					
				}
			}
				
		}
		return target;
	
	}

	
	

}
