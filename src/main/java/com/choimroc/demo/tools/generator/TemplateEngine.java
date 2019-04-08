package com.choimroc.demo.tools.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义模板引擎
 *
 * @author choimroc
 * @since 2019/4/8
 */
public class TemplateEngine extends AbstractTemplateEngine {
    private static final String DOT_VM = ".vm";
    private VelocityEngine velocityEngine;

    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty(ConstVal.VM_LOAD_PATH_KEY, ConstVal.VM_LOAD_PATH_VALUE);
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
            p.setProperty(Velocity.ENCODING_DEFAULT, ConstVal.UTF8);
            p.setProperty(Velocity.INPUT_ENCODING, ConstVal.UTF8);
            p.setProperty("file.resource.loader.unicode", StringPool.TRUE);
            velocityEngine = new VelocityEngine(p);
        }
        return this;
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        if (StringUtils.isEmpty(templatePath)) {
            return;
        }
        Template template = velocityEngine.getTemplate(templatePath, ConstVal.UTF8);
        //修改时间格式
        objectMap.put("date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, ConstVal.UTF8);
             BufferedWriter writer = new BufferedWriter(ow)) {
            template.merge(new VelocityContext(objectMap), writer);
        }
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }


    @Override
    public String templateFilePath(String filePath) {
        if (null == filePath || filePath.contains(DOT_VM)) {
            return filePath;
        }
        return filePath + DOT_VM;
    }
}
