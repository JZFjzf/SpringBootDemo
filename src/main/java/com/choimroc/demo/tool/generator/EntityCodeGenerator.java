package com.choimroc.demo.tool.generator;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;

/**
 * 代码生成器
 *
 * @author choimroc
 * @since 2019/4/8
 */
public class EntityCodeGenerator {

    public static void main(String[] args) {
        StrategyConfig strategyConfig = new StrategyConfig();
        GeneratorConfig.generator(strategyConfig);
    }
}
