package com.lying.moondisk.bean;

public class SysDir {
    private Long id;

    private String dirName;

    private Long pid;

    private Long uid;

    public SysDir(Long id, String dirName, Long pid, Long uid) {
        this.id = id;
        this.dirName = dirName;
        this.pid = pid;
        this.uid = uid;
    }

    public SysDir() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName == null ? null : dirName.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}