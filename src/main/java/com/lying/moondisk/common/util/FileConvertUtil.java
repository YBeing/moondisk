package com.lying.moondisk.common.util;

import cn.hutool.core.util.StrUtil;
import com.lying.moondisk.bean.SysDir;
import com.lying.moondisk.bean.SysFile;
import com.lying.moondisk.common.model.AllFileModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileConvertUtil {
    public static SysFile toDal(MultipartFile file, Long uid, Long dirPid){
        if (file == null || uid == null || dirPid == null){
            return null;
        }
        SysFile sysFile = new SysFile();
        sysFile.setFileName(file.getOriginalFilename());
        sysFile.setFileSize(file.getSize());
        sysFile.setFileType(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1));
        sysFile.setUid(uid);
        sysFile.setDirPid(dirPid);
        return sysFile;
    }

    public static List<AllFileModel> toAllFileDirModel(List<SysDir> list){
        List<AllFileModel> allFileModelList =new ArrayList<>();
        list.forEach(sysDir -> {
            AllFileModel allFileModel = new AllFileModel();
            allFileModel.setFileName(sysDir.getDirName());
            allFileModel.setType("dir");
            //这个pid是为了给前台创建目录以及进入目录查看其子目录使用的
            allFileModel.setPid(sysDir.getId().toString());
            //这个id是为了我们删除和下载使用的
            allFileModel.setId(sysDir.getId().toString());
            allFileModelList.add(allFileModel);
        });

        return allFileModelList;

    }

    public static List<AllFileModel> toAllFileModel(List<SysFile> list){
        List<AllFileModel> allFileModelList =new ArrayList<>();
        list.forEach(sysFile -> {
            AllFileModel allFileModel = new AllFileModel();
            allFileModel.setFileName(sysFile.getFileName());
            allFileModel.setFileSize(sysFile.getFileSize().toString());
            allFileModel.setModifyTime(sysFile.getModifyTime());
            allFileModel.setType(sysFile.getFileType());
            allFileModel.setPid("none");
            allFileModel.setId(sysFile.getId().toString());
            allFileModel.setNginxViewPath(sysFile.getNginxViewPath());
            allFileModelList.add(allFileModel);

        });

        return allFileModelList;

    }

    public static AllFileModel toImageTimeLineModel(Map sysFile){
        AllFileModel fileModel = new AllFileModel();
        String nginxViewPath = sysFile.get("viewPathList").toString();
        if (StrUtil.isEmpty(nginxViewPath)){
            return null;
        }
        String[] split = nginxViewPath.split(",");
        if (split == null){
            return null;
        }
        List<String> nginxViewPathList = Arrays.asList(split);

        return fileModel.builder()
                .modifyTime(sysFile.get("modifyTime").toString())
                .nginxViewPathList(nginxViewPathList)
                .build();

    }

}
