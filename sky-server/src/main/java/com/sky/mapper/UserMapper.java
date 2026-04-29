package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openId
     * @return
     */
    @Select("select * from user where openid = #{openId}")
    User getByOpenid(String openId);

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * 插入数据
     * @param user
     */
    void insert(User user);


    /**
     * 根据时间获取用户数量
     * @param beginTime
     * @param endTime
     * @return
     */
    Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime);
}
