package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void inserAddress(AddressBook addressBook);

    List<AddressBook> slectAddressBook(AddressBook addressBook);

    void updateAddressBook(AddressBook addressBook);

    //设置默认地址
    void setDefault(AddressBook addressBook);

    void deleteAddressBook(Long id);

    AddressBook selectAddressById(Long id);

    List<AddressBook> listAddressBook(AddressBook addressBook);
}
