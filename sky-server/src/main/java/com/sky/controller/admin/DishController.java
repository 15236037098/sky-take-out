package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("菜品管理")
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageList(DishPageQueryDTO dishPageQueryDTO){
        log.error("分页查询：{}" ,dishPageQueryDTO);
        PageResult pageResult =   dishService.pageQueryList(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping()
    @ApiOperation("新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}", dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids){
        log.info("菜品批量删除：{}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品和关联的口味数据")
    public Result<DishVO> getById(@PathVariable Long id){
        return Result.success(dishService.getByIdWithFlavor(id));
    }
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("起售，禁售")
    public Result<String>  disableOrSrart(@PathVariable Integer status ,Long id){
        log.info("修改禁售起售：{}", status,id);
        dishService.disable(status,id);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("根据id查询菜单")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.listDish(categoryId);
        return Result.success(list);
    }
}
