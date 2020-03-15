package com.choimroc.mybatisplusdemo.application.users.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.choimroc.mybatisplusdemo.application.student.entity.CourseInfoEntity;
import com.choimroc.mybatisplusdemo.application.student.entity.InquireEntity;
import com.choimroc.mybatisplusdemo.application.users.entity.UserRecordEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserRecordService extends IService<UserRecordEntity> {
    UserRecordEntity addUserRecord(CourseInfoEntity courseInfoEntity, String courseSum, MultipartFile imgFile) throws Exception;
    void updateUserRecord(String course,String totalCourse);
    List<UserRecordEntity> getUserRecords(InquireEntity inquireEntity);
}
