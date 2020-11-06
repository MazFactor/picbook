package com.lyl.web.service.impl;

import com.lyl.web.entity.Account;
import com.lyl.web.mapper.AccountMapper;
import com.lyl.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findAccountByAccountAndPassword(String account, String password) {
        return accountMapper.findAccountByAccountAndPassword(account, password);
    }
}
