package com.dingsheng.decent.util.common;

import com.dingsheng.decent.util.core.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
	private final static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	/**
	 * 发邮件
	 * @param email
	 * @param subject
	 * @param body
	 * @throws UnsupportedEncodingException
	 */
	public static void sendEmail(String email, String subject, String body)
			throws UnsupportedEncodingException,NoSuchProviderException,MessagingException,Exception {
		try {
			logger.debug(email+"=======["+subject+"]======"+body);
			
			Properties props = new Properties();
			String server= PropertyConfig.getPropertyValue("mail.smtp.host");//"smtp.qq.com";
			props.put("mail.smtp.host", server);
			props.put("mail.smtp.port", PropertyConfig.getPropertyValue("mail.smtp.port"));
			props.put("mail.smtp.auth", "true");
			String user=PropertyConfig.getPropertyValue("mail.Account");
			String password=PropertyConfig.getPropertyValue("mail.Pwd");
			String from="admin"; 
			Transport transport = null;
			Session session = Session.getDefaultInstance(props, null);
			transport = session.getTransport("smtp");
			transport.connect(server, user, password);
			MimeMessage msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = new InternetAddress(user, from,
					"UTF-8");
			msg.setFrom(fromAddress);
			
			String[] emails=email.split(";");
			
			InternetAddress[] toAddress = new InternetAddress[emails.length];
			for(int i=0;i<emails.length;i++){
				toAddress[i] = new InternetAddress(emails[i]);	
			}
			
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			
			msg.setSubject(subject, "UTF-8");
			
			Multipart mainPart=new MimeMultipart();
			//创建一个包含Html内容的MimeBodyPart
			MimeBodyPart htmlText=new MimeBodyPart();
			//设置HTML内容
			htmlText.setContent(body,"text/html;charset=utf-8");  //"text/html;charset=utf-8"
			mainPart.addBodyPart(htmlText);

			msg.setContent(mainPart);
			
			//msg.setText(body, "UTF-8");
			//msg.saveChanges();
			//transport.sendMessage(msg, msg.getAllRecipients());
			transport.sendMessage(msg, msg.getAllRecipients());
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			throw e;
		} catch (MessagingException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
