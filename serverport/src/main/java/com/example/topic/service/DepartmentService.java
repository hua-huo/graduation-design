package com.example.topic.service;
import com.example.topic.dao.DepartmentDao;
import com.example.topic.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


/**
 * User: hao.man
 * Created by Administrator on 2019\4\24 0024.
 */
@Service //声明成一个spring bean
public class DepartmentService {
    @Autowired //连接到DepartmentDao Bean
    private DepartmentDao departmentDao;

    /**
     * 查询所有部门
     * @return
     */
    public List<Department> getDepartment(){
        return departmentDao.get();
    }

    /**
     * 添加部门
     * @param name
     * @param description
     * @return
     */
    public String addDepartment(String name,String description){
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        departmentDao.insert(department);
        return "SUCCESS";
    }

    /**
     * 根据部门ID删除部门
     * @param department_id
     * @return
     */
    public String delDepartmentById(int department_id){
        departmentDao.delete(department_id);
        return "SUCCESS";
    }

    /**
     * 修改部门信息
     * @param getFindById
     * @param name
     * @param description
     * @return
     */
    public String updateDepartment(int getFindById, String name,String description){
        List<Department> list = departmentDao.getFindById(getFindById);
        if(list != null && list.size()>0){
            Department department = list.get(0);
            department.setName(name);
            department.setDescription(description);
            departmentDao.update(department);
            return "SUCCESS";
        }
        return "ERROR";
    }

    /**
     * 根据部门name查找部门
     * @param name
     * @return
     */
    public Department getDepartmentByName(String name){
       List<Department> list = departmentDao.getDepartmentByName(name);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
