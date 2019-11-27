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
    List<List<?>> selectByPage(
            @Param("current") Long current,
            @Param("pageSize") Long pageSize,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate

    );

    int insertReturnKey(Example example);

    int updateExample(Example example);

    List<Example> selectByIds(
            @Param("name") String name,
            @Param("ids") Long[] ids
    );
}
