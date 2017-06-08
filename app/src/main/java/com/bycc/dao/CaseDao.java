package com.bycc.dao;

import com.bycc.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseDao extends JpaRepository<Case, Integer> {

}
