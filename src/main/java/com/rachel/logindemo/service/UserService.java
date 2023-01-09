package com.rachel.logindemo.service;

import com.rachel.logindemo.dao.entity.Role;
import com.rachel.logindemo.dao.entity.User;
import com.rachel.logindemo.dao.mapper.UserMapperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapperDao userMapperDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapperDao.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("账户不存在!");
        }
        // 我的数据库用户密码没加密，这里手动设置
        String encodePassword = passwordEncoder.encode(user.getPassword());
        System.out.println("加密后的密码：" + encodePassword);
        user.setPassword(encodePassword);
        List<Role> userRoles = userMapperDao.getUserRolesByUid(user.getId());
        user.setUserRoles(userRoles);
        return user;
    }
}
