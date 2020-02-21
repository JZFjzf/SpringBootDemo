package com.choimroc.mybatisplusdemo.tool;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    //图片到byte数组
    public byte[] image2byte(String path) {
        byte[] data = null;
        FileImageInputStream input;
        try {
            input = new FileImageInputStream(new File(path));
            data = grtByte(input);
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    //图片文件到byte数组
    public static byte[] imageFile2byte(File file) {
        byte[] data = null;
        FileImageInputStream input;
        try {
            input = new FileImageInputStream(file);
            data = grtByte(input);
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    public static byte[] grtByte(FileImageInputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int numBytesRead;
        while ((numBytesRead = input.read(buf)) != -1) {
            output.write(buf, 0, numBytesRead);
        }
        byte[] data = output.toByteArray();
        output.close();
        input.close();
        return data;
    }

    //byte数组到图片
    public void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) return;
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    //byte数组到16进制字符串
    public String byte2string(byte[] data) {
        if (data == null || data.length <= 1) return "0x";
        if (data.length > 200000) return "0x";
        StringBuilder sb = new StringBuilder();
        int[] buf = new int[data.length];
        //byte数组转化成十进制
        for (int k = 0; k < data.length; k++) {
            buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
        }
        //十进制转化成十六进制
        for (int i : buf) {
            if (i < 16) sb.append("0").append(Integer.toHexString(i));
            else sb.append(Integer.toHexString(i));
        }
        return "0x" + sb.toString().toUpperCase();
    }
}
