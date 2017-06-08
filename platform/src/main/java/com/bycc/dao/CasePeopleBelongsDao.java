package com.bycc.dao;

import com.bycc.entity.CasePeopleBelongs;
import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CasePeopleBelongsDao extends BaseJpaRepository<CasePeopleBelongs, Integer> {
	@Query("delete from CasePeopleBelongs belong where belong.casePeople.id = ?1 and belong.id in ?2")
	@Modifying
	void deletePeopleBelongs(Integer peopleId, List<Integer> belongsIds);

	List<CasePeopleBelongs> findByCasePeopleId(Integer peopleId);
}
