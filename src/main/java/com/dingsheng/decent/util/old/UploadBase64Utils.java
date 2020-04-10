package com.dingsheng.decent.util.old;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class UploadBase64Utils {

    /**
     * 图片转化成base64字符串
     *
     * @param pathUrl 图片路径
     * @return
     */
    public static String GetImageStr(String pathUrl) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(pathUrl);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * base64字符串转化成图片
     *
     * @param path   保存路径
     * @param imgStr Base64字符串
     * @return
     */
    public static boolean GenerateImage(String path, String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        String strBase64 = "";

        try {
            if (imgStr.startsWith("data:image/jpeg;base64,")) {
                strBase64 = imgStr.replace("data:image/jpeg;base64,", "");
            }else {
                if (imgStr.startsWith("data:image/png;base64,")) {
                    strBase64 = imgStr.replace("data:image/png;base64,", "");
                }
            }

            //Base64解码
            byte[] b = decoder.decodeBuffer(strBase64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(path);//新生成的图片
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /***
     * 保存文件
     *
     * @param file
     * @return
     */
    public static boolean SaveFile(String path, MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                System.err.println(path);
                File saveDir = new File(path);
                if (!saveDir.getParentFile().exists())
                    saveDir.getParentFile().mkdirs();
                System.err.println(path);
                // 转存文件
                file.transferTo(saveDir);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
