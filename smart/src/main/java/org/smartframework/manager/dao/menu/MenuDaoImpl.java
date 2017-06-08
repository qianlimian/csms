package org.smartframework.manager.dao.menu;

import org.smartframework.manager.dto.menu.MenuCondition;
import org.smartframework.manager.entity.Menu;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class MenuDaoImpl implements MenuDaoExtend {

    @PersistenceContext
    private EntityManager em;

    /**
     * @description 分页查询用原生JPA(em)实现
     */
    @Override
    public List<Menu> findByCondition(MenuCondition condition) {
        StringBuilder queryStr = new StringBuilder("select m from Menu m where 1=1 ");

        if (null != condition.getName() && !"".equals(condition.getName())) {
            queryStr.append(" and m.name like :name");
        }

        queryStr.append(" order by m.displayOrder ");

        TypedQuery<Menu> query = em.createQuery(queryStr.toString(), Menu.class);

        if (null != condition.getName() && !"".equals(condition.getName())) {
            query.setParameter("name", "%" + condition.getName() + "%");
        }

        return query.getResultList();
    }

}
