package com.choimroc.mybatisplusdemo.application.users.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("users")
public class UsersEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userId;
    private String username;
    private String imgUrl;
    private long times;
    private String showtime;


}
