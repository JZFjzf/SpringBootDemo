package com.choimroc.demo.application.example.controller;


import com.choimroc.demo.base.BaseController;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author choimroc
 * @since 2019/08/13
 */
@Validated
@RestController
@RequestMapping("example")
public class ExampleController extends BaseController {



}

