package com.choimroc.demo.application.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.choimroc.demo.application.example.entity.Example;

import java.util.List;

/**
 * @author choimroc
 * @since 2019/10/17
 */
public interface ExampleService extends IService<Example> {
    IPage<Example> getByPage(Long pageNumber, Long pageSize, String startDate, String endDate);

    boolean updateBatch(List<Example> shelfUpdateBodies);

    boolean saveReturnKey(Example example);
}
