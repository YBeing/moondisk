package com.lying.moondisk.bean;

public class SysUser {
    private Long userId;

    private String userName;

    private String userPassword;

    private String userSalt;

    public SysUser(Long userId, String userName, String userPassword, String userSalt) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userSalt = userSalt;
    }

    public SysUser() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserSalt() {
        return userSalt;
    }

    public void setUserSalt(String userSalt) {
        this.userSalt = userSalt == null ? null : userSalt.trim();
    }
}