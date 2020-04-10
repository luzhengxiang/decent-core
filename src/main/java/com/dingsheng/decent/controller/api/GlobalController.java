package com.dingsheng.decent.controller.api;

import com.dingsheng.decent.util.db.DatabaseUtil;
import com.dingsheng.decent.util.encrypt.MD5;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

@Controller
@RequestMapping(value="")
public class GlobalController {
    Logger logger = LoggerFactory.getLogger(GlobalController.class);
	/*@RequestMapping(value = "/500.html")
	public String get500Page() {
		return "500";
	}

	@RequestMapping(value = "/404.html")
	public String get404Page() {
		return "404";
	}*/
//	@ApiOperation(value = "获取图形验证码", notes = "获取图形验证码")
	@GetMapping(value = "/verifyCode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {

		Properties properties = new Properties();
		properties.put("kaptcha.image.width", "100");
		properties.put("kaptcha.image.height", "40");
		properties.put("kaptcha.textproducer.char.string", "0123456789");
		properties.put("kaptcha.textproducer.char.length", "4");
		properties.put("kaptcha.border", "no");
		properties.put("kaptcha.border.color", "105,179,90");
		properties.put("kaptcha.border.thickness", "1");
		properties.put("kaptcha.textproducer.font.color", "black");
		properties.put("kaptcha.textproducer.font.size", "30");
		properties.put("kaptcha.textproducer.font.names", "BKamrnBd");
		properties.put("kaptcha.textproducer.char.space", "3");
		properties.put("kaptcha.noise.color", "gray");//"red");
		properties.put("kaptcha.obscurificator.impl", "");//"com.google.code.kaptcha.impl.ShadowGimpy");
		Config config = new Config(properties);

		DefaultKaptcha kaptch = new DefaultKaptcha();
		kaptch.setConfig(config);

		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = kaptch.createText();
		String codecode = MD5.encode(MD5.encode(capText)+"TH");

		BufferedImage bi = kaptch.createImage(capText);

		String vcId = ""+ DatabaseUtil.getDatabasePriykey();
//		if(RedisService.enabled){
//			RedisService.set(RedisServerKeys.IMG_VC+vcId, codecode, 20*60);
//			CookieUtil.setCookie(response,"vcId",vcId,20*60);
//		}else{
//			CookieUtil.setCookie(response, "vc", codecode, 0);
//		}
//		CookieUtil.setCookie(response, "vct", capText, 0);
		
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (Exception e) {
			logger.warn("生成图片验证码失败："+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(out!=null)
				out.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}


//	@PostMapping(value="/upload")
//	@ResponseBody
//	public String upload(@RequestParam MultipartFile picFile){
//		Map<String,String>m = new HashMap<>();
//		int uploadMaxSizeLimit = StringUtil.getNumberDefault(PropertyConfig.getPropertyValue("upload.maxSizeLimit"), "0");
//		m.put("status", "false");
//		if(uploadMaxSizeLimit>0 && picFile.getSize()>uploadMaxSizeLimit){
//			m.put("resultMsg", "上传的文件超过了大小限制！");
//		}else{
//			String fn = ""+DatabaseUtil.getDatabasePriykey()+FileUtil.getExt(picFile.getOriginalFilename()),
//					fp = PropertyConfig.getPropertyValue("upload.tempPath")+File.separator+fn;
//			try{
//				FileUtil.saveAsFile(picFile.getBytes(), fp);
//				m.put("status", "true");
//				m.put("filePath", fn);
//			}catch(Exception e){
//				m.put("resultMsg", e.getMessage());
//			}
//		}
//		return new Gson().toJson(m);
//	}
	/*
	@RequestMapping(value = "/img{tmp}-{sub}-{type}-{id}")
	public void img(HttpServletRequest request, HttpServletResponse response,
			@PathVariable int tmp, @PathVariable String type,
			@PathVariable String id, @PathVariable String sub) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(ImageUtils.readImage(id+"."+type, tmp!=1,sub), type, out);
			out.flush();
		} catch (Exception e) {
			logger.warn("图片读取失败："+e.getMessage());
		} finally {
			try {
				if(out!=null)
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/


}