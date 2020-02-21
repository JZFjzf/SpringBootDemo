package com.choimroc.mybatisplusdemo.application.users.service.impl;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.enums.ImageFormat;
import com.arcsoft.face.toolkit.ImageInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.choimroc.mybatisplusdemo.application.users.entity.Users;
import com.choimroc.mybatisplusdemo.application.users.mapper.UsersMapper;
import com.choimroc.mybatisplusdemo.application.users.service.UsersService;
import com.choimroc.mybatisplusdemo.tool.FSUtil;
import com.choimroc.mybatisplusdemo.tool.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;
import static com.choimroc.mybatisplusdemo.application.users.controller.MybatisPlusController.basefileurl;
import static com.choimroc.mybatisplusdemo.application.users.controller.MybatisPlusController.faceEngine;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    private final UsersMapper usersMapper;

    @Autowired
    public UsersServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public Users addUser(Users user, MultipartFile imgFile) throws IOException {
        //定义变量
        String userId = String.valueOf(user.getUserId());
        String username = user.getUsername();
        String filename = userId + username + ".jpg";

        //自定义目录路径
        String imgUrl = basefileurl + "img/" + userId + "_" + username + "/";
        String featureUrl = basefileurl + "feature/" + userId + "_" + username + "/";

        // TODO BASE64 方式的 格式和名字需要自己控制（如 png 图片编码后前缀就会是 data:image/png;base64,）
        // TODO 防止有的传了 data:image/png;base64, 有的没传的情况

        //创建目录
        FSUtil.checkDirExists(new File(imgUrl));
        FSUtil.checkDirExists(new File(featureUrl));

        //创建了一个File对象
        File tempFile = new File(imgUrl + FSUtil.getUUIDFileName(filename));
        byte[] bytes;
        try {
            bytes = ImageUtils.imageFile2byte(FSUtil.multipartFileToFile(imgFile));
        } catch (Exception e) {
            return null;
        }
        //复制bytes到file对象中
        FileCopyUtils.copy(bytes, tempFile);
        user.setImgUrl(userId + "_" + username + "/" + tempFile.getName());
        long times = System.currentTimeMillis();
        user.setTimes(times);
        user.setShowtime(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(times)));

        //人脸检测
        ImageInfo imageInfo = getRGBData(tempFile);
        List<FaceInfo> faceInfoList = new ArrayList<>();
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);
        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        try {
            faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList.get(0), faceFeature);
        } catch (Exception e) {
            return null;
        }
        //创建了一个File对象
        File featureTempFile = new File(featureUrl + userId + "_" + username);
        byte[] featureBytes = faceFeature.getFeatureData();
        FileCopyUtils.copy(featureBytes, featureTempFile);
        return user;
    }


}

