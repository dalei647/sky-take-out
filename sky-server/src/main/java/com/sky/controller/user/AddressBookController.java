package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 添加地址
     * @param addressBook
     * @return
     */
    @PostMapping()
    public Result add(@RequestBody AddressBook addressBook){
        log.info("新增地址：{}", addressBook);
        addressBookService.add(addressBook);
        return Result.success();
    }

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(){
        log.info("查询当前登录用户所有地址");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    /**
     * 查询当前登录用户的默认地址
     * @return
     */
    @GetMapping("/default")
    public Result<AddressBook> getDefaultAddress(){
        log.info("查询当前登录用户默认地址");
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        List<AddressBook> list = addressBookService.list(addressBook);
        if (list != null && list.size() > 0) {
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AddressBook> selecctById(@PathVariable Long id){
        log.info("查询id为{}的地址", id);
        AddressBook address = addressBookService.selectById(id);
        return Result.success(address);
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook){
        log.info("设置id为{}的为默认地址", addressBook.getId());
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody AddressBook addressBook){
        log.info("修改地址：{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @DeleteMapping()
    public Result delete(Long id){
        log.info("删除地址：{}", id);
        addressBookService.delete(id);
        return Result.success();
    }
}
