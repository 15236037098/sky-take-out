package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    //添加菜品或套餐
    void add(CategoryDTO categoryDTO);
    //分页查询
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
    //禁用菜品
    void disableOrStart(Integer status, Long id);
    //修改菜品信息
    void update(CategoryDTO categoryDTO);

    void deleteById(Long id);

    List<Category> list(Integer type);
}
