package com.lying.moondisk.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.lying.moondisk.bean.SysDir;
import com.lying.moondisk.common.model.AllFileModel;
import com.lying.moondisk.common.util.FileConvertUtil;
import com.lying.moondisk.mapper.SysDirMapper;
import com.lying.moondisk.service.FileDirService;
import com.lying.moondisk.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class FileDirServiceImpl implements FileDirService {
    @Autowired
    private SysDirMapper sysDirMapper;
    @Autowired
    private FileService fileService;

    @Override
    public int createDir(String dirname,Long pid,Long uid) {
        SysDir sysDir =new SysDir();
        sysDir.setDirName(dirname);
        sysDir.setPid(pid);
        sysDir.setUid(uid);
        int insert = sysDirMapper.insert(sysDir);
        return insert;
    }

    @Override
    public List<AllFileModel> queryDirNames(Long pid , Long uid) {
        List<SysDir> sysDirList = sysDirMapper.getByPid(pid,uid);
        if (CollectionUtil.isEmpty(sysDirList)){
            return null;
        }
        List<AllFileModel> modelList = FileConvertUtil.toAllFileDirModel(sysDirList);

        return modelList;
    }

    @Override
    @Transactional
    public void deleteFileDir(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)){
            return;
        }
        // 传来的如果是文件夹的话，那么我们要考虑删除时也要删除他的子文件夹以及下面的文件
        // 所以我们可以先把所有需要删除的文件夹的id给查出来，然后再用他的id关联到文件表的dir_pid
        // 这样就能把文件也给删除了
        List<String> finalIdList= new ArrayList<>();
        if (CollectionUtil.isNotEmpty(ids)){
            finalIdList.addAll(ids);
        }

        while (true){
            List<String> loopIdList = null;

            loopIdList = sysDirMapper.getByidList(ids);
            if (CollectionUtil.isNotEmpty(loopIdList)){
                finalIdList.addAll(loopIdList);
                ids = loopIdList;
            }else{
                break;
            }
        }

        sysDirMapper.deleteFileDirs(finalIdList);
        fileService.deleteFilesByPid(finalIdList);
    }
}
