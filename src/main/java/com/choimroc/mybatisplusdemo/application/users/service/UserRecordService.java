package com.choimroc.mybatisplusdemo.application.users.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.choimroc.mybatisplusdemo.application.users.entity.UserRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserRecordService extends IService<UserRecord> {
    UserRecord addUserRecord( String course, String courseSum, MultipartFile imgFile) throws Exception;
}
