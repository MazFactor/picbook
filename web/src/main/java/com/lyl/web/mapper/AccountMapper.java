package com.lyl.web.mapper;

import com.lyl.web.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountMapper {
    Account findAccountByAccountAndPassword(String account, String password);
}
