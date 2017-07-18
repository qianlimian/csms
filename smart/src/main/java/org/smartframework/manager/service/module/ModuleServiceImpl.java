package org.smartframework.manager.service.module;

import org.smartframework.manager.dao.module.ModuleDao;
import org.smartframework.manager.dao.operate.OperateDao;
import org.smartframework.manager.dto.module.ModuleCheckBoxDto;
import org.smartframework.manager.entity.Module;
import org.smartframework.manager.entity.Operate;
import org.smartframework.manager.enumitem.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {

    private String operates = "编辑,删除";

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private OperateDao operateDao;

    /**
     * 初始化模块&&操作
     * @param obj: [{ name:"",modules:[{name:"",modules:[..]},{name:"",modules:[..]}] }]
     */
    @Override
    public void initYmlModule(Object obj) {
        createModules(obj, null);
    }

    private void createModules(Object obj, Module parent) {
        List<Map> list = (List<Map>) obj;
        for (Map map: list) {
            String[] codeName = map.get("name").toString().split(",");
            if (codeName.length > 0) {
                String code = codeName.length == 1 ? "" : codeName[0];
                String name = codeName.length == 1 ? codeName[0] : codeName[1];

                //newOrUpdate Module
                Module m = moduleDao.findTopByNameAndParent(name, parent);
                if (m == null) {
                    m = new Module();
                }
                m.setCode(code);
                m.setName(name);
                m.setParent(parent);
                moduleDao.save(m);

                //递归创建Module或Operate
                if (map.containsKey("modules")) {
                    createModules(map.get("modules"), m);
                } else {
                    createOperates(map, m);
                }
            }
        }
    }

    private void createOperates(Map map, Module module) {
        Object obj = map.get("operate");
        String arrStr = obj == null? this.operates : obj.toString();
        String[] arr = arrStr.split("\\,");
        for (String name : arr) {
            String code = Operation.getMatchByValue(name).key();

            //newOrUpdate Operate
            Operate op = operateDao.findTopByNameAndModule(name, module);
            if (op == null) {
                op = new Operate();
            }
            op.setCode(code);
            op.setName(name);
            op.setModule(module);
            operateDao.save(op);
        }
    }

    /**
     * 查询所有模块&&操作
     */
    @Override
    public List<ModuleCheckBoxDto> getModules() {
        return getChildren(null);
    }

    /**
     * 查询所有模块&&操作
     */
    private List<ModuleCheckBoxDto> getChildren(Module parent) {
        List<ModuleCheckBoxDto> items = new ArrayList<ModuleCheckBoxDto>();

        //模块
        List<Module> modules = moduleDao.findByParent(parent);
        for (Module module : modules) {
            //递归查询
            List<ModuleCheckBoxDto> children = getChildren(module);

            ModuleCheckBoxDto dto = ModuleCheckBoxDto.toDto(module);
            dto.setHasChildren(true);
            dto.setItems(children);
            items.add(dto);
        }

        //操作
        List<Operate> operates = operateDao.findByModule(parent);
        for (Operate operate : operates) {
            ModuleCheckBoxDto dto = ModuleCheckBoxDto.toDto(operate);
            dto.setHasChildren(false);
            items.add(dto);
        }

        return items;
    }
}
