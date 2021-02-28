package com.lying.moondisk.bean;

public class SysFile {
    private Integer id;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private String serverPath;

    private String nginxViewPath;

    private String deleteStatus;

    private String groupName;

    private Long dirPid;

    private Long uid;

    private String modifyTime;

    public SysFile(Integer id, String fileName, String fileType, Long fileSize, String serverPath, String nginxViewPath, String deleteStatus, String groupName, Long dirPid, Long uid, String modifyTime) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.serverPath = serverPath;
        this.nginxViewPath = nginxViewPath;
        this.deleteStatus = deleteStatus;
        this.groupName = groupName;
        this.dirPid = dirPid;
        this.uid = uid;
        this.modifyTime = modifyTime;
    }

    public SysFile() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath == null ? null : serverPath.trim();
    }

    public String getNginxViewPath() {
        return nginxViewPath;
    }

    public void setNginxViewPath(String nginxViewPath) {
        this.nginxViewPath = nginxViewPath == null ? null : nginxViewPath.trim();
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus == null ? null : deleteStatus.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Long getDirPid() {
        return dirPid;
    }

    public void setDirPid(Long dirPid) {
        this.dirPid = dirPid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime == null ? null : modifyTime.trim();
    }
}