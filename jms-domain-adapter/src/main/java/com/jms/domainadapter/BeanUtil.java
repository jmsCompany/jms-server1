package com.jms.domainadapter;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class BeanUtil {
	private static final Logger logger = LogManager
			.getLogger(BeanUtil.class.getCanonicalName());
	public static Object shallowCopy(Object sourceObj,Class targetClass,Object target) throws Exception
	{
		String propertyName = null;
		if(target==null)
		 target =targetClass.newInstance();
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
							if(val!=null)
							{
							//	logger.debug("set: "+propertyName +", val: " + val);
								ClassUtil.invokeSetterMethod(target, "set"+propertyName,getterMethod.getReturnType(), val);
							}
							
						}
					}
					
				}
			}
				
		}
		return target;
	
	}

	
	

}
