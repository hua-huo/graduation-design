package com.example.topic.controller;

import com.example.topic.model.Department;
import com.example.topic.service.DepartmentService;
import com.example.topic.utils.NumberTool;
import com.example.topic.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019\4\24 0024.
 */
@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/frontend/query")
    public Map queryDepartment() {
        Map map = new HashMap<>();
        List<Department> list = departmentService.getDepartment();
        map.put("data", list);
        map.put("message", "SUCCESS");
        return map;
    }

    @PostMapping("/frontend/insert")
    public Map insertDepartment(@RequestBody Map parmmap) {
        Map map = new HashMap<>();
        String name = StringUtils.transitionString(parmmap,"name","");
        String description = StringUtils.transitionString(parmmap,"description","");
        departmentService.addDepartment(name, description);
        map.put("data", null);
        map.put("message", "SUCCESS");
        return map;
    }
    @RequestMapping("/frontend/update")
    public Map updateDepartment(@RequestBody Map parmmap){
        Map map = new HashMap<>();
        String name = StringUtils.transitionString(parmmap,"name","");
        String description = StringUtils.transitionString(parmmap,"description","");
        int department_id = NumberTool.transitionInteger(parmmap, "department_id", 0);
        departmentService.updateDepartment(department_id, name, description);
        map.put("data", null);
        map.put("message", "SUCCESS");
        return map;
    }
    @RequestMapping("/frontend/delete")
    public Map deleteDepartment(@RequestBody Map parmmap){
        Map map = new HashMap<>();
        int department_id =NumberTool.transitionInteger(parmmap, "department_id", 0);
        departmentService.delDepartmentById(department_id);
        map.put("data", null);
        map.put("message", "SUCCESS");
        return map;
    }

    /**
     * 根据name查找部门，判断部门名称是否存在
     * @param parmmap
     * @return
     */
    @RequestMapping("/frontend/checkdName")
    public Map checkdName(@RequestBody Map parmmap){
        Map map = new HashMap<>();
        String name = StringUtils.transitionString(parmmap, "name", "");
        int department_id =NumberTool.transitionInteger(parmmap, "department_id", 0);
        Department department = departmentService.getDepartmentByName(name);
        //这里有两个判断：
        if(department_id == 0){
            //1: department_id = 0 说明是新增页面过来验证，只需要验证department 是否为null
            map.put("data",department == null);
        }else{
            //2: department_id > 0 带了ID过来说明是修改页面过来验证，需要判断department == null || department.getDepartment_id()==department_id
            map.put("data",department == null || department.getDepartment_id()==department_id);
        }
        map.put("message", "SUCCESS");
        return map;
    }
}
