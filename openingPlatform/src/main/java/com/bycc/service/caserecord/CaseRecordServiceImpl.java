/**
 *
 */
package com.bycc.service.caserecord;

import com.bycc.common.ExcelUtil;
import com.bycc.dao.CaseRecordDao;
import com.bycc.dao.LawDao;
import com.bycc.dao.LawyerDao;
import com.bycc.dto.CaseRecordDto;
import com.bycc.dto.LawDto;
import com.bycc.dto.LawyerDto;
import com.bycc.entity.CaseRecord;
import com.bycc.entity.Law;
import com.bycc.entity.Lawyer;
import com.bycc.enumitem.OpenType;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	private LawDao lawDao;
	@Autowired
	private LawyerDao lawyerDao;

	private Logger logger = LoggerFactory.getLogger(CaseRecordServiceImpl.class);

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

	/**
	 * 公开
	 */
	public void doOpen(String ids) {
		for (String id : ids.split(",")) {
			CaseRecord cr = dao.findOne(Integer.parseInt(id));
			if (cr != null) {
				cr.setOpen(OpenType.YES);
				cr.setUpdateDate(new Date());
				dao.save(cr);
			}
		}

	}

	/**
	 * 取消公开
	 */
	public void doCancel(String ids) {
		for (String id : ids.split(",")) {
			CaseRecord cr = dao.findOne(Integer.parseInt(id));
			if (cr != null) {
				cr.setOpen(OpenType.NO);
				cr.setUpdateDate(new Date());
				dao.save(cr);
			}
		}
	}

	/**
	 * 关联法律法规
	 */
	public void doSaveLaw(Integer crId, String ids) {
		CaseRecord cr = dao.findOne(crId);
		List<Law> laws = cr.getLaws();
		for (String id : ids.split(",")) {
			boolean exit = false;
			Law law = lawDao.findOne(Integer.parseInt(id));
			for (Law l : laws) {
				if (l.getId().equals(law.getId())) {
					exit = true;
					break;
				}
			}
			if (!exit) {
				laws.add(law);
			}
		}
		if (!laws.isEmpty()) {
			cr.setLaws(laws);
			cr.setUpdateDate(new Date());
			dao.save(cr);
		}

	}

	/**
	 * 关联律师
	 */
	public void doSaveLawyer(Integer crId, String ids) {
		CaseRecord cr = dao.findOne(crId);
		List<Lawyer> lawyers = cr.getLawyers();
		for (String id : ids.split(",")) {
			boolean exit = false;
			Lawyer lawyer = lawyerDao.findOne(Integer.parseInt(id));
			for (Lawyer l : lawyers) {
				if (l.getId().equals(lawyer.getId())) {
					exit = true;
					break;
				}
			}
			if (!exit) {
				lawyers.add(lawyer);
			}
		}
		if (!lawyers.isEmpty()) {
			cr.setLawyers(lawyers);
			cr.setUpdateDate(new Date());
			dao.save(cr);
		}

	}

	/**
	 * 删除法律关联
	 */
	public void doCancelLaw(Integer caseRecordId, String ids) {
		CaseRecord cr = dao.findOne(caseRecordId);
		List<Law> laws = cr.getLaws();
		Iterator<Law> it=laws.iterator();
		for (String id : ids.split(",")) {
			Law entity = lawDao.findOne(Integer.parseInt(id));
			while(it.hasNext()){
				Law law=it.next();
				if (law.getId().equals(entity.getId())) {
					it.remove();
				}
			}
		}
		if (!laws.isEmpty()) {
			cr.setLaws(laws);
			cr.setUpdateDate(new Date());
			dao.save(cr);
		}

	}

	/**
	 * 删除律师关联
	 */
	public void doCancelLawyer(Integer caseRecordId, String ids) {

		CaseRecord cr = dao.findOne(caseRecordId);
		List<Lawyer> lawyers = cr.getLawyers();
		Iterator<Lawyer> it=lawyers.iterator();
		for (String id : ids.split(",")) {
			Lawyer entity = lawyerDao.findOne(Integer.parseInt(id));
			while(it.hasNext()){
				Lawyer lawyer=it.next();
				if (entity.getId().equals(lawyer.getId())) {
					it.remove();
				}
			}
		}
		if (!lawyers.isEmpty()) {
			cr.setLawyers(lawyers);
			cr.setUpdateDate(new Date());
			dao.save(cr);
		}

	}

	public List<LawDto> queryLaw(Integer caseRecordId) {
		CaseRecord cr = dao.findOne(caseRecordId);
		List<Law> laws = cr.getLaws();
		List<LawDto> dtos = new ArrayList<>();
		for (Law law : laws) {
			dtos.add(LawDto.toDto(law));
		}
		return dtos;
	}

	public List<LawyerDto> queryLawyer(Integer caseRecordId) {
		CaseRecord cr = dao.findOne(caseRecordId);
		List<Lawyer> lawyers = cr.getLawyers();
		List<LawyerDto> dtos = new ArrayList<>();
		for (Lawyer lawyer : lawyers) {
			dtos.add(LawyerDto.toDto(lawyer));
		}
		return dtos;
	}
	/**
	 * 导入数据
	 * return 导入数据条数
	 */
	public int importExcel(MultipartFile file) throws Exception {
		
		if (file == null) {
			throw new Exception("请选择要上传的文件！");
		}
		if (!file.getContentType().equals("application/vnd.ms-excel")
				&& !file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			throw new Exception("文件类型不正确，目前只支持excel文件！");
		}
		List<CaseRecordDto> impDtos = ExcelUtil.parse(file.getInputStream(), CaseRecordDto.class);
		List<CaseRecord> crs=new ArrayList<>();
		for (int i = 0; i < impDtos.size(); i++) {
			CaseRecordDto impDto = impDtos.get(i);
			crs.add(impDto.toEntity());			
		}
		dao.save(crs);	
		return crs.size();
	}

}
