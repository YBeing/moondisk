package com.lying.moondisk.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lying.moondisk.bean.SysFile;
import com.lying.moondisk.bean.SysUser;
import com.lying.moondisk.common.model.AllFileModel;
import com.lying.moondisk.common.model.CommonResult;
import com.lying.moondisk.common.model.DownloadFileModel;
import com.lying.moondisk.common.util.FileConvertUtil;
import com.lying.moondisk.mapper.SysFileMapper;
import com.lying.moondisk.service.FileDirService;
import com.lying.moondisk.service.FileService;
import com.lying.moondisk.service.UserService;
import com.lying.moondisk.util.FastDFSClientUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FastDFSClientUtil       fastDFSClientUtil;
    @Autowired
    private FileDirService          fileDirService;
    @Autowired
    private FileService             fileService;
    @Autowired
    private UserService             userService;
    @Autowired
    private SysFileMapper           sysFileMapper;

    private static ExecutorService executorService = new ThreadPoolExecutor(2,10,
            30000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public CommonResult uploadFile(MultipartFile file, String username, Long dirPid) throws IOException {
        LOGGER.info("file upload start, filename is {}, username is {}", file.getOriginalFilename(), username);
        SysUser user = userService.queryByName(username);
        if (user == null){
            LOGGER.error("user not found !!!");
            return new CommonResult(false, "用户信息不存在");
        }

        try {
            //上传文件返回路径信息
            LOGGER.info("file upload finished");

            //把已有的信息封装成我们的一个文件表的节点信息
            SysFile sysFile = FileConvertUtil.toDal(file, user.getUserId(), dirPid);

            //保存到文件表
            fileService.addFileInfo(sysFile);
            executorService.submit(()->{
                //由于是并发执行，不想阻塞最开始的插入操作，是前台能够更快响应，所以先执行基础数据的插入，
                //待文件上传完成后，再把相关的信息更新到响应的数据里面去
                //todo:还应该加入重试机制，避免文件过大导致的服务器压力过大，从而引起的上传失败，所以这里应该在抛出异常时进行重试
                Map pathInfoMap = null;
                try {
                    pathInfoMap = fastDFSClientUtil.uploadFile(file);
                    sysFile.setServerPath(pathInfoMap.get("serverPath").toString());
                    sysFile.setNginxViewPath(pathInfoMap.get("nginxViewPath").toString());
                    sysFile.setGroupName(pathInfoMap.get("groupName").toString());
                    sysFileMapper.updateByPrimaryKeySelective(sysFile);
                } catch (IOException e) {
                    LOGGER.error("请检查文件类型或者文件大小是否符合要求！");
                }
            });
        } catch (Exception e) {
            LOGGER.error("upload failed, message is {}", e.getMessage());
        }

        //重新查询新增后的页面信息，用于前台展示
        return new CommonResult(true, "上传成功", reloadFileInfo(dirPid, username));
    }

    /**
     * 文件下载
     */
    @GetMapping("/downloadFile")
    public void downloadFile(String id, HttpServletRequest request, HttpServletResponse response)  {
        InputStream inputStream=null;

        DownloadFileModel downloadFileModel= fileService.downloadFile(id);
        String fileName = downloadFileModel.getFileName();
        inputStream = downloadFileModel.getInputStream();
        String contentType = request.getServletContext().getMimeType(fileName);
        String contentDisposition = "attachment;filename=" + fileName;

        // 设置头
        response.setHeader("Content-Type",contentType);
        response.setHeader("Content-Disposition",contentDisposition);

        try {
            // 获取绑定了客户端的流
            ServletOutputStream output = response.getOutputStream();
            // 把输入流中的数据写入到输出流中
            IOUtils.copy(inputStream,output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream !=null ){
                try {
                    inputStream.close();

                } catch (IOException e) {

                }

            }
        }
    }

    /**
     * 创建文件夹
     */
    @GetMapping("/createDir")
    @ResponseBody
    public CommonResult createDir(@RequestParam String dirname,
                                  @RequestParam Long pid,
                                  @RequestParam String username) {
        SysUser user = userService.queryByName(username);
        if (user == null){
            return new CommonResult(false, "用户信息不存在！");
        }
        Long uid = user.getUserId();

        int i = fileDirService.createDir(dirname, pid, uid);
        if (i > 0) {
            List<AllFileModel> dirNames = fileDirService.queryDirNames(pid, uid);
            List<AllFileModel> fileNames = fileService.getFileNameByPid(pid, uid);
            if (CollectionUtil.isNotEmpty(dirNames)) {
                if (CollectionUtil.isNotEmpty(fileNames)) {
                    dirNames.addAll(fileNames);
                }
                return new CommonResult(true, "添加成功", dirNames);
            } else {
                return new CommonResult(true, "添加成功",  fileNames);
            }
        } else {
            return new CommonResult(false, "添加失败", null);
        }


    }

    /**
     * 查询文件或者文件夹信息
     */
    @GetMapping("/reloadFileInfo")
    @ResponseBody
    public CommonResult reloadFileInfo(@RequestParam Long pid,
                                       @RequestParam String username) {
        SysUser sysUser = userService.queryByName(username);
        Long uid = sysUser.getUserId();

        List<AllFileModel> dirNames = fileDirService.queryDirNames(pid, uid);
        List<AllFileModel> fileNames = fileService.getFileNameByPid(pid, uid);
        if (CollectionUtil.isNotEmpty(dirNames)) {
            if (CollectionUtil.isNotEmpty(fileNames)) {
                dirNames.addAll(fileNames);
            }
            return new CommonResult(true, "添加成功", dirNames);
        } else {
            return new CommonResult(true, "添加成功", fileNames);
        }
    }

    /**
     * 删除文件，支持批量删除
     */
    @PostMapping("/deleteFile")
    @ResponseBody
    public CommonResult deleteFile(@RequestBody String data){
        try {
            JSONObject dataObject = JSON.parseObject(data);
            JSONArray fileInfoArray = JSON.parseArray(dataObject.getString("data"));
            List<JSONObject> fileInfoList = fileInfoArray.stream().map(fileInfo -> (JSONObject) fileInfo).collect(Collectors.toList());

            if (fileInfoList == null){
                return new CommonResult(false, "格式转换错误！");
            }
            List<String> dirIdList  = fileInfoList.stream().filter(fileInfo ->  StrUtil.equals(fileInfo.getString("type"), "dir")).map(fileInfo -> fileInfo.getString("id")).collect(Collectors.toList());
            List<String> fileIdList = fileInfoList.stream().filter(fileInfo -> !StrUtil.equals(fileInfo.getString("type"), "dir")).map(fileInfo -> fileInfo.getString("id")).collect(Collectors.toList());
            fileService.deleteFiles(fileIdList);
            fileDirService.deleteFileDir(dirIdList);

        } catch (Exception e) {
            LOGGER.error("delete file err, message is {}", e.getMessage());
        }

        //todo delete file and dirs
        return new CommonResult(true, "删除成功");


    }

    /**
     * 根据文件类型查询file信息
     * @param username
     * @param fileType
     */
    @GetMapping("/queryFileInfoByType")
    @ResponseBody
    public CommonResult queryFileInfoByType(String username, String fileType){
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(fileType)){
            return new CommonResult(false, "参数不能为空！");
        }
        SysUser sysUser = userService.queryByName(username);
        Long uid = sysUser.getUserId();
        List<AllFileModel> modelList = fileService.queryFileNameByType(uid, fileType);
        return new CommonResult(true, "查询成功！", modelList);
    }

    /**
     * 根据时间轴查询图片
     * @param username
     * @return
     */
    @GetMapping("/getImageGroupByDate")
    @ResponseBody
    public CommonResult getImageGroupByDate(String username, String fileType){
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(fileType)){
            return new CommonResult(false, "请检查参数");
        }

        SysUser user = userService.queryByName(username);
        if (user == null){
            return new CommonResult(false, "请检查用户登陆信息！");
        }
        List<AllFileModel> modelList = fileService.getImageGroupByDate(user.getUserId(), fileType);
        return  new CommonResult(true,"查询成功", modelList);
    }

}
