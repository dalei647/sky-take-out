package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询对应的套餐id
     * @param ids
     * @return
     */
    List<Integer> getSetmealIdsByDishIds(List<Long> ids);

    /**
     * 批量插入套餐和菜品的关联数据
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
