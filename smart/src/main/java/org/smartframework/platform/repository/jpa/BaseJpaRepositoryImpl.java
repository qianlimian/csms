package org.smartframework.platform.repository.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 * @description 扩展JpaRepository基类，全局自定义方法的实现
 * @author zhaochuanfeng
 *
 */
@NoRepositoryBean
public class BaseJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseJpaRepository<T, ID> {

	private final JpaEntityInformation<T, ?> entityInformation;
	private final EntityManager em;

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.entityInformation = entityInformation;
		this.em = em;
	}

	/**
	 * @description
	 * @param String[] array
	 * @param Map<String, Object> map
	 * @param QueryBean {pageSize:10, currentPage:1, offset:0}
	 */
	@Override
	public List<T> findByQueryBeanCondition(String[] array, Map<String, Object> map, QueryBean queryBean) {
		//实体名
		String entityName = this.entityInformation.getEntityName();
		//HQL
		StringBuilder hql = new StringBuilder("from " + entityName + " where 1=1");

		//查询条件
		if (array.length > 0) {
			hql.append(" and " + String.join(" and ", array));
		}

		//grid过滤参数
		Map<String, Object> filters = queryBean.getFilters();
		if (filters != null) {
			hql.append(" and " + String.join(" and ", queryBean.getFiltersParam()));

			map.putAll(queryBean.getFiltersValue());
		}

		//grid排序参数
        String[] sorts = queryBean.getSorts();
		if (sorts != null) {
			hql.append(" order by " + String.join(",", sorts));
		}

		//构造查询
		TypedQuery<T> query = em.createQuery(hql.toString(), getDomainClass());

		//查询条件参数赋值
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}

		//page查询
		return queryBean.getResultList(query);
	}

	/**
	 * @description
	 * @param QueryBean
	 * {
	 *  pageSize:10, currentPage:1, offset:0,
	 *  filter:{logic:"and", filters:[{value:"", field:"", operator:"", ignoreCase:""}, {}]},
	 *  sort:[{field:"",dir:"asc"}]
	 * }
	 */
	@Override
	public List<T> findByQueryBean(QueryBean queryBean) {
		//实体名
		String entityName = this.entityInformation.getEntityName();
		//HQL
		StringBuilder hql = new StringBuilder("from " + entityName + " where 1=1");

		//grid过滤参数
		Map<String, Object> filters = queryBean.getFilters();
		if (filters != null) {
			hql.append(" and " + String.join(" and ", queryBean.getFiltersParam()));
		}

		//grid排序参数
		String[] sorts = queryBean.getSorts();
		if (sorts != null) {
			hql.append(" order by " + String.join(",", sorts));
		}

		//构造查询
		TypedQuery<T> query = em.createQuery(hql.toString(), getDomainClass());

		//grid过滤参数赋值
		if (filters != null) {
			Map<String, Object> values = queryBean.getFiltersValue();
			for (String key : values.keySet()) {
				query.setParameter(key, values.get(key));
			}
		}

		//page查询
		return queryBean.getResultList(query);
	}

}
