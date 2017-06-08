package org.smartframework.utils.helper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class ClassPathHelper {
	private static final String PROTOCOL_FILE = "file";
	private static final String PROTOCOL_JAR = "jar";
	private static final String PREFIX_FILE = "file:";
	private static final String JAR_URL_SEPERATOR = "!/";
	private static final String CLASS_FILE = ".class";
	private static Method addURL = null;
	private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

	private static Method initAddMethod() {
		try {
			Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			add.setAccessible(true);
			return add;
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
		}
		return null;
	}

	public static void addJar(String jarFile) throws Exception {
		addURL.invoke(classloader, new Object[] { new File(jarFile).toURI().toURL() });
	}

	public static File getJarFile(URL url) {
		String file = url.getFile();
		if (file.startsWith("file:")) {
			file = file.substring("file:".length());
		}
		int end = file.indexOf("!/");
		if (end != -1) {
			file = file.substring(0, end);
		}
		return new File(file);
	}

	public static List<Class<?>> scanClass(String packageName) throws IOException {
		Enumeration<URL> en = ClassPathHelper.class.getClassLoader().getResources(StringHelper.dot2Path(packageName));
		List<Class<?>> ret = new ArrayList();
		while (en.hasMoreElements()) {
			URL url = (URL) en.nextElement();
			if ("file".equalsIgnoreCase(url.getProtocol())) {
				File root = new File(url.getFile());
				findInDirectory(ret, root, root, packageName);
			} else if ("jar".equalsIgnoreCase(url.getProtocol())) {
				findInJar(ret, getJarFile(url), packageName);
			}
		}
		return ret;
	}

	private static void findInDirectory(List<Class<?>> results, File rootDir, File dir, String packageName) {
		File[] files = dir.listFiles();
		String rootPath = rootDir.getPath();
		for (File file : files) {
			if (file.isFile()) {
				String classFileName = file.getPath();
				if (classFileName.endsWith(".class")) {
					String className = classFileName.substring(
							rootPath.length() - packageName.length(), 
							classFileName.length() - ".class".length());
					try {
						Class<?> clz = Class.forName(StringHelper.path2Dot(className));
						results.add(clz);
					} catch (Throwable e) {
						LogHelper.log(ClassPathHelper.class, LogHelper.ERROR, "缺少class：" + className + "\r\n" + e.getMessage());
					}
				}
			} else if (file.isDirectory()) {
				findInDirectory(results, rootDir, file, packageName);
			}
		}
	}

	private static void findInJar(List<Class<?>> results, File file, String packageName) {
		JarFile jarFile = null;
		String packagePath = StringHelper.dot2Path(packageName) + "/";
		try {
			jarFile = new JarFile(file);
			Enumeration<JarEntry> en = jarFile.entries();
			while (en.hasMoreElements()) {
				JarEntry je = (JarEntry) en.nextElement();
				String name = je.getName();
				if ((name.startsWith(packagePath)) && (name.endsWith(".class"))) {
					String className = name.substring(0, name.length() - ".class".length());
					try {
						results.add(Class.forName(StringHelper.path2Dot(className)));
					} catch (Throwable e) {
						LogHelper.log(ClassPathHelper.class, LogHelper.ERROR, "缺少class：" + className + "\r\n" + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException localIOException1) {
				}
			}
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException localIOException2) {
				}
			}
		}
	}
}

