package com.choimroc.demo.application.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.choimroc.demo.application.example.entity.Example;
import com.choimroc.demo.application.example.mapper.ExampleMapper;
import com.choimroc.demo.application.example.service.ExampleService;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author choimroc
 * @since 2019/10/17
 */
@SuppressWarnings("unchecked")
@Service
public class ExampleServiceImpl extends ServiceImpl<ExampleMapper, Example> implements ExampleService {
    private final ExampleMapper exampleMapper;

    public ExampleServiceImpl(ExampleMapper exampleMapper) {
        this.exampleMapper = exampleMapper;
    }

    @Override
    public IPage<Example> getList(Long pageNumber, Long pageSize,String startDate,String endDate) {
        List<List<?>> objects;
        List<Example> list = new ArrayList<>();
        Integer total = 0;
        objects = exampleMapper.selectForPage((pageNumber - 1) * pageSize, pageSize,startDate,endDate);

        if (objects != null) {
            total = (Integer) objects.get(1).get(0);
            list = (List<Example>) objects.get(0);
        }

        IPage<Example> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setTotal(total);
        page.setPages(total / pageSize + 1);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean updateBatch(List<Example> examples) {
        if (examples == null) {
            return false;
        }
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            ExampleMapper mapper = batchSqlSession.getMapper(ExampleMapper.class);
            for (Example item : examples) {
                mapper.updateExample(item);
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    @Override
    public boolean saveReturnKey(Example example) {
        return exampleMapper.insertReturnKey(example) > 0;
    }
}
