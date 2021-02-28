package com.lying.moondisk.service;

import com.lying.moondisk.bean.SysFile;
import com.lying.moondisk.common.model.AllFileModel;
import com.lying.moondisk.common.model.DownloadFileModel;

import java.util.List;

public interface FileService {
    /**
     * 通过id查询文件信息
     */
    List<AllFileModel> getFileNameByPid(Long pid, Long uid);

    /**
     * 通过文件类型查询文件信息
     */
    List<AllFileModel> queryFileNameByType(Long uid, String type);

    /**
     * 添加文件信息
     */
    int addFileInfo(SysFile sysFile);

    /**
     * 删除文件
     */
    void deleteFiles(List<String> ids);

    /**
     * 根据文件夹删除下面的子文件夹
     */

    void deleteFilesByPid(List<String> pids);

    /**
     * 单个下载文件
     */
    DownloadFileModel downloadFile(String id);

    /**
     * 根据时间轴查询图片
     */
    List<AllFileModel> getImageGroupByDate(Long uid, String fileType);

}
