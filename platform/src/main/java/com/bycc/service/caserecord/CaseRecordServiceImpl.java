/**
 *
 */
package com.bycc.service.caserecord;

import com.bycc.dao.*;
import com.bycc.dto.CaseFindParamDto;
import com.bycc.dto.CaseRecordDto;
import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.Case;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseHandle;
import com.bycc.enumitem.CaseType;
import org.apache.poi.hssf.usermodel.*;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;
import org.smartframework.utils.helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.util.*;

/**
 * @author gaoningbo
 * @description
 * @date 2017年4月14日
 */
@Service
public class CaseRecordServiceImpl implements CaseRecordService {

	@Autowired
	private CaseRecordDao dao;
	@Autowired
	private BdmPoliceStationDao bpsDao;
	@Autowired
	private BdmPoliceDao policeDao;
	@Autowired
	private CaseDao caseDao;
	@PersistenceContext
	private EntityManager em;

	public CaseRecordDto saveCaseRecord(CaseRecordDto dto) throws Exception {
		CaseRecord caseRecord = null;
		if (dto.getId() == null) {
			caseRecord = dto.toEntity();
		} else {
			caseRecord = dao.findOne(dto.getId());
			if (caseRecord != null) {
				dto.toEntity(caseRecord);
			}
		}
		caseRecord.setCaze(dto.getCaseId() != null ? caseDao.findOne(dto.getCaseId()) : null);
		caseRecord.setAcceptUnit(dto.getAcceptUnitId() != null ? bpsDao.findOne(dto.getAcceptUnitId()) : null);
		caseRecord.setMasterUnit(dto.getMasterUnitId() != null ? bpsDao.findOne(dto.getMasterUnitId()) : null);
		caseRecord.setSlaveUnit(dto.getSlaveUnitId() != null ? bpsDao.findOne(dto.getSlaveUnitId()) : null);
		caseRecord.setAcceptPolice(dto.getAcceptPoliceId() != null ? policeDao.findOne(dto.getAcceptPoliceId()) : null);
		caseRecord.setMasterPolice(dto.getMasterPoliceId() != null ? policeDao.findOne(dto.getMasterPoliceId()) : null);
		caseRecord.setSlavePolice(dto.getSlavePoliceId() != null ? policeDao.findOne(dto.getSlavePoliceId()) : null);
		dao.save(caseRecord);
		return CaseRecordDto.toDto(caseRecord);
	}

	public CaseRecordDto findById(Integer id) {
		CaseRecord cr = dao.findOne(id);
		return CaseRecordDto.toDto(cr);
	}

	public List<CaseRecordDto> query(QueryBean qb) {
		List<CaseRecord> crs = dao.findByQueryBean(qb);
		List<CaseRecordDto> dtos = new ArrayList<CaseRecordDto>();
		for (CaseRecord cr : crs) {
			dtos.add(CaseRecordDto.toDto(cr));
		}
		return dtos;
	}


	public void deleteCaseRecordById(String ids) {
		for (String id : ids.split(",")) {
			CaseRecord caseRecord = dao.findOne(Integer.valueOf(id));
			dao.delete(caseRecord);
		}
	}

	public CaseRecordDto startCase(Integer caseId, Integer caseRecordId) throws Exception {
        CaseRecord caseRecord = null;

        if (null != caseRecordId) {
            caseRecord = dao.findOne(caseRecordId);
        }
        //createOrUpdate
        if (null != caseId) {
            Case caze = caseDao.findOne(caseId);
            caseRecord = caze.getCaseRecord();
            if (caseRecord == null) {
                if (caze.getMasterUnit() == null) {
                    throw new BusinessException("主办单位不能为空！");
                }
                if (caze.getSlaveUnit() == null) {
                    throw new BusinessException("协办单位不能为空！");
                }
                if (caze.getMasterPolice() == null) {
                    throw new BusinessException("主办人不能为空！");
                }
                if (caze.getSlavePolice() == null) {
                    throw new BusinessException("协办人不能为空！");
                }
                caseRecord = CaseRecordDto.toEntity(caze);
            }
        }
        //办理中...
	    if (null != caseRecord) {
            caseRecord.setCaseHandle(CaseHandle.HANDLING);
            caseRecord.setStartDate(new Date());
            caseRecord.setUpdateDate(new Date());
            dao.save(caseRecord);

            return CaseRecordDto.toDto(caseRecord);
        }
		return null;
	}

