package com.lying.moondisk.controller;

import cn.hutool.core.util.StrUtil;
import com.lying.moondisk.bean.SysUser;
import com.lying.moondisk.common.model.CommonResult;
import com.lying.moondisk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @ResponseBody
    @GetMapping("/login")
    public CommonResult login(String username, String password){
        if (StrUtil.isEmpty(username) && StrUtil.isEmpty(password)){
            return new CommonResult(false, "账号或密码不能为空！!");
        }
        SysUser user = userService.login(username, password);
        return new CommonResult(user != null, user != null ? "登陆成功!":"账号或密码错误!",user != null ? user.getUserName() : null);
    }
    /**
     * 用户注册
     */
    @ResponseBody
    @GetMapping("/register")
    public CommonResult register(String username, String password){

        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)){
            return new CommonResult(false, "账号或密码不能为空！");
        }
        //校验用户名是否重复
        SysUser sysUser = userService.queryByName(username);
        if (sysUser != null){
            return new CommonResult(false, "该用户名已被注册！");
        }
        int res = userService.register(username, password);
        return new CommonResult(res > 0, "注册成功！");
    }
}
