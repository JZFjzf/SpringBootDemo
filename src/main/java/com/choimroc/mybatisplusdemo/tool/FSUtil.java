package com.choimroc.mybatisplusdemo.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author yangxin_ryan
 * 机器磁盘文件的操作
 */
public class FSUtil {
 
    private static Log LOG = LogFactory.getLog(FSUtil.class);

    /**
     * 判断文件是否存在
     * @param file
     */
    public static void checkFileExists(File file) {
        if (file.exists()) {
            LOG.info("待写入文件存在");
        } else {
            LOG.info("待写入文件不存在, 创建文件成功");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 判断文件夹是否存在
     * @param file
     */
    public static void checkDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) LOG.info("目录存在");
            else {
                LOG.info("同名文件存在, 不能创建");
            }
        } else {
            LOG.info("目录不存在，创建目录");
            file.mkdirs();
        }
    }


    public static String getUUIDFileName(String fileName) {
        // 将文件名的前面部分进行截取：xx.jpg   --->   .jpg
        int idx = fileName.lastIndexOf(".");
        String extension = fileName.substring(idx);
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }

    public static byte[] fileConvertToByteArray(File file) {
        byte[] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            data = baos.toByteArray();
            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public static int plusHundred(Float value) {
        BigDecimal target = new BigDecimal(value);
        BigDecimal hundred = new BigDecimal("100");
        return target.multiply(hundred).intValue();
    }

    public static List<String> func(File file) {
        List<String> fileNameList = new ArrayList<>();
        File[] tempList = file.listFiles();
        assert tempList != null;
        for (File value : tempList) {
            if (value.isFile()) {
                System.out.println("文     件：" + value);
            }
            if (value.isDirectory()) {
//                System.out.println("文件夹：" + tempList[i].getName());
                fileNameList.add(value.toString() + "/" + value.getName());
            }
        }
        return fileNameList;
    }

    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
