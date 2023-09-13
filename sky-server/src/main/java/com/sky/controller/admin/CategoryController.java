package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("导入分类模块功能")
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("新增菜品分类")
    public Result add(@RequestBody CategoryDTO categoryDTO){
        categoryService.add(categoryDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("品类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.error("分页查询：{}" ,categoryPageQueryDTO);
        PageResult pageResult =categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("禁用菜品")
    public Result<String> disableCategory(@PathVariable Integer status,Long id){
        log.info("禁用员工：{}{}",status,id);
        categoryService.disableOrStart(status,id);
        return Result.success();
    }
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        log.info("编辑员工：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result<String> deleteById(Long id){
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }
 /*   *
  * 根据类型查询分类
  * @param type
  * @return*/
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.listCategoryService(type);
        return Result.success(list);
    }

}
