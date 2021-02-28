package com.lying.moondisk.mapper;

import com.lying.moondisk.bean.SysDir;

public interface SysDirMapper extends SysDirMapperBase{
    int deleteByPrimaryKey(Long id);

    int insert(SysDir record);

    int insertSelective(SysDir record);

    SysDir selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDir record);

    int updateByPrimaryKey(SysDir record);
}