package com.choimroc.mybatisplusdemo.application.users.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Users{
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private int userId;
    private String username;
    @TableField("img_url")
    private String imgUrl;
    private long times;
    private String showtime;


}
