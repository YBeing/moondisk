package com.lying.moondisk.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.lying.moondisk.bean.SysFile;
import com.lying.moondisk.common.enums.FileTypeEnum;
import com.lying.moondisk.common.model.AllFileModel;
import com.lying.moondisk.common.model.DownloadFileModel;
import com.lying.moondisk.common.util.FileConvertUtil;
import com.lying.moondisk.mapper.SysFileMapper;
import com.lying.moondisk.mapper.SysUserMapper;
import com.lying.moondisk.service.FileService;
import com.lying.moondisk.util.FastDFSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<AllFileModel> getFileNameByPid(Long pid, Long uid) {
        List<SysFile> fileList = sysFileMapper.getByPid(pid, uid);
        if (CollectionUtil.isEmpty(fileList)) {
            return null;
        }
        return FileConvertUtil.toAllFileModel(fileList);
    }

    @Override
    public List<AllFileModel> queryFileNameByType(Long uid, String type) {
        List<String> TypeNameList = FileTypeEnum.getEnumListByType(type);
        List<SysFile> fileList = sysFileMapper.getByType(uid, TypeNameList);
        return  FileConvertUtil.toAllFileModel(fileList);
    }

    @Override
    public int addFileInfo(SysFile sysFile) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        sysFile.setModifyTime(currentDate);
        int insert = sysFileMapper.insert(sysFile);
        return insert;
    }

    @Override
    @Transactional
    /**
     * filepath: group1/M00/00/00/wKgDMV9o4B-AHEQRAAcppDDlZRk814.png
     */
    public Boolean deleteFiles(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        List<String> serverPathList = sysFileMapper.getFilesByIds(ids);
        //如果传入的file id和查询到的结果集数量不一致，则代表还有正在上传的文件未完成
        if (CollectionUtil.isEmpty(serverPathList) || ids.size() != serverPathList.size()){
            return false;
        }
        fastDFSClientUtil.delFileList(serverPathList);
        sysFileMapper.deleteFiles(ids);
        return true;


    }

    @Override
    @Transactional
    /**
     * filepath: group1/M00/00/00/wKgDMV9o4B-AHEQRAAcppDDlZRk814.png
     */
    public void deleteFilesByPid(List<String> pids) {
        List<String> serverPathList = sysFileMapper.getFilesByPids(pids);
        fastDFSClientUtil.delFileList(serverPathList);
        sysFileMapper.deleteFiles(pids);


    }

    @Override
    public DownloadFileModel downloadFile(String id) {
        if (StrUtil.isEmpty(id)) {
            throw new RuntimeException("id不能为空");
        }

        SysFile file = sysFileMapper.selectByPrimaryKey(Integer.parseInt(id));

        DownloadFileModel downloadFileModels = new DownloadFileModel();

        StorePath storePath = StorePath.parseFromUrl(file.getServerPath());
        InputStream inputStream = fastDFSClientUtil.download(storePath.getGroup(), storePath.getPath());

        downloadFileModels.setFileName(file.getFileName());
        downloadFileModels.setInputStream(inputStream);
        return downloadFileModels;
    }

    @Override
    public List<AllFileModel> getImageGroupByDate(Long uid, String fileType) {
        if (uid == null || StrUtil.isEmpty(fileType)){
            return null;
        }
        List<String> typeNameList = FileTypeEnum.getEnumListByType("image");
        List<Map> fileList = sysFileMapper.getImageGroupByDate(uid, typeNameList);
        return fileList.stream().map(FileConvertUtil::toImageTimeLineModel).collect(Collectors.toList());
    }
}
