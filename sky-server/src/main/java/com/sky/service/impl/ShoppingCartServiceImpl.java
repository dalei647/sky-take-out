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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        List<ShoppingCart> list = shoppingCartMapper.selectByUserId(userId);
        return list;
    }
}
