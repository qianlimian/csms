package com.bycc.service.caseRegister;

import com.bycc.dao.*;
import com.bycc.dto.caseRegister.CasePeopleBelongsDto;
import com.bycc.dto.caseRegister.CasePeopleBelongsSaveDto;
import com.bycc.dto.caseRegister.CasePeopleDto;
import com.bycc.dto.caseRegister.CasePeopleInspectDto;
import com.bycc.dto.caseRegister.jasper.HandlingAreaRegister;
import com.bycc.dto.caseRegister.jasper.SubBelongs;
import com.bycc.dto.caseRegister.jasper.SubInspect;
import com.bycc.dto.caseRegister.jasper.SubPeople;
import com.bycc.dto.caseRegister.jasper.SubReturn;
import com.bycc.dto.caseRegister.jasper.SubTrace;
import com.bycc.entity.*;
import com.bycc.enumitem.UsageStatus;

import org.apache.commons.lang.StringUtils;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.smartframework.platform.exception.BusinessException;
import org.smartframework.platform.jasper.constaints.JasperConstaints;
import org.smartframework.platform.jasper.dto.JasperDto;
import org.smartframework.platform.jasper.engine.JasperEngine;
import org.smartframework.utils.helper.DateHelper;
import org.smartframework.utils.helper.JasperHelper;
import org.smartframework.utils.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-5-2 11:22
 */
@Service
public class CaseRegisterServiceImpl implements CaseRegisterService {

    @Autowired
    private CaseDao caseDao;

    @Autowired
    private CaseRecordDao caseRecordDao;

	@Autowired
	private CasePeopleDao casePeopleDao;

	@Autowired
	private CasePeopleBelongsDao casePeopleBelongsDao;

    @Autowired
    private CasePeopleInspectDao inspectDao;

	@Autowired
	private BdmStrapDao strapDao;

	@Autowired
	private BdmCabinetDao cabinetDao;

	@Override
	public List<CasePeopleDto> query(Integer caseRecordId, Integer caseId, QueryBean qb) {
		String[] arr = new String[1];
		Map<String, Object> map = new HashMap<String, Object>();

		if (caseRecordId != null) {
			arr[0] = "caseRecord.id = :caseRecordId";
			map.put("caseRecordId", caseRecordId);
		}
		if (caseId != null) {
			arr[0] = "caseRecord.caze.id = :caseId";
			map.put("caseId", caseId);
		}

		List<CasePeople> entities = casePeopleDao.findByQueryBeanCondition(arr, map, qb);
		List<CasePeopleDto> dtos = new ArrayList<>();
		for (CasePeople entity : entities) {
			dtos.add(CasePeopleDto.toDto(entity));
		}
		return dtos;
	}

	@Override
	public CasePeopleDto findById(Integer id) {
		CasePeople entity = casePeopleDao.findOne(id);
		return CasePeopleDto.toDto(entity);
	}

	@Override
	public CasePeopleDto save(CasePeopleDto dto) throws Exception {
	    //办案记录
        CaseRecord caseRecord = null;
        if (null != dto.getCaseRecordId()) {
            caseRecord = caseRecordDao.findOne(dto.getCaseRecordId());
        }
        if (null != dto.getCaseId()) {
            Case caze = caseDao.findOne(dto.getCaseId());
            caseRecord = caze.getCaseRecord();
        }
        if (null == caseRecord) {
            throw new BusinessException("办案记录不存在");
        }

        //涉案人
		CasePeople entity;
		if (dto.getId() == null) {
			entity = dto.toEntity();
		} else {
			entity = casePeopleDao.findOne(dto.getId());
            entity = dto.toEntity(entity);
		}
        entity.setCaseRecord(caseRecord);
		casePeopleDao.save(entity);
		return CasePeopleDto.toDto(entity);
	}

