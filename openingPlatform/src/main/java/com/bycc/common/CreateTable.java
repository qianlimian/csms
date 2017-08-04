/**
 * 
 */
package com.bycc.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description
 * @author gaoningbo
 * @date 2016年9月5日
 * 
 */
public class CreateTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath*:/dbgenContext.xml");
		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) ctx.getBean("entityManagerFactory");
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.getTransaction().commit();
		em.close();
		entityManagerFactory.close();
	}
}
