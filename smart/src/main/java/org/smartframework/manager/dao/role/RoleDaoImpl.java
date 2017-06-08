package org.smartframework.manager.dao.role;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class RoleDaoImpl implements RoleDaoExtend {


    @PersistenceContext
    private EntityManager em;

    /**
     * @description 分页查询用原生JPA(em)实现
     */
    @Override
    public List<Role> findByGroupId(QueryBean qb, Integer groupId) {
        StringBuilder queryStr = new StringBuilder("select distinct r from Role r inner join r.groups as g where 1=1 ");

        if (null != groupId) {
            queryStr.append("and g.id = :groupId");
        }

        TypedQuery<Role> query = em.createQuery(queryStr.toString(), Role.class);

        if (null != groupId) {
            query.setParameter("groupId", groupId);
        }

        return qb.getResultList(query);
    }
}
