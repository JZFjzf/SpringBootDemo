package com.choimroc.mybatisplusdemo.application.users.service.impl;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.toolkit.ImageInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.choimroc.mybatisplusdemo.application.student.entity.CourseInfoEntity;
import com.choimroc.mybatisplusdemo.application.student.entity.InquireEntity;
import com.choimroc.mybatisplusdemo.application.users.entity.UserRecordEntity;
import com.choimroc.mybatisplusdemo.application.users.mapper.UserRecordMapper;
import com.choimroc.mybatisplusdemo.application.users.service.UserRecordService;
import com.choimroc.mybatisplusdemo.tool.DateTimeUtils;
import com.choimroc.mybatisplusdemo.tool.FSUtil;
import com.choimroc.mybatisplusdemo.tool.ImageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;
import static com.choimroc.mybatisplusdemo.application.users.controller.UsersController.baseFileUrl;
import static com.choimroc.mybatisplusdemo.application.users.controller.UsersController.faceEngine;

@Service
public class UserRecordServiceImpl extends ServiceImpl<UserRecordMapper, UserRecordEntity> implements UserRecordService {

    private final UserRecordMapper userRecordMapper;

    @Autowired
    public UserRecordServiceImpl(UserRecordMapper userRecordMapper) {
        this.userRecordMapper = userRecordMapper;
    }

    @Override
    public UserRecordEntity addUserRecord(CourseInfoEntity courseInfoEntity, String courseSum, MultipartFile imgFile) throws Exception {
        String path = baseFileUrl + "feature";
        File file = new File(path);        //获取其file对象
        List<String> filename = new ArrayList<>(FSUtil.func(file));
        byte[] bytes;

        //图片转byte
        bytes = ImageUtils.imageFile2byte(FSUtil.multipartFileToFile(imgFile));

        //人脸检测
        ImageInfo imageInfo = getRGBData(bytes);
        List<FaceInfo> faceInfoList = new ArrayList<>();
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        try {
            faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        } catch (Exception e) {
            return null;
        }


        //人脸比对
        List<Float> similarList = new ArrayList<>();
        List<String> fileList = new ArrayList<>();

        for (String s : filename) {
            FaceFeature targetFaceFeature = new FaceFeature();
            targetFaceFeature.setFeatureData(FSUtil.fileConvertToByteArray(new File(s)));
            FaceSimilar faceSimilar = new FaceSimilar();
            faceEngine.compareFaceFeature(targetFaceFeature, faceFeature, faceSimilar);
            String[] fileMn = s.split("/");
            //similarlist.add(new ScoreMax(faceSimilar.getScore(),filemn[filemn.length-1]));
            similarList.add(faceSimilar.getScore());
            fileList.add(fileMn[fileMn.length - 1]);
        }


        float maxCore = Collections.max(similarList);
        String singleFile = fileList.get(similarList.indexOf(maxCore));
//        System.out.println("最大相似度：" + maxscore);
//        System.out.println("对应文件名：" + singlefile);

        int similarValue = FSUtil.plusHundred(maxCore);//获取相似值
        UserRecordEntity userRecord = new UserRecordEntity();
        String userId = singleFile.split("_")[0];
        String username = singleFile.split("_")[1];
        userRecord.setSimilarity(similarValue);

        userRecord.setCourse(courseInfoEntity.getCourse());
        userRecord.setCourseSum(Integer.parseInt(courseSum));
        userRecord.setTotalCourse(courseInfoEntity.getTotalCourse());
        long time = System.currentTimeMillis();
        userRecord.setTimes(time);
        userRecord.setShowtime(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(time)));
        String imgName = singleFile + ".jpg";
        String singleFileUrl = baseFileUrl + "discern_img/" + singleFile + "/";
        String otherUrl = baseFileUrl + "discern_img/other/";

        //创建目录
        FSUtil.checkDirExists(new File(singleFileUrl));
        FSUtil.checkDirExists(new File(otherUrl));

        if (similarValue > 85) {
            System.out.println("打卡成功");
            userRecord.setUserId(Integer.parseInt(userId));
            userRecord.setUsername(username);
            File tempFile = new File(singleFileUrl + FSUtil.getUUIDFileName(imgName));
            FileCopyUtils.copy(bytes, tempFile);
            userRecord.setImgUrl(singleFile + "/" + tempFile.getName());
            userRecord.setResult("success");
        } else {
            System.out.println("打卡失败");
            userRecord.setUserId(-1);
            userRecord.setUsername("failure");
            File tempFile = new File(otherUrl + UUID.randomUUID() + ".jpg");
            userRecord.setImgUrl("other/" + tempFile.getName());
            FileCopyUtils.copy(bytes, tempFile);
            userRecord.setResult("failure");
            return null;
        }
        return userRecord;
    }

    @Override
    public void updateUserRecord(String course, String totalCourse) {
        //更新用户记录
        userRecordMapper.updateUserRecord(course, totalCourse);
    }

    @Override
    public List<UserRecordEntity> getUserRecords(InquireEntity inquireEntity) {
        List<UserRecordEntity> userRecordList = new ArrayList<>();
        String userId = inquireEntity.getUserId();
        String username = inquireEntity.getUsername();
        String startTime = inquireEntity.getStartTime();
        String endTime = inquireEntity.getEndTime();
        String course = inquireEntity.getCourse();
        String courseSum = inquireEntity.getCourseSum();
        //转换为Date类
        Long startTimes = null;
        Long endTimes = null;

        try {
          startTimes= DateTimeUtils.getMilliseconds(startTime);
          endTimes=DateTimeUtils.getMilliseconds(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(course) && StringUtils.isNotBlank(courseSum)) {
            userRecordList.addAll(userRecordMapper.queryUserRecord(userId, username, course, courseSum, startTimes, endTimes));
            if (userRecordList.isEmpty()){
                return null;
            }
        }
        return userRecordList;
    }
}
