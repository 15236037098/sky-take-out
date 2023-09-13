package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.autoAnotication.AutoFile;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @AutoFile(OperationType.INSERT)
    @Insert("insert into category(type,name,sort,status,create_time,update_time,create_user,update_user) values(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void add(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFile(OperationType.UPDATE)
    void update(Category category);

    @Delete("delete from category where id=#{id}")
    void deleteById(Long id);

    List<Category> listCategoryMapper(Integer type);
}
