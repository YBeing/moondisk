package com.lying.moondisk.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileModel {
    private InputStream inputStream;
    private String      fileName;
}
