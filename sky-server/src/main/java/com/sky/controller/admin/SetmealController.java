package com.sky.controller.admin;


import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api("套餐管理接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping ("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> queruSetmeal( SetmealPageQueryDTO setmealPageQueryDTO){
        log.error("分页查询：{}" ,setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("根据id进行删除")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete( @RequestParam List<Long> ids){
        log.info("删除套餐：{}", ids);
        setmealService.delete(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("停售起售")
    @CacheEvict
    public Result<String> disable(@PathVariable Integer status,Long id){
        log.info("停售起售：{}", status,id);
        setmealService.disable(status,id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict
    public Result update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐：{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品和关联的口味数据")
    public Result<SetmealVO> getById(@PathVariable Long id){
        return Result.success(setmealService.getByIdWithDish(id));
    }
}
