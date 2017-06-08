package org.smartframework.platform.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {
	public static ApplicationContext applicationContext;

	public static Object getBean(String name) throws BeansException {
		if (name == null) {
			return null;
		}
		return applicationContext.getBean(name);
	}

	public static Object getBean(Class<?> cls) throws BeansException {
		if (cls == null) {
			return null;
		}
		return applicationContext.getBean(cls);
	}

	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		applicationContext = contex;
	}
}
