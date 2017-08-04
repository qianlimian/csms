package com.bycc.service.lawyer;

import com.bycc.common.ExcelUtil;
import com.bycc.dao.LawyerDao;
import com.bycc.dto.LawyerDto;
import com.bycc.entity.Lawyer;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mt.W on 2017/7/11.
 */
@Service
public class LawyerServiceImpl implements LawyerService {

    @Autowired
    private LawyerDao dao;


    @Override
    public void deleteLawyerById(String ids) {
        for (String id :ids.split(",")){
            Lawyer lawyer = dao.findOne(Integer.valueOf(id));
            dao.delete(lawyer);
        }
    }

    @Override
    public LawyerDto findById(Integer id) {
        Lawyer lawyer = dao.findOne(id);
        return LawyerDto.toDto(lawyer);
    }

    @Override
    public List<LawyerDto> query(QueryBean qb) {
        List<Lawyer> lawyers = dao.findByQueryBean(qb);
        List<LawyerDto> lawyerDtos = new ArrayList<LawyerDto>();
        for (Lawyer lawyer:lawyers){
            lawyerDtos.add(LawyerDto.toDto(lawyer));
        }
        return  lawyerDtos;
    }

    @Override
    public List<LawyerDto> findAll() {
        List<Lawyer> lawyers = dao.findAll();
        List<LawyerDto> lawyerDtos = new ArrayList<LawyerDto>();
        for (Lawyer lawyer:lawyers){
            lawyerDtos.add(LawyerDto.toDto(lawyer));
        }
        return  lawyerDtos;
    }

    @Override
    public LawyerDto save(LawyerDto dto) throws Exception {
        Lawyer lawyer = null;
        if (dto.getId()==null){
            lawyer=dto.toEntity();
        }else {
            lawyer = dao.findOne(dto.getId());
            if (lawyer!=null){
                dto.toEntity(lawyer);
            }
        }
        dao.save(lawyer);
        return LawyerDto.toDto(lawyer);
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
        List<LawyerDto> impDtos = ExcelUtil.parse(file.getInputStream(),LawyerDto.class);
        List<Lawyer> lawyers = new ArrayList<>();
        for (int i = 0; i < impDtos.size(); i++){
            LawyerDto lawyerDto = impDtos.get(i);
            lawyers.add(lawyerDto.toEntity());
        }
        dao.save(lawyers);
        return lawyers.size();
    }

    @Override
    public List<LawyerDto> findAllPage(QueryBean qb) {
        List<Lawyer> lawyerList = dao.findByQueryBean(qb);
        List<LawyerDto> lawyerDtos = new ArrayList<LawyerDto>();
        for (Lawyer lawyer:lawyerList){
            lawyerDtos.add(LawyerDto.toDto(lawyer));
        }

        return lawyerDtos;
    }


}
