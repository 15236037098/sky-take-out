package com.sky.service;


import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;


public interface SetmealService {
    void addSetmeal(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void delete(List<Long> ids);

    void disable(Integer status, Long id);

    void update(SetmealDTO setmealDTO);
    /**
     * 根据id查询套餐和关联的菜品数据
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);
//根据id查询
    List<Setmeal> listSetmealService(Setmeal setmeal);


    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
