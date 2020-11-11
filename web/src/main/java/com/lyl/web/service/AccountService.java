package com.lyl.web.service;

import com.lyl.web.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    Account findAccountByAccountAndPassword(String account, String password);
}
