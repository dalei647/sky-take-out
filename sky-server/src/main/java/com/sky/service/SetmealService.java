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
    void insert(SetmealDTO setmealDTO);

    PageResult getPage(SetmealPageQueryDTO setmealPageQueryDTO);

    SetmealVO getById(Long id);

    void update(SetmealDTO setmealDTO);

    void delete(List<Long> ids);

    void startOrStop(Integer status, Long id);

    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> selectDishItemById(Long id);
}
