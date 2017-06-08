package org.smartframework.manager.dao.user;

import java.util.List;

import org.smartframework.manager.entity.User;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends BaseJpaRepository<User, Integer>, UserDaoExtend {
    @Query("select u from User u where u.loginName = :loginName ")
    List<User> findByLoginName(@Param("loginName") String loginName);
    
    @Query("select distinct r.name from Role r "
    		+ "inner join r.groups as g "
    		+ "inner join g.users as u "
    		+ "where u.loginName=:loginName" )
    List<String> findRoleByLoginName(@Param("loginName") String loginName);

}
