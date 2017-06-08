package org.smartframework.manager.dao.group;

import org.smartframework.manager.entity.Group;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;

/**
 * @author zhaochuanfeng
 * 1.BaseJpaRepository接口扩展了JpaRepository的共用方法，每个dao都可以使用。
 * 2.可以定义GroupDaoExtend接口，用来扩展了GroupDao的方法。
 * 3.如果仅用到CRUD可以只继承BaseJpaRepository， 如果用到复杂的查询如动态参数或分页查询等，可以继承并实现GroupDaoExtend。
 * 4.原则上能用BaseJpaRepository实现的尽量不要扩展GroupDaoExtend。
 * 5.注意接口实现类的名：
 *   1) 推荐用GroupDaoImpl，这样是实现了GroupDao和GroupDaoExtend接口，这样只需在service中注入GroupDao就可以。
 *   2) 如果用GroupDaoExtendImpl，只是实现了GroupDaoExtend接口，需要在service中注入GroupDao和GroupDaoExtend。
 */
public interface GroupDao extends BaseJpaRepository<Group, Integer> {

    Group findByName(String name);

}
