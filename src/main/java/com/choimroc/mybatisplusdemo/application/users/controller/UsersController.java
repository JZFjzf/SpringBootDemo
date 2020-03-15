package com.choimroc.mybatisplusdemo.application.users.controller;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.choimroc.mybatisplusdemo.annotation.IgnoreSecurity;
import com.choimroc.mybatisplusdemo.application.student.entity.InquireEntity;
import com.choimroc.mybatisplusdemo.application.student.service.CourseInfoService;
import com.choimroc.mybatisplusdemo.application.users.entity.UserRecordEntity;
import com.choimroc.mybatisplusdemo.application.users.entity.UsersEntity;
import com.choimroc.mybatisplusdemo.application.users.service.UserRecordService;
import com.choimroc.mybatisplusdemo.application.users.service.UsersService;
import com.choimroc.mybatisplusdemo.base.BaseController;
import com.choimroc.mybatisplusdemo.common.config.UrlUtils;
import com.choimroc.mybatisplusdemo.common.result.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;


@Validated
@RestController
@RequestMapping("/face")
public class UsersController extends BaseController {
    public static FaceEngine faceEngine;
    public static String baseFileUrl = UrlUtils.baseurl + "FaceAttendance/";
    private final UsersService usersService;
    private final UserRecordService userRecordService;
    private final CourseInfoService courseInfoService;

    @Autowired
    public UsersController(UsersService usersService, UserRecordService userRecordService, CourseInfoService courseInfoService) {
        this.usersService = usersService;
        this.userRecordService = userRecordService;
        this.courseInfoService = courseInfoService;
    }

    @PostConstruct
    public static void initEngine() {
        String appId = "FVZYoDcwvxZjmB1n8ujYncfmWcwLBSTh7fDpX95pu4rD";
        String sdkKey = "EQakpZp6xRzY4TwrYFDbZ5khiMK1R2Y1poo7xFpf3Kfb";

        faceEngine = new FaceEngine(UrlUtils.baseurl + "arcsoft_lib");
        //激活引擎
        int activeCode = faceEngine.activeOnline(appId, sdkKey);

        if (activeCode != ErrorInfo.MOK.getValue() && activeCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        int initCode = faceEngine.init(engineConfiguration);

        if (initCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }
    }


    /**
     * 新增一个用户
     */
    @PostMapping("/user")
    @IgnoreSecurity
    public Result insertUser(UsersEntity user, MultipartFile imgFile) throws IOException {
        UsersEntity storedUsers = usersService.addUser(user, imgFile);
        if (storedUsers == null) {
            return failed("记录用户失败");
        } else {
            //添加用户
            usersService.save(storedUsers);
            return success("记录用户成功");
        }

    }


    @PostMapping("/faceSearch")
    @IgnoreSecurity
    public Result faceSearch(String course, String courseSum, MultipartFile imgFile) throws Exception {
        UserRecordEntity storedUserRecord = userRecordService.addUserRecord(courseInfoService.getById(course), courseSum,imgFile);
        if (storedUserRecord == null) {
            return failed("打卡失败");
        } else {
            //纪录打卡记录
            userRecordService.save(storedUserRecord);
            return success("打卡成功");
        }

}

    @GetMapping("/attendance/records")
    @IgnoreSecurity
    public Result index(@Param("inquire") InquireEntity inquireEntity) {

        List<UserRecordEntity> userRecordList = userRecordService.getUserRecords(inquireEntity);
        if (userRecordList==null){
            return failed("未查询到该用户");
        }else if (userRecordList.isEmpty()) {
             //返回所有记录
            userRecordList.addAll(userRecordService.list());
        }

        //时间倒序
        userRecordList.sort((o1, o2) -> Long.compare(o2.getTimes(), o1.getTimes()));

        for (int i = 0; i < userRecordList.size(); i++) {
            userRecordList.get(i).setId(i + 1);
        }
        return data("userRecord","人脸识别考勤记录", userRecordList);
    }

    @GetMapping("/student/attendance")
    @IgnoreSecurity
    public Result student(@Param("inquire") InquireEntity inquireEntity) {
        List<UserRecordEntity> userRecordList = userRecordService.getUserRecords(inquireEntity);
        if (userRecordList==null){
            return failed("未查询到该用户");
        }

        //时间倒序
        userRecordList.sort((o1, o2) -> Long.compare(o2.getTimes(), o1.getTimes()));

        for (int i = 0; i < userRecordList.size(); i++) {
            userRecordList.get(i).setId(i + 1);
        }
        return data("userRecord","人脸识别考勤记录", userRecordList);
    }

    @GetMapping("/register/records")
    @IgnoreSecurity
    public Result register(@Param("inquire") InquireEntity inquireEntity) {
        List<UsersEntity> usersList = usersService.getUsers(inquireEntity,0);
        if (usersList==null){
            return failed("未查询到该用户");
        }

        //时间倒序
        usersList.sort((o1, o2) -> Long.compare(o2.getTimes(), o1.getTimes()));
        //学号正序
        usersList.sort(Comparator.comparingInt(UsersEntity::getUserId));

        for (int i = 0; i < usersList.size(); i++) {
            usersList.get(i).setId(i + 1);
        }
        return data("users","人脸识别注册记录", usersList);
    }

    @GetMapping("/student/register")
    @IgnoreSecurity
    public Result studentRegister(@Param("inquire") InquireEntity inquireEntity) {
        List<UsersEntity> usersList = usersService.getUsers(inquireEntity,1);
        if (usersList==null){
            return failed("未查询到该用户");
        }

        //时间倒序
        usersList.sort((o1, o2) -> Long.compare(o2.getTimes(), o1.getTimes()));

        for (int i = 0; i < usersList.size(); i++) {
            usersList.get(i).setId(i + 1);
        }

        return data("users","人脸识别注册记录", usersList);
    }

    /**
     * 修改一个用户
     *
     * @param usersEntity
     * @return
     */
    @PutMapping("/user")
    @IgnoreSecurity
    public Result updateUser(UsersEntity usersEntity) {
        System.out.println("----------Put");
        return auto(usersService.updateById(usersEntity));
    }

    /**
     * 删除一个用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/user/{id}")
    @IgnoreSecurity
    public Result deleteUserById(@PathVariable Integer id) {
        System.out.println("----------Delete");
        return auto(usersService.removeById(id));
    }
}
