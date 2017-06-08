package org.smartframework.platform.scanner;

import java.util.Iterator;
import java.util.List;

import org.smartframework.platform.scanner.reader.ClassReader;
import org.smartframework.utils.helper.ClassPathHelper;

public class ScannerImpl implements Scanner {
	private List<String> packageName = null;
	private List<?> readers = null;

	public void afterPropertiesSet() throws Exception {
		if (readers != null && packageName != null) {
			for (Iterator<String> iterator = packageName.iterator(); iterator.hasNext();) {
				String pname = (String) iterator.next();
				for (Iterator<Class<?>> iterator1 = ClassPathHelper.scanClass(pname).iterator(); iterator1.hasNext();) {
					Class<?> clazz = (Class<?>) iterator1.next();
					for (Iterator<?> iterator2 = readers.iterator(); iterator2.hasNext();) {
						ClassReader cr = (ClassReader) iterator2.next();
					    cr.read(clazz);
					}
				}
			}
		}
	}

	public List<String> getPackageName() {
		return this.packageName;
	}

	public void setPackageName(List<String> packageName) {
		this.packageName = packageName;
	}

	public List<?> getReaders() {
		return this.readers;
	}

	public void setReaders(List<?> readers) {
		this.readers = readers;
	}
}

