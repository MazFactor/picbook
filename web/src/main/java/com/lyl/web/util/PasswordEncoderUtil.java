package com.lyl.web.util;

import com.lyl.common.util.StringUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil implements PasswordEncoder {
    @Override //不清楚除了在下面方法用到还有什么用处
    public String encode(CharSequence rawPassword) {
        return StringUtil.StringToMD5(rawPassword.toString());
    }

    //判断密码是否匹配
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(this.encode(rawPassword));
    }
}
