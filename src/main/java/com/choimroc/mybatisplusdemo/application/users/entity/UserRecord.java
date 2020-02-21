package com.choimroc.mybatisplusdemo.application.users.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.choimroc.mybatisplusdemo.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("users_record")
public class UserRecord extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private int userId;
    private String username;
    private long times;
    private String showtime;
    private String result;
    @TableField("img_url")
    private String imgUrl;
    private String course;
    private int similarity;
    @TableField("course_sum")
    private int courseSum;
    @TableField("total_course")
    private String totalCourse;



}
