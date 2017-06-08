package org.smartframework.manager.dao.user;

import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl implements UserDaoExtend {


    @PersistenceContext
    private EntityManager em;

    /**
     * @description 分页查询用原生JPA(em)实现
     */
    @Override
    public List<User> findByGroupId(QueryBean qb, Integer groupId) {
        StringBuilder queryStr = new StringBuilder("select distinct u from User u inner join u.groups as g where 1=1 ");

        if (null != groupId) {
            queryStr.append("and g.id = :groupId");
        }

        TypedQuery<User> query = em.createQuery(queryStr.toString(), User.class);

        if (null != groupId) {
            query.setParameter("groupId", groupId);
        }

        return qb.getResultList(query);
    }
}
