package com.bycc.service.caserecord;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.text.ParseException;

/**
 * Created by wanghaidong on 2017/6/20.
 */
public interface CaseRecordExcelService {
    HSSFWorkbook getExcel(String handleStatus,String masterUnit,String acceptStart,String acceptEnd,String closeStart,String closeEnd) throws ParseException;
}
