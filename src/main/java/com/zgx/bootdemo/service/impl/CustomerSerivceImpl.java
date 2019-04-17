package com.zgx.bootdemo.service.impl;

import com.zgx.bootdemo.dao.CustomerDao;
import com.zgx.bootdemo.entity.Customer;
import com.zgx.bootdemo.service.CustomerSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "customerSerivce")
public class CustomerSerivceImpl implements CustomerSerivce {

    @Autowired
    private CustomerDao customerDao;


    @Override
    @Transactional
    public Customer getCustomer(String custCode) throws Exception {
        return customerDao.read(custCode);
    }

    @Override
    @Transactional
    public List listCustomer(Customer customer) {
        return customerDao.list(customer);
    }


    @Override
    @Transactional
    public Integer countCustomer(Customer customer) {
        return customerDao.count(customer);
    }


    @Override
    @Transactional
    public Boolean saveCustomer(Customer customer) {
        boolean flag = true;
        try {
            customerDao.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    @Override
    @Transactional
    public void removeCustomer(String customerId) throws Exception {
        customerDao.remove(customerId);
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }
}
