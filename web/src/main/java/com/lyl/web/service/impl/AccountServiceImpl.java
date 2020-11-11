package com.lyl.web.service.impl;

import com.lyl.web.entity.Account;
import com.lyl.web.mapper.AccountMapper;
import com.lyl.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findAccountByAccountAndPassword(String account, String password) {
        return accountMapper.findAccountByAccountAndPassword(account, password);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountMapper.findAccountByName(userName);
        if (account == null) {
            //抛出错误，用户不存在
            throw new UsernameNotFoundException("用户名 " + userName + "不存在");
        }
        return account;
    }
}
