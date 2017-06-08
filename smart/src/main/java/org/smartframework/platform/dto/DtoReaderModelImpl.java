package org.smartframework.platform.dto;

import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.scanner.reader.ClassReader;

public class DtoReaderModelImpl implements ClassReader {
	
	public void read(Class<?> clazz) {
		clazz.getAnnotation(DtoClass.class);
	}
}