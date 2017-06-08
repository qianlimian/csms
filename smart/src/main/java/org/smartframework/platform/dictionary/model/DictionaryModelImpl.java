package org.smartframework.platform.dictionary.model;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.smartframework.platform.context.SpringContext;
import org.smartframework.platform.dictionary.bean.DictionaryBean;
import org.smartframework.platform.dictionary.bean.entry.JpaEntry;
import org.smartframework.platform.dictionary.bean.entry.ListEntry;
import org.smartframework.platform.dictionary.exception.BeanException;
import org.smartframework.platform.dictionary.exception.EntryException;
import org.smartframework.platform.dictionary.exception.MethodException;
import org.smartframework.platform.scanner.reader.ClassReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
public class DictionaryModelImpl implements DictionaryModel, ClassReader {
	
	private static final Logger log = LoggerFactory.getLogger(DictionaryModelImpl.class);
	private String[] entrys = null;
	private static Set<JpaEntry> jpaEntrys = new ConcurrentSkipListSet<JpaEntry>();
	private static Set<String> enumEntrys = new ConcurrentSkipListSet<String>();
	private static Set<ListEntry> listEntrys = new ConcurrentSkipListSet<ListEntry>();

	public boolean isEntry(String name) {
		for (JpaEntry j : jpaEntrys) {
			if (j.getQueryName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		for (String c : enumEntrys) {
			if (c.equalsIgnoreCase(name)) {
				return true;
			}
		}
		for (ListEntry l : listEntrys) {
			if (l.getId().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public void afterPropertiesSet() throws Exception {
		PathMatchingResourcePatternResolver pm = new PathMatchingResourcePatternResolver();
		SAXReader reader = new SAXReader();
		if (getEntrys() == null) {
			return;
		}
		for (String path : getEntrys()) {
			for (Resource r : pm.getResources(path)) {
				Document doc = reader.read(r.getInputStream());
				for (Object o : doc.getRootElement().elements("jpa")) {
					Element e = (Element) o;
					jpaEntrys.add(new JpaEntry(e.attributeValue("queryName"), 
							e.attributeValue("key"), e.attributeValue("value"), e.attributeValue("emf")));
				}
				for (Object o : doc.getRootElement().elements("list")) {
					Element e = (Element) o;
					listEntrys.add(new ListEntry(e.attributeValue("class"), e.attributeValue("method"), 
							e.attributeValue("key"), e.attributeValue("value"), e.attributeValue("emf")));
				}
			}
		}
	}
	
	public void read(Class<?> clazz) {
		if (clazz.isEnum()) {
			log.debug("增加枚举字典:" + clazz.getName());
			enumEntrys.add(clazz.getName());
		}
	}

	public List<DictionaryBean> createEntry(String name) throws EntryException {
		for (JpaEntry j : jpaEntrys) {
			if (j.getQueryName().equalsIgnoreCase(name)) {
				try {
					if (j.getEmf() != null) {
						EntityManager em = ((EntityManagerFactory) SpringContext.getBean(j.getEmf())).createEntityManager();
						return DictionaryBean.createEntries(em.createNamedQuery(j.getQueryName()).getResultList(), 
								j.getKeyName(), j.getValueName());
					}
				} catch (BeanException e) {
					throw new EntryException(e);
				}
			}
		}
		for (String c : enumEntrys) {
			if (c.equalsIgnoreCase(name)) {
				try {
					return DictionaryBean.createEntries(Class.forName(c));
				} catch (Exception e) {
					throw new EntryException(e);
				}
			}
		}
		for (ListEntry l : listEntrys) {
			if (l.getId().equalsIgnoreCase(name)) {
				try {
					return DictionaryBean.createEntries(l.getList(), l.getKeyName(), l.getValueName());
				} catch (BeanException e) {
					throw new EntryException(e);
				} catch (MethodException e) {
					throw new EntryException(e);
				}
			}
		}
		throw new EntryException();
	}
	
	public String[] getEntrys() {
		return this.entrys;
	}

	public void setEntrys(String[] entrys) {
		this.entrys = entrys;
	}
}