	public CaseRecordDto finishCase(Integer caseId, Integer caseRecordId) throws Exception {
        CaseRecord caseRecord = null;

        if (null != caseRecordId) {
            caseRecord = dao.findOne(caseRecordId);
        }

        if (null != caseId) {
            Case caze = caseDao.findOne(caseId);
            caseRecord = caze.getCaseRecord();
            if (caseRecord == null) {
                throw new BusinessException("当前案件未办理，无法结束办案！");
            }
        }
        //已办理
        if (null != caseRecord) {
            caseRecord.setCaseHandle(CaseHandle.HANDLED);
            caseRecord.setUpdateDate(new Date());
            caseRecord.setCloseDate(new Date());
            dao.save(caseRecord);

            return CaseRecordDto.toDto(caseRecord);
        }
        return null;
	}




	@Override
	public List<CaseRecordDto> search(QueryBean queryBean, CaseFindParamDto caseFindParamDto) throws ParseException {
		List<String> arr = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		if (caseFindParamDto.getAlarmCode()!=null && !caseFindParamDto.getAlarmCode().equals("")){
			arr.add("alarmCode = :alarmCode");
			map.put("alarmCode",caseFindParamDto.getAlarmCode());
		}
		if (caseFindParamDto.getCaseType()!=null && !caseFindParamDto.getCaseType().equals("")){
			arr.add("caseType = :caseType");
			map.put("caseType", CaseType.getMatchByKey(caseFindParamDto.getCaseType()));
		}
		if (caseFindParamDto.getAcceptUnit()!=null && !caseFindParamDto.getAcceptUnit().equals("")){
			arr.add("acceptUnit.id = :acceptUnit");
			map.put("acceptUnit",Integer.valueOf(caseFindParamDto.getAcceptUnit()));
		}
		if (caseFindParamDto.getAcceptPolice()!=null && !caseFindParamDto.getAcceptPolice().equals("")){
			arr.add("acceptPolice.user.name = :acceptPolice");
			map.put("acceptPolice",caseFindParamDto.getAcceptPolice());
		}
		if (caseFindParamDto.getOccurStart()!=null && !caseFindParamDto.getOccurStart().equals("")){
			arr.add("occurDate >= :occurStart");
			map.put("occurStart",DateHelper.formatStringToDate(caseFindParamDto.getOccurStart(),"yyyy-MM-dd"));
		}
		if (caseFindParamDto.getOccurEnd()!=null && !caseFindParamDto.getOccurEnd().equals("")){
			arr.add("occurDate <= :occurEnd");
			map.put("occurEnd",DateHelper.formatStringToDate(caseFindParamDto.getOccurEnd(),"yyyy-MM-dd"));
		}
		if (caseFindParamDto.getMasterUnit()!=null && !caseFindParamDto.getMasterUnit().equals("")){
			arr.add("masterUnit.id = :masterUnit");
			map.put("masterUnit", Integer.valueOf(caseFindParamDto.getMasterUnit()));
		}
		if (caseFindParamDto.getHandleStatus()!=null && !caseFindParamDto.getHandleStatus().equals("")){
			arr.add("caseHandle = :caseHandle");
			map.put("caseHandle",CaseHandle.getMatchByKey(caseFindParamDto.getHandleStatus()));
		}
		if (caseFindParamDto.getAcceptStart()!=null && !caseFindParamDto.getAcceptStart().equals("")){
			arr.add("acceptDate >= :acceptStart");
			map.put("acceptStart",DateHelper.formatStringToDate(caseFindParamDto.getAcceptStart(),"yyyy-MM-dd"));
		}
		if (caseFindParamDto.getAcceptEnd()!=null && !caseFindParamDto.getAcceptEnd().equals("")){
			arr.add("acceptDate <= :acceptEnd");
			map.put("acceptEnd",DateHelper.formatStringToDate(caseFindParamDto.getAcceptEnd(),"yyyy-MM-dd"));
		}
		if (caseFindParamDto.getCloseStart()!=null && !caseFindParamDto.getCloseStart().equals("")){
			arr.add("closeDate >= :closeStart");
			map.put("closeStart",DateHelper.formatStringToDate(caseFindParamDto.getCloseStart(),"yyyy-MM-dd"));
		}
		if (caseFindParamDto.getCloseEnd()!=null && !caseFindParamDto.getCloseEnd().equals("")){
			arr.add("closeDate <= :closeEnd");
			map.put("closeEnd",DateHelper.formatStringToDate(caseFindParamDto.getCloseEnd(),"yyyy-MM-dd"));
		}
		List<CaseRecord> crs = dao.findByQueryBeanCondition(arr.toArray(new String[arr.size()]),map,queryBean);

		List<CaseRecordDto> dtos = new ArrayList<CaseRecordDto>();
		for (CaseRecord cr : crs) {
			dtos.add(CaseRecordDto.toDto(cr));
		}
		return dtos;
	}

