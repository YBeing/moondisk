package com.lying.moondisk.mapper;

import com.lying.moondisk.bean.SysDir;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDirMapperBase {
    /**
     * 根据父级ID查询文件夹信息
     * @param pid
     * @param uid
     * @return
     */
    List<SysDir> getByPid(@Param("pid") Long pid, @Param("uid") Long uid);

    /**
     * 根据ID集合查询文件夹名称
     * @param list
     * @return
     */
    List<String> getByidList(List<String> list);

    /**
     * 根据ID集合删除文件夹
     * @param ids
     */
    void deleteFileDirs(List<String> ids);
}