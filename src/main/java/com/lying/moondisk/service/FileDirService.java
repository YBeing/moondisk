package com.lying.moondisk.service;

import com.lying.moondisk.common.model.AllFileModel;

import java.util.List;

public interface FileDirService {
    /**
     * 创建文件夹
     */
    int createDir(String dirname, Long pid,Long uid);

    /**
     * 查询文件夹信息
     */
    List<AllFileModel> queryDirNames(Long pid, Long uid);

    /**
     * 删除当前文件夹及其子文件夹
     */
    void deleteFileDir(List<String> ids);
}
