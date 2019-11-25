package com.choimroc.demo.application.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.choimroc.demo.application.example.entity.Example;
import com.choimroc.demo.application.example.service.ExampleService;
import com.choimroc.demo.base.BaseController;
import com.choimroc.demo.common.result.Result;
import com.choimroc.demo.tool.ValidatorUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

/**
 * @author choimroc
 * @since 2019/08/13
 */
@Validated
@RestController
@RequestMapping("example")
public class ExampleController extends BaseController {
    private final ExampleService exampleService;
    private final TransactionTemplate transactionTemplate;


    @Autowired
    public ExampleController(ExampleService exampleService, TransactionTemplate transactionTemplate) {
        this.exampleService = exampleService;
        this.transactionTemplate = transactionTemplate;
    }

    @PostMapping
    public Result transaction() {
        Boolean success = transactionTemplate.execute(status -> {
            boolean result = false;
            try {
                if (exampleService.save(new Example())
                        && exampleService.updateBatch(new ArrayList<>())) {
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!result) {
                //回滚
                status.setRollbackOnly();
            }
            return result;
        });

        if (success != null && success) {
            return success();
        }

        return failed();
    }

    @GetMapping("getForPage")
    public Result getForPage(
            @NotNull(message = "{parameter.notNull.pageNumber}") Long pageNumber,
            @NotNull(message = "{parameter.notNull.pageSize}") Long pageSize,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return page(exampleService.getList(pageNumber, pageSize, startDate, endDate));
    }

    @GetMapping("getForDate")
    public Result getForDate(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        //大于或等于 startDate 小于 endDate
        QueryWrapper<Example> queryWrapper = Wrappers.<Example>query()
                .ge(ValidatorUtils.nonBlank(startDate), "create_time", startDate)
                .lt(ValidatorUtils.nonBlank(endDate), "create_time", endDate);
        return data(exampleService.list(queryWrapper));
    }
}

