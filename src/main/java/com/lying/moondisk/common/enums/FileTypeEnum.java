package com.lying.moondisk.common.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum FileTypeEnum {
    MP3(  "mp3", "music"),
    MUSIC("flac","music"),
    PNG(  "png", "image"),
    JPG(  "jpg", "image"),
    JPEG( "jpeg","image"),
    GIF(  "gif", "image");
    private String name;
    private String type;

    public static List<String> getEnumListByType(String type){
        if (StrUtil.isEmpty(type)){
            return null;
        }
        List<String> nameList = new ArrayList<>();
        FileTypeEnum[] typeEnums = FileTypeEnum.values();
        for (FileTypeEnum typeEnum : typeEnums) {
            if (StrUtil.equalsIgnoreCase(type, typeEnum.getType())){
                nameList.add(typeEnum.getName());
            }
        }

        return nameList;
    }
}
