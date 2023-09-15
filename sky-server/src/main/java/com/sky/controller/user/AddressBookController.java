package com.sky.controller.user;


import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api("C类端口-查询用户地址")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result addAddress(@RequestBody AddressBook addressBook){
        addressBookService.inserAddress(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询登录用户所有地址")
    public Result<List<AddressBook>>  selectAddress(){
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> addressBookList = addressBookService.slectAddressBook(addressBook);
        return  Result.success(addressBookList);
    }

    @PutMapping
    @ApiOperation("修改地址")
    public Result  updateAddress(@RequestBody AddressBook addressBook){
        addressBookService.updateAddressBook(addressBook);
        return Result.success();
    }
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result updateDefaultAddress(@RequestBody AddressBook addressBook){

        addressBookService.setDefault(addressBook);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteAddress(Long id){
        addressBookService.deleteAddressBook(id);
        return Result.success();
    }
    @GetMapping("{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> selectById(@PathVariable Long id){
      AddressBook addressBook=  addressBookService.selectAddressById(id);
        return  Result.success(addressBook);
    }
    /**
     * 查询默认地址
     */
    @GetMapping("default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault() {
        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.listAddressBook(addressBook);

        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }

        return Result.error("没有查询到默认地址");
    }
}
