package org.smartframework.platform.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * 
 * @description springData jpa 工厂
 * @author gaoningbo
 *
 */
public class BaseJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
		extends JpaRepositoryFactoryBean<T, S, ID> {

	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new CommonRepositoryFactory(entityManager);
	}

	private static class CommonRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

		private EntityManager entityManager;

		public CommonRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			this.entityManager = entityManager;
		}

		protected Object getTargetRepository(RepositoryInformation information) {
			JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
			return new BaseJpaRepositoryImpl(entityInformation, entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return BaseJpaRepository.class;
		}
	}

}
