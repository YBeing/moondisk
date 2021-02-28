package com.lying.moondisk.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllFileModel {
    private String fileName;
    private String fileSize;
    private String modifyTime;
    private String type;
    private String pid;
    private String id;
    private String nginxViewPath;
    private List<String> nginxViewPathList;
}
