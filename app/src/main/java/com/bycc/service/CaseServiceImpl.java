package com.bycc.service;

import com.bycc.dao.CaseDao;
import com.bycc.entity.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	CaseDao dao;

	@Override
	public List<Case> findAll() {
		return dao.findAll();
	}
}
