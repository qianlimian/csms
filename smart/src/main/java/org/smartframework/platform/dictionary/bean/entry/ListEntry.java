package org.smartframework.platform.dictionary.bean.entry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.smartframework.platform.context.SpringContext;
import org.smartframework.platform.dictionary.exception.BeanException;
import org.smartframework.platform.dictionary.exception.MethodException;

public class ListEntry implements Comparable<ListEntry> {
	private String className;
	private String methodName;
	private String keyName;
	private String valueName;
	private String id = null;
	private String emf;

	public String getId() {
		if (this.id == null) {
			this.id = (this.className + "." + this.methodName);
		}
		return this.id;
	}

	public ListEntry() {
	}

	public ListEntry(String cn, String mn, String kn, String vn, String emf) {
		this.className = cn;
		this.methodName = mn;
		this.keyName = kn;
		this.valueName = vn;
		this.emf = emf;
	}

	public List<?> getList() throws BeanException, MethodException {
		try {
			if (getEmf() != null) {
				EntityManager em = ((EntityManagerFactory) SpringContext.getBean(getEmf())).createEntityManager();
			}
			Class<?> c = Class.forName(this.className);
			Method method = c.getMethod(this.methodName, new Class[0]);
			return (List<?>) method.invoke(null, new Object[0]);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new BeanException(this.className);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new BeanException(this.className);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new MethodException(this.className, this.methodName);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new MethodException(this.className, this.methodName);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new MethodException(this.className, this.methodName);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new MethodException(this.className, this.methodName);
		}
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + (this.className == null ? 0 : this.className.hashCode());
		result = prime * result + (this.keyName == null ? 0 : this.keyName.hashCode());
		result = prime * result + (this.methodName == null ? 0 : this.methodName.hashCode());
		result = prime * result + (this.valueName == null ? 0 : this.valueName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ListEntry other = (ListEntry) obj;
		if (this.className == null) {
			if (other.className != null) {
				return false;
			}
		} else if (!this.className.equals(other.className)) {
			return false;
		}
		if (this.keyName == null) {
			if (other.keyName != null) {
				return false;
			}
		} else if (!this.keyName.equals(other.keyName)) {
			return false;
		}
		if (this.methodName == null) {
			if (other.methodName != null) {
				return false;
			}
		} else if (!this.methodName.equals(other.methodName)) {
			return false;
		}
		if (this.valueName == null) {
			if (other.valueName != null) {
				return false;
			}
		} else if (!this.valueName.equals(other.valueName)) {
			return false;
		}
		return true;
	}

	public int compareTo(ListEntry o) {
		return (this.className + this.methodName + this.keyName + this.valueName)
				.compareTo(o.className + o.methodName + o.keyName + o.valueName);
	}
	
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValueName() {
		return this.valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getEmf() {
		return this.emf;
	}

	public void setEmf(String emf) {
		this.emf = emf;
	}
}

