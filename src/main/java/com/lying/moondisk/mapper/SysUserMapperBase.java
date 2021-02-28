package com.lying.moondisk.mapper;

import com.lying.moondisk.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapperBase{
    /**
     * 登陆功能
     * @param userName
     * @param password
     * @return
     */
    SysUser login(@Param("userName") String userName, @Param("password") String password);

    /**
     * 根据用户名查询信息
     * @param username
     * @return
     */
    List<SysUser> queryByUserName(String username);
}