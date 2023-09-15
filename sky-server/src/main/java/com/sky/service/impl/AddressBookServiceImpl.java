package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;


    //新增地址
    @Override
    public void inserAddress(AddressBook addressBook) {
       addressBook.setUserId(BaseContext.getCurrentId());
       addressBook.setIsDefault(0);
        addressBookMapper.insertAddressBook(addressBook);
    }

    //查询用户所有地址

    @Override
    public List<AddressBook> slectAddressBook(AddressBook addressBook) {
        List<AddressBook>  addressBookList=addressBookMapper.selectAddress(addressBook);
        return addressBookList;
    }

    //更新地址
    @Override
    public void updateAddressBook(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }
    //设置默认地址
    @Override
    @Transactional
    public void setDefault(AddressBook addressBook) {
        //将当前用户的所有地址修改为非默认地址 update address_book set is_default = ? where user_id = ?
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefaultByUserId(addressBook);
        //2、将当前地址改为默认地址 update address_book set is_default = ? where id = ?
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
    //删除默认地址
    @Override
    public void deleteAddressBook(Long id) {
        addressBookMapper.delete(id);
    }
    //根据id进行查询

    @Override
    public AddressBook selectAddressById(Long id) {
        AddressBook addressBook=addressBookMapper.selectById(id);

        return  addressBook;
    }
    @Override
    public List<AddressBook> listAddressBook(AddressBook addressBook) {
        return addressBookMapper.selectAddress(addressBook);
    }
}
