package com.dingsheng.decent.util.old;

import com.alibaba.fastjson.JSONObject;
import com.dingsheng.decent.dto.admin.System.DResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传帮助类
 */
public class UploadingUnity {

    //<editor-fold desc="上传文件">

    /**
     * 上传文件
     *
     * @param file 前端上传的文件
     * @return
     */
    public DResult FileUpload(MultipartFile file, String UserName, int VerifyType) {
        DResult res = new DResult();
        //上传路径
        String path;
        //返回路径
        String retUrl;
        try {

            Date date = new Date(new Date().getTime());
            //上传目录地址 按时间与用户GUID产生文件夹
            String uploadDir = UploadPath.UploadLocalPath + UploadPath.CommUrl;
            retUrl = UploadPath.RootUrl + UploadPath.CommUrl;
            //验证后缀名
            Verify.If(!SuffixVerify(file, VerifyType), "请上传正确的文件");
            //如果上传目录不存在则创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //获取上传文件的后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //上传文件名
            String fileName = UUID.randomUUID().toString() + suffix;
            path = uploadDir + "/" + fileName;
            retUrl += "/" + fileName;
            //服务器端保存文件对象
            File serverFile = new File(path);
            //将上传的文件写入到服务器端的文件内
            file.transferTo(serverFile);
            res.SetSuccess("成功", retUrl);
        } catch (Exception e) {
            //打印错误的堆栈信息
            e.printStackTrace();
            res.SetError(e.getMessage());
        }
        return res;
    }
    //</editor-fold>

    //<editor-fold desc="前端图片上传">

    /**
     * 前端图片上传
     *
     * @return
     */
    public DResult FileUploadImg(String base64Str) {
        DResult res = new DResult();
        String path = null;
        try {
            String savaPath = UploadPath.CommUrl + UUID.randomUUID().toString() + ".png";
            boolean isSuccess = UploadBase64Utils.GenerateImage(UploadPath.UploadLocalPath + savaPath, base64Str);
            if (isSuccess) {
                path = UploadPath.RootUrl + savaPath;
            } else {
                Verify.If(true, "上传图片失败");
            }
            res.SetSuccess("成功", path);
        } catch (Exception e) {
            e.printStackTrace();
            res.SetError(e.getMessage());
        }
        return res;
    }
    //</editor-fold>

    //<editor-fold desc="富文本上传文件">

    /**
     * 富文本上传文件
     *
     * @param file 前端上传的文件
     * @return
     */
    public String DitorFileUpload(MultipartFile file, String UserName, int VerifyType) {
        //上传路径
        String path;
        //返回路径
        String retUrl;
        JSONObject jo = new JSONObject();
        try {
            Date date = new Date(new Date().getTime());
            //上传目录地址 按时间与用户GUID产生文件夹
            String uploadDir = UploadPath.UploadLocalPath + UploadPath.CommUrl;
            retUrl = UploadPath.RootUrl + UploadPath.CommUrl;
            //验证后缀名
            Verify.If(!SuffixVerify(file, VerifyType), "请上传正确的文件");
            //如果上传目录不存在则创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //获取上传文件的后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //上传文件名
            String fileName = UUID.randomUUID().toString() + suffix;
            path = uploadDir + "/" + fileName;
            retUrl += "/" + fileName;
            //服务器端保存文件对象
            File serverFile = new File(path);
            //将上传的文件写入到服务器端的文件内
            file.transferTo(serverFile);
            long size = file.getSize();
            jo.put("state", "SUCCESS");
            jo.put("original", file.getOriginalFilename());//原来的文件名
            jo.put("size", size);       //文件大小
            jo.put("title", fileName);       //随意，代表的是鼠标经过图片时显示的文字
            jo.put("type", suffix);     //文件后缀名
            jo.put("url", retUrl);//这里的url字段表示的是上传后的图片在图片服务器的完整地址（http://ip:端口/***/***/***.jpg）
        } catch (Exception e) {
            //打印错误的堆栈信息
            e.printStackTrace();
            return "";
        }
        return jo.toJSONString();
    }
    //</editor-fold>

    //<editor-fold desc="后缀名验证">

    /**
     * 后缀名验证
     *
     * @param file
     * @param VerifyType
     * @return
     */
    public Boolean SuffixVerify(MultipartFile file, int VerifyType) {
        //返回标识
        Boolean flag = true;
        //获取上传文件的后缀名 转换为小写 便于判断
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        if (suffix.equals(EnumUtil.UploadVerifyType.图片.getValue().toString())) {
            switch (suffix) {
                case ".jpg":
                case ".jpeg":
                case ".png":
                case ".gif":
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
        } else if (suffix.equals(EnumUtil.UploadVerifyType.文本.getValue().toString())) {
            switch (suffix) {
                case ".txt":
                case ".xls":
                case ".xlsx":
                case ".doc":
                case ".docx":
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
        }
        return flag;
    }
    //</editor-fold>
}
