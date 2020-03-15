package com.choimroc.mybatisplusdemo.application.student.entity;

import lombok.Data;

@Data
public class StudentInfoEntity {
    private Integer id;
    private String userId;
    private String username;
    private String clazz;
    private long times;
    private String insertTime;
    private String showtime;
    private String course;
    private String courseSum;
    private String status;
    private String sumAvg;

}
