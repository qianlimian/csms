package com.bycc.syncService.service;

import com.bycc.syncService.dao.CaseDao;
import com.bycc.syncService.entity.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by wanghaidong on 2017/5/26.
 */
@Service
public class CaseServiceImpl implements CaseService{
    @Autowired
    private CaseDao caseDao;
    @Override
    public List<Case> findAll() {
        return caseDao.findAll();
    }

    @Override
    public Case save(Case myCase) {
        return caseDao.save(myCase);
    }
}
