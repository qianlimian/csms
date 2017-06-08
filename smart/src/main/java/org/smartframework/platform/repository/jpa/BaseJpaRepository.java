package org.smartframework.platform.repository.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 * @description 扩展JpaRepository基类，全局自定义方法
 * @author zhaochuanfeng
 *
 */
@NoRepositoryBean
public interface BaseJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    
    List<T> findByQueryBeanCondition(String[] array, Map<String, Object> map, QueryBean queryBean);

    List<T> findByQueryBean(QueryBean queryBean);

}