	@Override
	public HSSFWorkbook getExcel(QueryBean queryBean, CaseFindParamDto caseFindParamDto) throws ParseException {
		String[] excelHeader = { "ID","案件编号", "案件名称", "嫌疑人","主办单位","主办人","办理状态","警情编号","简要案情","案件类型","受理单位","受理人","协办单位","协办人","案发时间","受案时间","立案时间","办案时间","结案时间","案件状态","备注"};
		List<CaseRecordDto> list=search(queryBean,caseFindParamDto);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet");
		//第一行的空白
		sheet.createRow(0);
		//第二行的标题栏
		HSSFRow row = sheet.createRow(1);
		HSSFCellStyle style = wb.createCellStyle();
		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
		}
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 2);
			CaseRecordDto caseRecordDto = list.get(i);
			CaseRecord caseRecord = caseRecordDto.toEntity();
			row.createCell(0).setCellValue(caseRecord.getId()==null?"":caseRecord.getId().toString());
			row.createCell(1).setCellValue(caseRecord.getCaseCode());
			row.createCell(2).setCellValue(caseRecord.getCaseName());
			row.createCell(3).setCellValue(caseRecord.getSuspect());
			row.createCell(4).setCellValue(caseRecord.getMasterUnit()==null?"":caseRecord.getMasterUnit().getName());
			row.createCell(5).setCellValue(caseRecord.getMasterPolice()==null?"":caseRecord.getMasterPolice().getUser().getName());
			row.createCell(6).setCellValue(caseRecord.getCaseHandle().name());
			row.createCell(7).setCellValue(caseRecord.getAlarmCode());
			row.createCell(8).setCellValue(caseRecord.getCaseSummary());
			row.createCell(9).setCellValue(caseRecord.getCaseType().name());
			row.createCell(10).setCellValue(caseRecord.getAcceptUnit()==null?"":caseRecord.getAcceptUnit().getName());
			row.createCell(11).setCellValue(caseRecord.getAcceptPolice()==null?"":caseRecord.getAcceptPolice().getUser().getName());
			row.createCell(12).setCellValue(caseRecord.getSlaveUnit()==null?"":caseRecord.getSlaveUnit().getName());
			row.createCell(13).setCellValue(caseRecord.getSlavePolice()==null?"":caseRecord.getSlavePolice().getUser().getName());
			if (caseRecord.getOccurDate()!=null){
				row.createCell(14).setCellValue(caseRecord.getOccurDate().toString());
			}
			if (caseRecord.getAcceptDate()!=null){
				row.createCell(15).setCellValue(caseRecord.getAcceptDate().toString());
			}
			if (caseRecord.getRegisterDate()!=null) {
				row.createCell(16).setCellValue(caseRecord.getRegisterDate().toString());
			}
			if (caseRecord.getStartDate()!=null){
				row.createCell(17).setCellValue(caseRecord.getStartDate().toString());
			}
			if (caseRecord.getCloseDate()!=null){
				row.createCell(18).setCellValue(caseRecord.getCloseDate().toString());
			}
			row.createCell(19).setCellValue(caseRecord.getCaze()==null?"":caseRecord.getCaze().getCaseStatus().name());
			row.createCell(20).setCellValue(caseRecord.getNote()==null?"":caseRecord.getNote());

		}

		for (int i = 0; i < excelHeader.length; i++) {
			sheet.autoSizeColumn(i,true);
		}
		return wb;
	}

}
