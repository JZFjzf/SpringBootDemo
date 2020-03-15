package com.choimroc.mybatisplusdemo.application.users.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("users_record")
public class UserRecordEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userId;
    private String username;
    private long times;
    private String showtime;
    private String result;
    private String imgUrl;
    private String course;
    private int similarity;
    private int courseSum;
    private String totalCourse;



}
