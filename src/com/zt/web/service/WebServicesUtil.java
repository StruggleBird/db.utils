package com.zt.web.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map.Entry;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;



public class WebServicesUtil {
	
	
	@SuppressWarnings("unchecked")
	public static int service (String serviceUrl,Class serviceClass,String serviceMethodName,List<Entry<Class, Object>>  serviceMethodArgs) {
		
		Service serviceModel = new ObjectServiceFactory().create(serviceClass);
		
		XFire xFire=XFireFactory.newInstance().getXFire();
		
		XFireProxyFactory factory = new XFireProxyFactory(xFire);
		
		Object tClient=null;
		
		try {
			tClient= factory.create(serviceModel,serviceUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int serviceResult=0;
		
		try {
			
			Class[] args= new Class[serviceMethodArgs.size()];
			Object[] values =new Object[serviceMethodArgs.size()];
			
			for (int i = 0; i < serviceMethodArgs.size(); i++) {
				Entry<Class, Object> entry=serviceMethodArgs.get(i);
				args[i]=entry.getKey();
				values[i]=entry.getValue();
			}
			
			Method serviceMethod = serviceClass.getDeclaredMethod(serviceMethodName,args);
			serviceResult=(Integer)serviceMethod.invoke(tClient,values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return serviceResult;
	}
	
	
}
