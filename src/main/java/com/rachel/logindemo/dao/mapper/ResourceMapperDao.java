package com.rachel.logindemo.dao.mapper;


import com.rachel.logindemo.dao.entity.Resources;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapperDao {
    /**
     * @Author dw
     * @Description 获取所有的资源
     * @Date 2020/4/15 11:16
     * @Param
     * @return
     */
    public List<Resources> getAllResources();
}