	@Override
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			casePeopleDao.delete(Integer.valueOf(id));
		}
	}

	@Override
	public List<CasePeopleBelongsDto> findPeopleBelongs(Integer peopleId, QueryBean qb) {
		String[] hql = new String[]{"casePeople.id = :peopleId"};

		Map<String, Object> param = new HashMap<String, Object>() {
			{
				put("peopleId", peopleId);
			}
		};
		List<CasePeopleBelongs> belongs = casePeopleBelongsDao.findByQueryBeanCondition(hql, param, qb);

//		return belongs.stream().map(CasePeopleBelongsDto::toDto).collect(Collectors.toList());

        List<CasePeopleBelongsDto> dtos = new ArrayList<CasePeopleBelongsDto>();
        for (CasePeopleBelongs belong : belongs) {
            dtos.add(CasePeopleBelongsDto.toDto(belong));
        }
        return dtos;
	}

	@Override
	public List<CasePeopleBelongsDto> saveOrUpdatePeopleBelongs(CasePeopleBelongsSaveDto dto) throws Exception {
		CasePeople people = casePeopleDao.findOne(dto.getCasePeopleId());
		if (people == null) {
			throw new BusinessException("人员不存在");
		}

		// 新增
		for (CasePeopleBelongsDto newDto : dto.getNews()) {
			CasePeopleBelongs belong = newDto.toEntity();
			belong.setCasePeople(people);
			if (newDto.getCabinetId() != null) {
				belong.setCabinet(cabinetDao.findOne(newDto.getCabinetId()));
			}
			people.getCasePeopleBelongs().add(belong);
			casePeopleBelongsDao.save(belong);
		}
		// 更新
		for (CasePeopleBelongsDto updateDto : dto.getUpdates()) {
			CasePeopleBelongs belong = casePeopleBelongsDao.findOne(updateDto.getId());
			updateDto.toEntity(belong);
			if (updateDto.getCabinetId() != null) {
				belong.setCabinet(cabinetDao.findOne(updateDto.getCabinetId()));
			}
			casePeopleBelongsDao.save(belong);
		}
        for (Integer deleteId : dto.getDeletes()) {
            CasePeopleBelongs belong = casePeopleBelongsDao.findOne(deleteId);
            if (belong != null) {
                casePeopleBelongsDao.delete(belong);
                casePeopleBelongsDao.flush();
            }
        }

        List<CasePeopleBelongsDto> dtos = new ArrayList<CasePeopleBelongsDto>();
        for (CasePeopleBelongs belong : people.getCasePeopleBelongs()) {
            dtos.add(CasePeopleBelongsDto.toDto(belong));
        }
        return dtos;
	}

    @Override
    public void returnPeopleBelongs(List<Integer> belongIds) {
        List<CasePeopleBelongs> belongs = casePeopleBelongsDao.findAll(belongIds);
        for (CasePeopleBelongs belong : belongs) {
            belong.setBackDate(new Date());
            belong.setCabinet(null);
            belong.setBackOrNot(true);
            belong.setOperatorId(User.getCurrentUser().getId());
            belong.setUpdateDate(new Date());
        }
        casePeopleBelongsDao.save(belongs);
    }

	@Override
	public void bindStrap(Integer peopleId, Integer strapId) throws BusinessException {
		BdmStrap strap = strapDao.findOne(strapId);
		if (strap == null) {
			throw new BusinessException("手环不存在");
		}

		if (strap.getStatus() == UsageStatus.USED) {
			throw new BusinessException("手环已使用，不能绑定");
		}

		// 先解绑手环再绑定新手环
		CasePeople casePeople = casePeopleDao.findOne(peopleId);
		BdmStrap oldStrap = casePeople.getStrap();
		if (oldStrap != null) {
			oldStrap.setStatus(UsageStatus.UNUSED);
			oldStrap.setOperatorId(User.getCurrentUser().getId());
			oldStrap.setUpdateDate(new Date());
			strapDao.save(oldStrap);
		}

		strap.setStatus(UsageStatus.USED);
		strap.setUpdateDate(new Date());
		strapDao.save(strap);
		casePeople.setStrap(strap);
		casePeople.setUpdateDate(new Date());
		casePeople.setOperatorId(User.getCurrentUser().getId());
		casePeopleDao.save(casePeople);
	}

	@Override
	public void unBindStrap(CasePeopleDto dto) {
		CasePeople people = casePeopleDao.findOne(dto.getId());

		// 设置离开信息
		people.setNote(dto.getNote());
		people.setAllBelongsReturn(dto.getAllBelongsReturn());
		people.setLeaveReason(dto.getLeaveReason());
		people.setLeaveDate(new Date());
		people.setUpdateDate(new Date());
		people.setOperatorId(User.getCurrentUser().getId());

		// 解绑手环
		BdmStrap strap = people.getStrap();
		if (strap != null) {
			people.setStrap(null);
			strap.setUpdateDate(new Date());
			strap.setStatus(UsageStatus.UNUSED);
			strap.setOperatorId(User.getCurrentUser().getId());
			strapDao.save(strap);
		}

		casePeopleDao.save(people);
	}

	@Override
	public CasePeopleInspectDto findPeopleInspect(Integer peopleId) {
		CasePeople people = casePeopleDao.findOne(peopleId);
		return CasePeopleInspectDto.toDto(people.getInpect());
	}

	@Override
	public CasePeopleInspectDto savePeopleInspect(CasePeopleInspectDto dto) {
        CasePeopleInspect entity;
		if (dto.getId() == null) {
			entity = dto.toEntity();
			entity.setCasePeople(casePeopleDao.findOne(dto.getCasePeopleId()));
		} else {
            entity = inspectDao.findOne(dto.getId());
            dto.toEntity(entity);
		}
        inspectDao.save(entity);
		return CasePeopleInspectDto.toDto(entity);
	}

	@Override
	public void print(Integer peopleId, String reportName) throws BusinessException {
		
		HandlingAreaRegister register = new HandlingAreaRegister();
		
		CasePeople casePeople = casePeopleDao.findOne(peopleId);
		CaseRecord caseRecord = casePeople.getCaseRecord();
		if (caseRecord != null) {
			BdmPoliceStation masterUnit = caseRecord.getMasterUnit();
			if (masterUnit != null) {
				register.setPoliceStationName(masterUnit.getName());
			}
		}
		
		//人员基本情况
		SubPeople sp = new SubPeople();
        sp.setName(casePeople.getName());
        sp.setGender(casePeople.getGender() != null ? casePeople.getGender().value() : null);
        sp.setBirthday(DateHelper.formatDateToString(casePeople.getBirthday(), "yyyy-MM-dd"));
        sp.setTelNum(casePeople.getTelNum());
        sp.setCertificateType(casePeople.getCertificateType() != null ? casePeople.getCertificateType().value() : null);
        sp.setCertificateNum(casePeople.getCertificateNum());
        sp.setAddress(casePeople.getAddress());
        if (caseRecord != null) {
        	sp.setCaseNo(caseRecord.getCaseCode());
            sp.setCaseSummary(caseRecord.getCaseSummary());
		}
        sp.setEnterDate(DateHelper.formatDateToString(casePeople.getEnterDate(), "yyyy-MM-dd hh:mm:ss"));
        sp.setEnterReason(casePeople.getEnterReason() != null ? casePeople.getEnterReason().key() : null);
        register.setSubPeopleMap(sp);
        
        //随身物品
        List<SubBelongs> sbList = new ArrayList<SubBelongs>();
        List<CasePeopleBelongs> casePeopleBelongsList = casePeople.getCasePeopleBelongs();
        for (CasePeopleBelongs casePeopleBelongs : casePeopleBelongsList) {
        	SubBelongs sb = new SubBelongs();
        	sb.setName(casePeopleBelongs.getName());
            sb.setDescription(casePeopleBelongs.getDescription());
            sb.setCount(casePeopleBelongs.getCount());
            sb.setUnit(casePeopleBelongs.getUnit());
            sb.setStoreType(casePeopleBelongs.getStoreType() != null ? casePeopleBelongs.getStoreType().key() : null);
            sb.setInvolvedOrNot(casePeopleBelongs.getInvolvedOrNot());
            sb.setCabinetNo(casePeopleBelongs.getCabinet() != null ? casePeopleBelongs.getCabinet().getCode() : null);
            sbList.add(sb);
		}
        register.setSubBelongsList(sbList);
        //人身安全检查
        CasePeopleInspect casePeopleInspect = casePeople.getInpect();
        if (casePeopleInspect != null) {
        	SubInspect si = new SubInspect();
        	si.setInspection(casePeopleInspect.getInspection());
            si.setStatement(casePeopleInspect.getStatement());
            si.setCollectItem(StringUtils.join(casePeopleInspect.getCollectItems().toArray(), ","));
            si.setCollectOrNot(casePeopleInspect.getCollectOrNot());
            si.setStoreOrNot(casePeopleInspect.getStoreOrNot());
            si.setVerifyOrNot(casePeopleInspect.getVerifyOrNot());
            register.setSubInspectMap(si);
		}
        
        //人员轨迹（测试数据）
        List<SubTrace> stList = new ArrayList<SubTrace>();
        SubTrace st1 = new SubTrace();
        st1.setDateRange("12:00:00 至 12:15:00");
        st1.setRoomName("人身检查室");
        stList.add(st1);
        SubTrace st2 = new SubTrace();
        st2.setDateRange("12:15:00 至 12:20:00");
        st2.setRoomName("信息采集");
        stList.add(st2);
        SubTrace st3 = new SubTrace();
        st3.setDateRange("12:20:00 至 12:30:00");
        st3.setRoomName("讯询问室");
        stList.add(st3);
        register.setSubTraceList(stList);
        
        //离开情况
        SubReturn sr = new SubReturn();
        sr.setLeaveDate(DateHelper.formatDateToString(casePeople.getLeaveDate(), "yyyy-MM-dd hh:mm:ss"));
        sr.setLeaveReason(casePeople.getLeaveReason());
        sr.setAllReturnOrNot(casePeople.getAllBelongsReturn());
        sr.setNote(casePeople.getNote());
        register.setSubReturnMap(sr);
        
        JasperDto jasperDto = new JasperDto();
        jasperDto.setExportName("办案区使用登记表");
        jasperDto.setExportType(JasperConstaints.EXPORT_TYPE_VIEW);
        jasperDto.setReportName(reportName);
        jasperDto.setJasperFillParams(new HashMap<String, Object>(){{ put("ROOT_PATH", JasperHelper.getJasperRootPath()); }}); //路径参数
        new JasperEngine().getResponse(jasperDto, JsonHelper.getJson(register));
	}

}
