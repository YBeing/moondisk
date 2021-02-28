package com.lying.moondisk.mapper;

import com.lying.moondisk.bean.SysFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysFileMapperBase {
    /**
     * 通过用户ID和文件夹ID查询文件信息
     * @param pid
     * @param uid
     * @return
     */
    List<SysFile> getByPid(@Param("pid") Long pid, @Param("uid") Long uid);

    /**
     * 根据文件类型和用户ID查询文件信息
     * @param uid
     * @param typeNameList
     * @return
     */
    List<SysFile> getByType(@Param("uid") Long uid, @Param("typeNameList") List<String> typeNameList);

    /**
     * 根据ID列表删除文件
     * @param ids
     */
    void deleteFiles(List<String> ids);

    /**
     * 根据父级ID删除下面的子级文件夹
     * @param pids
     */
    void deleteFilesByPid(List<String> pids);

    /**
     * 根据ID集合查询指定信息
     * @param list
     * @return
     */
    List<String> getFilesByIds(List<String> list);

    /**
     * 根据ID集合查询文件的所有信息
     * @param list
     * @return
     */
    List<SysFile> getFilesInfoByIds(List<String> list);

    /**
     * 根据父级ID集合查询指定信息
     * @param list
     * @return
     */
    List<String> getFilesByPids(List<String> list);

    /**
     * 根据用户ID查询文件类型为图片的信息（根据日期类型分类）
     * @param uid
     * @param typeNameList
     * @return
     */
    List<Map> getImageGroupByDate(@Param("uid") Long uid, @Param("typeNameList") List<String> typeNameList);

    /**
     * 根据用户ID查询所有的文件类型为图片的信息
     * @param uid
     * @return
     */
    List<SysFile> getAllImage(Long uid);

    /**
     * 指定查询图片信息
     * @param modifyTime
     * @param fileName
     * @param uid
     * @return
     */
    List<String> searchImage(@Param("modifyTime") String modifyTime,@Param("fileName") String fileName,
                             @Param("uid") Long uid);

    /**
     * 查询所有的音乐文件类型的信息
     * @param uid
     * @return
     */
    List<SysFile> getAllMusicFile(Long uid);
}