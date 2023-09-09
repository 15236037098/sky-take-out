package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public PageResult pageQueryList(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<Dish> page=dishMapper.pageQueryList(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //插入一条数据
        dishMapper.insert(dish);

        //获取菜品主键值
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null&&flavors.size()>0){
            //想口味表dish_flavor插入n条
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //批量插入
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    //批量删除

    @Override
    public void deleteBatch(List<Long> ids) {

        ids.forEach(id->{
            Dish dish=dishMapper.getById(id);
            //判断当前要删除的菜品状态是否为起售中
            if (dish.getStatus() == StatusConstant.ENABLE){
                //如果是起售中，抛出业务异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        //判断当前要删除的菜品是否被套餐关联
        List<Long> setmealIds = setmealMapper.countByCategoryId(ids);
        if (setmealIds !=null && setmealIds.size()>0){
            //如果关联了，抛出业务异常
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品表中的数据
        ids.forEach(id->{
            dishMapper.deleteById(id);
            //删除口味表中的数据
            dishFlavorMapper.deleteByDishId(id);
        });
    }
    //根据id查询菜品 接口开发

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        //查询菜单表
        Dish dish = dishMapper.getById(id);
        //查询关联的口味
        List<DishFlavor> dishFlavorList =dishFlavorMapper.getByDishId(id);

        //封装成vo
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    //修改菜品

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //修改菜品表dish，执行update操作
        dishMapper.update(dish);
        //删除当前菜品关联的口味数据，操作dish_flavor，执行delete操作
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        //插入最新的口味数据，操作dish_flavor，执行insert操作
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null&& flavors.size()>0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }

    }
}
