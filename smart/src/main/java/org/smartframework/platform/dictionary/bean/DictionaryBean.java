package org.smartframework.platform.dictionary.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.smartframework.platform.dictionary.bean.entry.EnumEntry;
import org.smartframework.platform.dictionary.exception.BeanException;

public class DictionaryBean {
	private String key;
	private String value;

	public DictionaryBean(Object key, String value) {
		this.key = String.valueOf(key);
		this.value = value;
	}

	public static <T> List<DictionaryBean> createEntries(List<T> list, String key, String value) throws BeanException {
		List<DictionaryBean> ret = new ArrayList<DictionaryBean>();
		Class<?> c = null;
		try {
			for (T t : list) {
				if (c == null) {
					c = t.getClass();
				}
				Object ko = PropertyUtils.getProperty(t, key);
				Object vo = PropertyUtils.getProperty(t, value);
				ret.add(new DictionaryBean(ko == null ? "" : ko.toString(), vo == null ? "" : vo.toString()));
			}
		} catch (IllegalAccessException e) {
			throw new BeanException(c, new String[] { key, value });
		} catch (InvocationTargetException e) {
			throw new BeanException(c, new String[] { key, value });
		} catch (NoSuchMethodException e) {
			throw new BeanException(c, new String[] { key, value });
		}
		return ret;
	}

	public static <T> List<DictionaryBean> createEntries(Class<T> entry) throws Exception {
		List<DictionaryBean> ret = new ArrayList<DictionaryBean>();
		if (!entry.isEnum()) {
			throw new BeanException(entry, new String[] { "key", "value" });
		}
		for (Object o : entry.getEnumConstants()) {
			if ((o instanceof EnumEntry)) {
				EnumEntry d = (EnumEntry) o;
				ret.add(new DictionaryBean(d.key(), d.value()));
			} else {
				Enum<?> e = (Enum<?>) o;
				ret.add(new DictionaryBean(e.name(), e.name()));
			}
		}
		return ret;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

