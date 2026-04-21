package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 添加地址
     * @param addressBook
     */
    @Insert("insert into address_book (user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default) " +
            "VALUES (#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void add(AddressBook addressBook);


    /**
     * 条件查询
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);


    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook selectById(Long id);

    /**
     * 修改地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 根据id删除地址
     * @param id
     */
    @Delete("delete from address_book where id = #{id}")
    void delete(Long id);
}
