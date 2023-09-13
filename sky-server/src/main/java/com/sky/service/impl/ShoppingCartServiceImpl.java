package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl  implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        //只能查到自己的购物车数据
        shoppingCart.setUserId(BaseContext.getCurrentId());
        //判断当前商品是否在购物车中
      List<ShoppingCart>  shoppingCartList =shoppingCartMapper.listShopp(shoppingCart);
        if (shoppingCartList !=null && shoppingCartList.size() ==1){
            //若果已经存在，就更新数量，数量加1
            shoppingCart = shoppingCartList.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber()+1);
            shoppingCartMapper.updateNumberById(shoppingCart);
        }else {
            //若果不存在插入数据，数量就是1
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId!=null){
                //添加到购物车的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
                System.out.println("菜品总共价格============="+shoppingCart.getAmount());
            }else {
                //添加到购物车的是套餐
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
                System.out.println("套餐总共价格============="+shoppingCart.getAmount());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            System.out.println("总共价格============="+shoppingCart.getAmount());
           shoppingCartMapper.insertShopping(shoppingCart);
        }
    }

    //查看购物车

    @Override
    public List<ShoppingCart> showShoppingCart() {
        return shoppingCartMapper.listShopp(ShoppingCart.builder().userId(BaseContext.getCurrentId()).build());
    }

    //清空购物车

    @Override
    public void cleanshoppingCart() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }
}
