package org.smartframework.platform.dto.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.PropertyException;

import org.smartframework.utils.helper.DifferentException;
import org.smartframework.utils.helper.FormatException;
import org.smartframework.utils.helper.StringHelper;

public final class PropertyHelper {
	public static void setSimpleProperty(Object obj, String name, Object value) throws SecurityException, NoSuchMethodException, 
	    IllegalArgumentException, IllegalAccessException, InvocationTargetException, FormatException {
		if (obj == null) {
			return;
		}
		Class<?> objClass = obj.getClass();
		Method method = findSetMethod(objClass, name);
		if (method == null) {
			return;
		}
		Class<?> clazz = method.getParameterTypes()[0];
		if ((value == null) || (clazz.isInstance(value))) {
			method.invoke(obj, new Object[] { value });
		} else if ((value instanceof String)) {
			method.invoke(obj, new Object[] { StringHelper.str2obj(clazz, (String) value) });
		}
	}
  
	public static void setProperty(Object obj, String name, Object value) throws SecurityException, NoSuchMethodException, 
	    IllegalArgumentException, IllegalAccessException, InvocationTargetException, FormatException {
		int index = name.lastIndexOf('.');
		if (index == -1) {
			setSimpleProperty(obj, name, value);
			return;
		}
		Class<?> clazz = getSimplePropertyClass(obj, name.substring(0, index));
		if ((clazz != null) && (clazz.isEnum())) {
			if (value == null) {
				setSimpleProperty(obj, name.substring(0, index), null);
			} else {
				setSimpleProperty(obj, name.substring(0, index), clazz.getEnumConstants()[((java.lang.Integer) value).intValue()]);
			}
		} else {
			setSimpleProperty(getProperty(obj, name.substring(0, index)), name.substring(index + 1), value);
		}
	}
  
	public static String[] getPropertys(Object obj) {
		if (obj == null) {
			return new String[0];
		}
		Class<?> clazz = obj.getClass();
		Set<String> set = new HashSet<String>();
		Set<String> get = new HashSet<String>();
		findPropertysName(clazz, set, "set", 1);
		findPropertysName(clazz, get, "get", 0);
		findPropertysName(clazz, get, "has", 0);
		findPropertysName(clazz, get, "is", 0);
		set.retainAll(get);
		return (String[]) set.toArray(new String[set.size()]);
	}
  
	private static void findPropertysName(Class<?> clazz, Set<String> set, String prefix, int parameterLength) {
		for (Method m : clazz.getMethods()) {
			if ((m.getParameterTypes().length == parameterLength) && (m.getName().indexOf(prefix) == 0)) {
				set.add(m.getName().substring(prefix.length(), prefix.length() + 1).toLowerCase()
						+ m.getName().substring(prefix.length() + 1));
			}
		}
	}
  
	public static Class<?> getSimplePropertyClass(Object obj, String propertyName) {
		return getClassSimplePropertyClass(obj.getClass(), propertyName);
	}

	public static Class<?> getClassSimplePropertyClass(Class<?> objectClass, String propertyName) {
		Method method = findGetMethod(objectClass, propertyName);
		if (method == null) {
			return null;
		}
		return method.getReturnType();
	}

	public static Class<?> getClassPropertyClass(Class<?> objectClass, String propertyName) {
		String[] names = propertyName.split("\\.");
		Class<?> clazz = objectClass;
		for (int i = 0; i < names.length; i++) {
			clazz = getClassSimplePropertyClass(clazz, names[i]);
		}
		return clazz;
	}
  
	public static Class<?> getPropertyClass(Object obj, String propertyName) {
		return getClassPropertyClass(obj.getClass(), propertyName);
	}

	private static String nameForPrefix(String name, String prefix) {
		StringBuilder sBuilder = new StringBuilder(prefix);
		sBuilder.append(name.substring(0, 1).toUpperCase());
		sBuilder.append(name.substring(1));
		return sBuilder.toString();
	}

	public static Object getProperty(Object obj, String name) throws SecurityException, NoSuchMethodException, 
	    IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String[] names = name.split("\\.");
		Object ret = obj;
		for (int i = 0; i < names.length; i++) {
			ret = getSimpleProperty(ret, names[i]);
		}
		return ret;
	}

	public static Object getSimpleProperty(Object obj, String name) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (obj == null) {
			return null;
		}
		Class<?> clazz = obj.getClass();
		Method method = findGetMethod(clazz, name);
		if (method == null) {
			throw new NoSuchMethodException(name);
		}
		return method.invoke(obj, new Object[0]);
	}

	public static boolean hasSetMethod(Class<?> clazz, String propertyName) {
		return hasMethod(clazz, 1, new String[] { nameForPrefix(propertyName, "set") });
	}

	public static Method findSetMethod(Class<?> clazz, String propertyName) {
		return findMethod(clazz, 1, new String[] { nameForPrefix(propertyName, "set") });
	}

	private static boolean hasMethod(Class<?> clazz, int parameterLength, String... methodNames) {
		return findMethod(clazz, parameterLength, methodNames) != null;
	}

	private static Method findMethod(Class<?> clazz, int parameterLength, String... methodNames) {
		Method ret = null;
		for (Method m : clazz.getMethods()) {
			for (String methodName : methodNames) {
				if ((m.getName().equals(methodName)) && (m.getParameterTypes().length == parameterLength)) {
					if (m.getReturnType().equals(Object.class)) {
						ret = m;
					} else {
						return m;
					}
				}
			}
		}
		return ret;
	}

	public static boolean hasGetMethod(Class<?> clazz, String propertyName) {
		return hasMethod(clazz, 0,
				new String[] { nameForPrefix(propertyName, "get"),
						nameForPrefix(propertyName, "is"),
						nameForPrefix(propertyName, "has") });
	}

	public static Method findGetMethod(Class<?> clazz, String propertyName) {
		return findMethod(clazz, 0,
				new String[] { nameForPrefix(propertyName, "get"),
						nameForPrefix(propertyName, "is"),
						nameForPrefix(propertyName, "has") });
	}

	public static void copy(Object source, Object target) throws DifferentException, PropertyException {
		if (!source.getClass().equals(target.getClass())) {
			throw new DifferentException(source, target);
		}
		for (String name : getPropertys(source)) {
			try {
				setProperty(target, name, getProperty(source, name));
			} catch (SecurityException e) {
				throw new PropertyException(e);
			} catch (IllegalArgumentException e) {
				throw new PropertyException(e);
			} catch (NoSuchMethodException e) {
				throw new PropertyException(e);
			} catch (IllegalAccessException e) {
				throw new PropertyException(e);
			} catch (InvocationTargetException e) {
				throw new PropertyException(e);
			} catch (FormatException e) {
				throw new PropertyException(e);
			}
		}
	}
}
