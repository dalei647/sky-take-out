package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;

public interface SetmealService {
    void insert(SetmealDTO setmealDTO);

    PageResult getPage(SetmealPageQueryDTO setmealPageQueryDTO);

}
