package com.dingsheng.decent.util.common;//package com.dingsheng.yitui.common.util.commons;
//
//import com.dingsheng.yitui.common.util.conf.PropertyConfig;
//import com.dingsheng.yitui.common.util.db.DatabaseUtil;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//
//
//public class FileUtil {
//
//	public final static String TEMP_PATH = PropertyConfig.getPropertyValue("upload.tempPath")+File.separator;
//	public final static String REAL_PATH = PropertyConfig.getPropertyValue("upload.savePath")+File.separator;
//
//	public final static String DOWNLOAD_PATH = "download"+File.separator;
//
//	private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
//
//	public static void saveAsFile(InputStream is,String fn) throws Exception{
//		byte[]b = new byte[0];
//		int read = 0;
//		int i = 0;
//		while((read=is.read())!=-1){
//			b[i] = (byte) read;
//			i++;
//		}
//		saveAsFile(b, fn);
//	}
//	public static void saveAsFile(byte[] b,String fn) throws Exception{
//		OutputStream os=null;
//		try {
//			File f = new File(fn);
//			if(!f.exists()){
//				createPath(f);
//				if(!f.createNewFile()){
//					throw new Exception("文件创建失败");
//				}
//			}
//			os = new FileOutputStream(f);
//			os.write(b);
//			os.flush();
//		} catch (Exception e) {
//			logger.error("文件保存失败",e);
//			throw new Exception("文件保存失败");
//		}finally{
//			if(os!=null)
//				try {
//					os.close();
//				} catch (IOException e) {
//					logger.error("文件关闭时失败",e);
//					throw new Exception("保存时失败");
//				}
//		}
//	}
//	public static void saveAsTempFile(InputStream is,String fn) throws Exception{
//		saveAsFile(is, TEMP_PATH+fn);
//	}
//	public static void saveAsTempFile(byte[] b, String fn) throws Exception{
//		saveAsFile(b, TEMP_PATH+fn);
//	}
//	public static void moveFileTo(String fromFn,String toFn) throws Exception{
//		FileUtils.moveFile(new File(fromFn), new File(toFn));
//	}
//	public static void moveTempFileTo(String fromFn,String toFn) throws Exception{
//		FileUtil.moveFileTo(TEMP_PATH + fromFn,REAL_PATH + toFn);
//	}
//	public static boolean deleteFile(String fn){
//		File f = new File(fn);
//		if(f.isDirectory())return false;
//		return FileUtils.deleteQuietly(f);
//	}
//	public static boolean deleteTempFile(String fn){
//		return deleteFile(TEMP_PATH+fn);
//	}
//	public static String getExtName(String fn){
//		if(fn==null||"".equals(fn))return "";
//		String[] fns = fn.split("\\.");
//		return fns.length>1?fns[fns.length-1]:"";
//	}
//	public static String getExt(String fn){
//		if(fn==null||"".equals(fn))return "";
//		String[] fns = fn.split("\\.");
//		return fns.length>1?("."+fns[fns.length-1]):"";
//	}
//	public static boolean createPath(File f){
//		File p = f;
//		if(!p.isFile()){
//			p = p.getParentFile();
//		}
//		/*if(!p.exists()) {
//			return p.mkdirs();
//		}*/
//		return p.exists() || p.mkdirs();
//	}
//	public static String getTempFile(String fn){
//		return TEMP_PATH+fn;
//	}
//	public static String getRealFile(String t, String fn){
//		return REAL_PATH + t + File.separator + fn;
//	}
//	public static String downImage(String url, String formatName){
//		//TODO 全部下载到临时文件夹中的download里，以databaseutil获取主键作为文件名,格式名称作为后缀
//		String fn = DOWNLOAD_PATH+ DatabaseUtil.getDatabasePriykey()+"."+formatName;
//
//		try {
//			ImageUtils.format(ImageUtils.readImage(url), TEMP_PATH+fn);
//		} catch (IOException e) {
//			logger.error("微信头像下载失败",e);
//		}
//		//返回相对临时文件夹的文件地址
//		return fn;
//	}
//
//}