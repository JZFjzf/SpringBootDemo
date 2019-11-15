package com.choimroc.demo.application.example.controller;


import com.choimroc.demo.application.example.entity.Example;
import com.choimroc.demo.application.example.service.ExampleService;
import com.choimroc.demo.base.BaseController;
import com.choimroc.demo.common.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public Result get(
            @NotNull(message = "{parameter.notNull.pageNumber}") Long pageNumber,
            @NotNull(message = "{parameter.notNull.pageSize}") Long pageSize) {
        return page(exampleService.getList(pageNumber, pageSize));
    }
}

