package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.autoAnotication.AutoFile;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    Page<Dish> pageQueryList(DishPageQueryDTO dishPageQueryDTO);

    @AutoFile(OperationType.INSERT)

    void insert(Dish dish);

    @Select("select status,id from dish where id=#{id}")
    Dish getById(Long id);

    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    @AutoFile(OperationType.UPDATE)
    void update(Dish dish);
}
