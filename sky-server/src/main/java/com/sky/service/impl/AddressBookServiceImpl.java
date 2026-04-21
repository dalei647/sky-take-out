package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;


    /**
     * 添加地址
     * @param addressBook
     */
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        // 设置为非默认地址
        addressBook.setIsDefault(0);
        addressBookMapper.add(addressBook);
    }

    /**
     * 查询当前登录用户的所有地址
     * @return
     */
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    public AddressBook selectById(Long id) {
        return addressBookMapper.selectById(id);
    }

    /**
     * 设置默认地址
     * @param addressBook
     */
    public void setDefault(AddressBook addressBook) {
        // 先将当前登录用户的所有地址设置为非默认地址
        AddressBook addressBook1 = new AddressBook();
        addressBook1.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookMapper.list(addressBook1);
        list.forEach(address -> {
            address.setIsDefault(0);
            addressBookMapper.update(address);
        });
        // 再将当前地址设置为默认地址
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }

    /**
     * 修改地址
     * @param addressBook
     */
    public void update(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id删除地址
     * @param id
     */
    public void delete(Long id) {
        addressBookMapper.delete(id);
    }
}
