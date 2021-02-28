package com.lying.moondisk.mapper;

import com.lying.moondisk.bean.SysUser;

public interface SysUserMapper extends SysUserMapperBase{
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

}