package com.bycc.dao;

import com.bycc.entity.BdmEvaluation;

import java.util.List;

import org.smartframework.platform.repository.jpa.BaseJpaRepository;
import org.springframework.data.domain.Sort;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
public interface BdmEvaluationDao extends BaseJpaRepository<BdmEvaluation, Integer> {

	List<BdmEvaluation> findByParent(Integer parent, Sort sort);

	void deleteByParent(Integer id);
}
