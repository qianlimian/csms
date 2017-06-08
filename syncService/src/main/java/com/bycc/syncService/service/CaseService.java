package com.bycc.syncService.service;

import com.bycc.syncService.entity.Case;

import java.util.List;

/**
 * Created by wanghaidong on 2017/5/26.
 */
public interface CaseService {
     List<Case> findAll();
     Case save(Case myCase);
}
