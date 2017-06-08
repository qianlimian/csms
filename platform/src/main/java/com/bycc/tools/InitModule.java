package com.bycc.tools;

import org.smartframework.manager.service.module.ModuleService;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @description 初始化数据
 * @author zhaochuanfeng
 */
public class InitModule {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:/dbgenContext.xml");
		EntityManagerFactory emf = (EntityManagerFactory) ctx.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		//--------------------初始化数据------------------------
		initYmlModule(ctx);
		//--------------------------------------------------
		
		t.commit();
		em.close();
		emf.close();
	}

	//初始化yml
	private static void initYmlModule(ClassPathXmlApplicationContext ctx) throws FileNotFoundException {
		Yaml yaml = new Yaml();
		File f = new File(InitModule.class.getResource("/").getPath() + "yml");
		if (f.isDirectory()) {
			File[] fileArray = f.listFiles();
			for (int i = 0; i < fileArray.length; i++) {
				Object result = yaml.load(new FileInputStream(fileArray[i]));
				System.out.println("+++++++++++++++++++++++++++++++++++");
				System.out.println(JsonHelper.getJson(result));
				System.out.println("+++++++++++++++++++++++++++++++++++");

				ModuleService moduleService = (ModuleService) ctx.getBean("moduleServiceImpl");
				moduleService.initYmlModule(result);
			}
		}
	}

}
