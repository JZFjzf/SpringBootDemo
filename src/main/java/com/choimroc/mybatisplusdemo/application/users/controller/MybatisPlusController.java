package com.choimroc.mybatisplusdemo.application.users.controller;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.choimroc.mybatisplusdemo.annotation.IgnoreSecurity;
import com.choimroc.mybatisplusdemo.application.users.entity.UserRecord;
import com.choimroc.mybatisplusdemo.application.users.entity.Users;
import com.choimroc.mybatisplusdemo.application.users.service.UserRecordService;
import com.choimroc.mybatisplusdemo.application.users.service.UsersService;
import com.choimroc.mybatisplusdemo.base.BaseController;
import com.choimroc.mybatisplusdemo.common.config.UrlUtils;
import com.choimroc.mybatisplusdemo.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Validated
@RestController
@RequestMapping("/face")
public class MybatisPlusController extends BaseController {
    public static FaceEngine faceEngine;
    public static String basefileurl = UrlUtils.baseurl + "FaceAttendance/";
    private final UsersService usersService;
    private final UserRecordService userRecordService;

    @Autowired
    public MybatisPlusController(UsersService usersService, UserRecordService userRecordService) {
        this.usersService = usersService;
        this.userRecordService = userRecordService;
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
    public Result insertUser(Users user, MultipartFile imgFile) throws IOException {
        Users storedUsers = usersService.addUser(user, imgFile);
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
        UserRecord storedUserRecord = userRecordService.addUserRecord(course, courseSum,imgFile);
        if (storedUserRecord == null) {
            return failed("打卡失败");
        } else {
            //纪录打卡记录
            userRecordService.save(storedUserRecord);
            return success("打卡成功");
        }

}


}
