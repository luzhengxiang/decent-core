package com.dingsheng.decent.util.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Timestamp getPastDay(int d){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1 * d);
		return new Timestamp(c.getTimeInMillis());
	}
	
	/**
	 * 格式化成 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return getDate(date,"yyyy-MM-dd");
	}

	/**
	 * 获取格式化的时间 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @param formart
	 * @return
	 */
	public static String getDate(Date date, String formart) {
		SimpleDateFormat df = new SimpleDateFormat(formart);
		return df.format(date);
	}

	/**
	 * 获取下个周期的日期
	 * @param d 起始时间
	 * @param number 间隔周期
	 * @param periodType 日期类型 Calendar.MONTH
	 * @return
	 */
	public static Date getNextPeriodDate(Date d, int number,int periodType) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_MONTH, number);
		return cal.getTime();
	}

	/**
	 * 获取上个周期的日期
	 * @param d 起始时间
	 * @param number 间隔周期
	 * @param periodType 日期类型 Calendar.MONTH
	 * @return
	 */
	public static Date getPrePeriodDate(Date d, int number,int periodType) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(periodType, -number);
		return cal.getTime();
	}

}
