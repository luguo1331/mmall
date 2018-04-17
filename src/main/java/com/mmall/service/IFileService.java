package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author junyi
 * @Date 2018-04-17 17-33
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
