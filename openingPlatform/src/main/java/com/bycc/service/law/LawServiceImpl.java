/**
 *
 */
package com.bycc.service.law;


import com.bycc.common.ExcelUtil;
import com.bycc.dao.LawDao;
import com.bycc.dto.LawDto;
import com.bycc.entity.Law;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaoningbo
 * @description
 * @date 2017&#x5e74;7&#x6708;11&#x65e5;
 */
@Service
public class LawServiceImpl implements LawService {

	@Autowired
	LawDao dao;

	@Override
	public List<LawDto> query(QueryBean qb) {
		List<Law> laws = dao.findByQueryBean(qb);
		List<LawDto> dtos = new ArrayList<LawDto>();
		for (Law law : laws) {
			dtos.add(LawDto.toDto(law));
		}
		return dtos;
	}

	/**
	 * 按id查找
	 */
	@Override
	public LawDto findById(Integer id) {
		Law domain = dao.findOne(id);
		return LawDto.toDto(domain);
	}

	/**
	 * 查询所有
	 */
	@Override
	public List<LawDto> findAll() {
		List<Law> laws = dao.findAll();
		List<LawDto> dtos = new ArrayList<LawDto>();
		for (Law law : laws) {
			dtos.add(LawDto.toDto(law));
		}
		return dtos;
	}

	/**
	 * 保存
	 */
	@Override
	public LawDto save(LawDto dto) throws Exception {
		Law law = null;
		if (dto.getId() == null) {
			law = dto.toEntity();
		} else {
			law = dao.findOne(dto.getId());
			if (law != null) {
				dto.toEntity(law);
			}
		}
		dao.save(law);
		return LawDto.toDto(law);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(String ids) {
		for (String id : ids.split(",")) {
			Law law = dao.findOne(Integer.valueOf(id));
			dao.delete(law);
		}

	}

	@Override
	public int importExcel(MultipartFile file) throws Exception {
		if (file == null) {
			throw new Exception("请选择要上传的文件！");
		}
		if (!file.getContentType().equals("application/vnd.ms-excel")
				&& !file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			throw new Exception("文件类型不正确，目前只支持excel文件！");
		}
		List<LawDto> impDtos = ExcelUtil.parse(file.getInputStream(), LawDto.class);
		List<Law> laws = new ArrayList<>();
		for (int i = 0; i < impDtos.size(); i++) {
			LawDto lawDto = impDtos.get(i);
			laws.add(lawDto.toEntity());
		}
		dao.save(laws);
		return laws.size();
	}


}
