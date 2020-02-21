package com.choimroc.mybatisplusdemo.application.users.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.choimroc.mybatisplusdemo.application.users.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UsersService extends IService<Users> {
    Users addUser(Users user, MultipartFile imgFile) throws IOException;
}
