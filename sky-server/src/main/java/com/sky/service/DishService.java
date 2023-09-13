package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    //菜品分页查询
    PageResult pageQueryList(DishPageQueryDTO dishPageQueryDTO);

    //添加菜品
    void save(DishDTO dishDTO);

    void deleteBatch(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    void disable(Integer status, Long id);

    List<Dish> listDish(Long categoryId);

    List<DishVO> listWithFlavor(Dish dish);
}
