package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    /*根据表名插入数据*/
    @Insert("INSERT  INTO employee(name,username,password,phone,sex,id_number,status, create_time, update_time, create_user, update_user)  values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status}, #{createTime},#{updateTime},#{createUser}, #{updateUser})")
    int insertEmployee(Employee employee);

    /*分页查询*/
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

 /*   @Insert("insert into employee(status) values(#{status})")
    void insertStatus(Integer status);*/
    //根据id修改员工信息:好处索引查询
    void update(Employee employee);

    @Select("select * from employee where id=#{id}")
    Employee getById(Long id);
}
