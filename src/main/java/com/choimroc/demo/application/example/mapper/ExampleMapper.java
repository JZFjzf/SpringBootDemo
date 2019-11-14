package com.choimroc.demo.application.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.choimroc.demo.application.example.entity.Example;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author choimroc
 * @since 2019/10/17
 */
@Repository
public interface ExampleMapper extends BaseMapper<Example> {
    List<List<?>> selectPage(
            @Param("current") Long current,
            @Param("pageSize") Long pageSize
    );
}
