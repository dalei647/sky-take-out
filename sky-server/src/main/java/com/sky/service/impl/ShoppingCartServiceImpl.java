package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.interceptor.JwtTokenUserInterceptor;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.properties.JwtProperties;
import com.sky.service.ShoppingCartService;
import com.sky.utils.JwtUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jdk.jpackage.internal.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        // 判断当前购物车是否已经存在
        List<ShoppingCart> list = shoppingCartMapper.select(shoppingCart);
        if(list != null && list.size() > 0){
            // 已经存在，数量加1
            list.forEach(item -> {
                item.setNumber(item.getNumber() + 1);
                // 更新
                shoppingCartMapper.update(item);
            });
        }else{
            // 不存在，创建购物车
            if(shoppingCart.getDishId() != null){
                // 如果添加菜品，查询菜品信息并拷贝到购物车
                Dish dish = dishMapper.getById(shoppingCart.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                // 如果添加套餐，查询套餐信息并拷贝到购物车
                Setmeal setmeal = setmealMapper.getById(shoppingCart.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            // 添加购物车
            shoppingCartMapper.add(shoppingCart);
        }

    }

    /**
     * 查看购物车
     * @return
     */
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(userId).build();
        List<ShoppingCart> list = shoppingCartMapper.select(shoppingCart);
        return list;
    }

    @Override
    public void sub(ShoppingCart shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        // 查询购物车数据
        List<ShoppingCart> list = shoppingCartMapper.select(shoppingCart);
        ShoppingCart item = list.get(0);
        if(item.getNumber() > 1){
            // 数量大于1则数量减1
            item.setNumber(item.getNumber() - 1);
            shoppingCartMapper.update(item);
        }else{
            // 数量为1则删除该数据
            shoppingCartMapper.deleteById(item.getId());
        }
    }

    /**
     * 清空购物车
     */
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.cleanByUserId(userId);
    }
}
