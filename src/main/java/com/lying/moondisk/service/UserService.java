package com.lying.moondisk.service;


import com.lying.moondisk.bean.SysUser;

public interface UserService {
    /**
     * 注册
     * @param userName
     * @param password
     */
    int register(String userName, String password);

    /**
     * 登陆
     * @param userName
     * @param password
     */
    SysUser login(String userName, String password);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SysUser queryByName(String username);
}
