package org.smartframework.platform.dictionary.model;

import java.util.List;

import org.smartframework.platform.dictionary.bean.DictionaryBean;
import org.smartframework.platform.dictionary.exception.EntryException;
import org.springframework.beans.factory.InitializingBean;

public interface DictionaryModel extends InitializingBean {
	
	public boolean isEntry(String paramString);

	public List<DictionaryBean> createEntry(String paramString) throws EntryException;
}
