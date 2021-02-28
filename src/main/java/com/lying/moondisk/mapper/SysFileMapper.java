package com.lying.moondisk.mapper;

import com.lying.moondisk.bean.SysFile;

public interface SysFileMapper extends SysFileMapperBase{
    int deleteByPrimaryKey(Integer id);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    SysFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);
}