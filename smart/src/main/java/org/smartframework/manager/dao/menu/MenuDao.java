package org.smartframework.manager.dao.menu;

import java.util.List;

import org.smartframework.manager.entity.Menu;
import org.smartframework.manager.entity.Plugin;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuDao extends BaseJpaRepository<Menu, Integer>, MenuDaoExtend {

	List<Menu> findByTypeAndUrl(String type, String url);

	@Query(value = "select e from Menu e where e.parent = :parent and e.id in :ids")
	List<Menu> findByParentLimitIds(@Param("parent") Menu parent, @Param("ids") List<Integer> ids);

	@Query(value = "select e from Menu e where e.type = :type and e.parent = :parent and e.id in :ids")
	List<Menu> findByTypeAndParentLimitIds(@Param("type") String type, @Param("parent") Menu parent, @Param("ids") List<Integer> ids);

	@Query(value = "select e from Menu e where e.type = :type and e.plugin = :plugin and e.id in :ids")
	List<Menu> findByTypeAndPluginLimitIds(@Param("type") String type, @Param("plugin") Plugin plugin, @Param("ids") List<Integer> ids);

	@Query(value = "select e from Menu e where e.parent is null")
	List<Menu> findRootMenus();

    List<Menu> findByParentOrderByDisplayOrder(Menu parent);
}
