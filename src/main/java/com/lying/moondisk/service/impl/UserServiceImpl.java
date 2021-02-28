package com.lying.moondisk.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.lying.moondisk.bean.SysUser;
import com.lying.moondisk.mapper.SysUserMapper;
import com.lying.moondisk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper userMapper;

    @Override
    public int register(String userName, String password) {
        return userMapper.insertSelective(new SysUser(null, userName, password, null));
    }

    @Override
    public SysUser login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public SysUser queryByName(String username) {
        List<SysUser> userList = userMapper.queryByUserName(username);
        if (CollectionUtil.isEmpty(userList)){
            return null;
        }
        return userList.get(0);
    }
}
