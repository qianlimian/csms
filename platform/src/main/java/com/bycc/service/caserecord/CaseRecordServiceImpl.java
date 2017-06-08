/**
 *
 */
package com.bycc.service.caserecord;

import com.bycc.dao.BdmPoliceDao;
import com.bycc.dao.BdmPoliceStationDao;
import com.bycc.dao.CaseDao;
import com.bycc.dao.CaseRecordDao;
import com.bycc.dto.CaseRecordDto;
import com.bycc.entity.Case;
import com.bycc.entity.CaseRecord;
import com.bycc.enumitem.CaseHandle;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

}
