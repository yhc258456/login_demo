package com.rachel.logindemo.dao.mapper;

import com.rachel.logindemo.dao.entity.Role;
import com.rachel.logindemo.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapperDao {

    User loadUserByUsername(String userName);

    List<Role> getUserRolesByUid(Integer id);

}
