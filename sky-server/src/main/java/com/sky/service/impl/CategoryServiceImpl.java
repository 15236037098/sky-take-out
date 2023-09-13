package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;
    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        //前端数据赋值给后端
        BeanUtils.copyProperties(categoryDTO,category);
        //创建时间
        category.setCreateTime(LocalDateTime.now());
        //创建人
        category.setCreateUser(BaseContext.getCurrentId());
        //更新时间
        category.setUpdateTime(LocalDateTime.now());
        //更新者
        category.setUpdateUser(BaseContext.getCurrentId());
        //类型
        category.setStatus(StatusConstant.ENABLE);

        category.setType(categoryDTO.getType());
        //排序
        category.setSort(categoryDTO.getSort());
        //名字
        category.setName(categoryDTO.getName());

        categoryMapper.add(category);
    }

    /*菜单分页查询*/
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category>  page=categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //禁用
    @Override
    public void disableOrStart(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.update(category);

    }
    //菜品修改
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setSort(categoryDTO.getSort());
        category.setName(categoryDTO.getName());
        categoryMapper.update(category);
    }
    //根据id进行删除
    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public List<Category> listCategoryService(Integer type) {
        return categoryMapper.listCategoryMapper(type);
    }
}
