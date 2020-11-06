package com.lyl.web.service;

import com.lyl.web.entity.Account;

public interface AccountService {
    Account findAccountByAccountAndPassword(String account, String password);
}
