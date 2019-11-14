package com.choimroc.demo.application.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.choimroc.demo.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author choimroc
 * @since 2019/4/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Example extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;

}
