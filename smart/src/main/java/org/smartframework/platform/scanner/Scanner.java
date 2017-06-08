package org.smartframework.platform.scanner;

import java.util.List;
import org.springframework.beans.factory.InitializingBean;

public interface Scanner extends InitializingBean {
	
	public List<String> getPackageName();

	public void setPackageName(List<String> paramList);

	public List<?> getReaders();

	public void setReaders(List<?> paramList);
}

