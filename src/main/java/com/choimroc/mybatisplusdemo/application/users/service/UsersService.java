package com.choimroc.mybatisplusdemo.application.users.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.choimroc.mybatisplusdemo.application.student.entity.InquireEntity;
import com.choimroc.mybatisplusdemo.application.users.entity.UsersEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UsersService extends IService<UsersEntity> {
    UsersEntity addUser(UsersEntity user, MultipartFile imgFile) throws IOException;
    List<UsersEntity> getUsers(InquireEntity inquireEntity,int judge);

}
