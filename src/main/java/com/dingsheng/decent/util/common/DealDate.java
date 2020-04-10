package com.dingsheng.decent.util.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DealDate {
	private static Logger logger = LoggerFactory.getLogger(DealDate.class);

	public static final int YEAR = 1;
	public static final int MONTH = 2;
	public static final int DAY = 3;
	public static final int WEEK = 4;
	public static final int HOURS = 5;
	public static final int MINUTE = 6;
	public static final int SECOND = 7;
	public static final long TIME_AREA;
	private static int[] lastDays = {-1,31,28,31,30,31,30,31,31,30,31,30,31};
	
	static{
		TIME_AREA = Timestamp.valueOf("1970-01-01 00:00:00.000").getTime(); 
	}
	private DealDate(){
	}
	
	public static long dateDiff(int flag,Calendar c1,Calendar c2){
		if( c1==null || c2==null )throw new RuntimeException("Null Pointer In Two Calendar instance");
		//long distance = c2.getTimeInMillis() - c1.getTimeInMillis();
		switch(flag){
		case DealDate.YEAR :
			return c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		case DealDate.MONTH :
			return (c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH)+12*(c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR)));
		case DealDate.WEEK :
			return ((c2.getTimeInMillis()-TIME_AREA)/(86400000L*7) 
					- (c1.getTimeInMillis()-TIME_AREA)/(86400000L*7));
		case DealDate.DAY :
			return ((c2.getTimeInMillis()-TIME_AREA)/86400000 
					- (c1.getTimeInMillis()-TIME_AREA)/86400000);
		case DealDate.HOURS :
			return ((c2.getTimeInMillis()-TIME_AREA)/3600000 
					- (c1.getTimeInMillis()-TIME_AREA)/3600000);
		case DealDate.MINUTE :
			return ((c2.getTimeInMillis()-TIME_AREA)/60000 
					- (c1.getTimeInMillis()-TIME_AREA)/60000);
		case DealDate.SECOND :
			return ((c2.getTimeInMillis()-TIME_AREA)/1000 
					- (c1.getTimeInMillis()-TIME_AREA)/1000);
		default :
			throw new RuntimeException("Flag Not Found:"+flag);
		}
	}
	public static long dateDiff(int flag, Date t1, Date t2){
		if( t1==null || t2==null ) throw new IllegalArgumentException("Null Pointer In Two java.util.Date Argument");
		Calendar c1,c2;
		(c1 = Calendar.getInstance()).setTimeInMillis(t1.getTime());
		(c2 = Calendar.getInstance()).setTimeInMillis(t2.getTime());
		return DealDate.dateDiff(flag, c1, c2);
	}
	public static boolean isLeapYear(int year){
		return year%100==0?year%400==0:year%4==0;
	}
	public static boolean isLeapYear(Calendar t){
		return null==t?false:isLeapYear(t.get(Calendar.YEAR));
	}
	public static boolean isLeapYear(Date t){
		if(null==t)return false;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t.getTime());
		return isLeapYear(c.get(Calendar.YEAR));
	}
	public static Calendar dateAdd(int flag,int increase,Calendar tar){
		if(tar==null)throw new RuntimeException("Null Pointer Calendar");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(tar.getTimeInMillis());

		if(increase==0)return c;

		switch(flag){
		case DealDate.YEAR :
			c.add(Calendar.YEAR,increase);
			break;
		case DealDate.MONTH :
			c.add(Calendar.MONTH,increase);
			break;
		case DealDate.DAY :
			c.add(Calendar.DATE,increase);
			break;
		case DealDate.WEEK :
			c.add(Calendar.DATE,increase*7);
			break;
		case DealDate.HOURS :
			c.add(Calendar.HOUR_OF_DAY,increase);
			break;
		case DealDate.MINUTE :
			c.add(Calendar.MINUTE,increase);
			break;
		case DealDate.SECOND :
			c.add(Calendar.SECOND,increase);
			break;
		default :
			throw new RuntimeException("Flag Not Found:"+flag);
		}
		return c;
	}
	public static <T extends Date> T dateAdd(int flag,int increase,final T t){
		if(t==null)throw new RuntimeException("Null Pointer java.util.Date");
		Calendar c = Calendar.getInstance();
		c.setTime(t);
		c = dateAdd(flag, increase, c);
		T to = null;
		try {
			to = (T)t.getClass().getConstructor(long.class).newInstance(c.getTimeInMillis());
			return to;
		} catch (Exception e) {
			throw new RuntimeException(String.format("创建日期对象失败：%s",e.getMessage()));
		}
	}
	public static String toShortDate(Calendar c){
		if(c==null)return "";
		String m = "0"+(c.get(Calendar.MONTH)+1),d="0"+c.get(Calendar.DAY_OF_MONTH);
		return c.get(Calendar.YEAR)+"-"+m.substring(m.length()-2,m.length())+"-"+d.substring(d.length()-2,d.length());
	}
	public static String toShortDate(Date t){
		if(t==null)return "";
		return format(t);
	}
	public static String toShortDate(String t){
		return toShortDate(createDate(t, Timestamp.class));
	}
	public static String toFullDateTime(Calendar t){
		if(t==null)return "";
		String y,m,d,h,mi,se,ms;
		y = "" + t.get(Calendar.YEAR);
		m = "0" + (t.get(Calendar.MONTH)+1);
		d = "0" + t.get(Calendar.DAY_OF_MONTH);
		h = "0" + t.get(Calendar.HOUR_OF_DAY);
		mi = "0" + t.get(Calendar.MINUTE);
		se = "0" + t.get(Calendar.SECOND);
		ms = "" + t.get(Calendar.MILLISECOND)+"00";
		m = m.substring(m.length()-2,m.length());
		d = d.substring(d.length()-2,d.length());
		h = h.substring(h.length()-2,h.length());
		mi = mi.substring(mi.length()-2,mi.length());
		se = se.substring(se.length()-2,se.length());
		ms = ms.substring(0,3);
		return y+"-"+m+"-"+d+
			" " + h +":"+ mi +":"+ se
			+"."+ ms;
	}
	public static String toFullDateTime(Date t){
		if(t==null)return "";
		return format(t, "yyyy-MM-dd HH:mm:ss.SSS");
	}
	public static String toFullDateTime(String t){
		return toFullDateTime(createDate(t, Timestamp.class));
	}
	public static Date createDate(){
		return createDate(toShortDate(Calendar.getInstance()), Date.class);
	}
	public static <T extends Date> T createDate(Class<T> c){
		return createDate(toShortDate(Calendar.getInstance()), c);
	}
	public static Calendar createCalendar(String ts){
		Timestamp t = createDate(ts, Timestamp.class);
		if(t==null)return null;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t.getTime());
		return c;
	}
	public static <T extends Date> T createDate(String ts,Class<T> c){
		Timestamp t;
		try{
			t = Timestamp.valueOf(ts);
		}catch(Exception e){
			try{
				t = Timestamp.valueOf(ts + ".000");
			}catch(Exception ex){
				try{
					t = Timestamp.valueOf(ts+" 00:00:00.000");
				}catch(Exception ex1){
					try{
						String fs[] = ts.split("-");
						fs[1] = "0"+fs[1];
						fs[2] = "0"+fs[2];
						fs[1] = fs[1].substring(fs[1].length()-2,fs[1].length());
						fs[2] = fs[2].substring(fs[2].length()-2,fs[2].length());
						t = Timestamp.valueOf(fs[0]+"-"+fs[1]+"-"+fs[2]+" 00:00:00.000");
					}catch(Exception ex2){
						return null;
					}
				}
			}
		}
		T to = null;
		try {
			to = c.getConstructor(long.class).newInstance(t.getTime());
//			to.setTime(t.getTime());
		} catch (Exception e){
			logger.warn(String.format("创建日期对象失败：%s",e.getMessage()), e);
		}
		return to;
	}
	/*
	 * getLastDayOfMonth()方法获取系统当前日期所在月的最大天数
	 */
	public static int getLastDayOfMonth(){
		int year = lastDays[0]/100;
		int month = lastDays[0]%100;
		return lastDays[month]+(month==2?(isLeapYear(year)?1:0):0);
	}
	public static int getLastDayOfMonth(int year,int month){
		if(month>12 || month<1){
			return 0;
		}
		return lastDays[month]+(month==2?(isLeapYear(year)?1:0):0);
	}
	public static int getLastDayOfMonth(Calendar t){
		if(null==t)return 0;
		return getLastDayOfMonth(t.get(Calendar.YEAR),t.get(Calendar.MONTH)+1);
	}
	public static int getLastDayOfMonth(Date t){
		if(null==t)return 0;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t.getTime());
		return getLastDayOfMonth(c);
	}
	public static int getLastDayOfMonth(String time){
		return getLastDayOfMonth(createDate(time,Date.class));
	}/**
	 * 取当前月最后一天日期
	 *
	 * @return
	 */
	public static Date getLastDateOfMonth(){
		return dateAdd(DAY, -1, dateAdd(MONTH, 1, getFirstDateOfMonth()));
	}
	public static Date getLastDateOfMonth(int year,int month){
		String m = "00"+month,d = "00"+getLastDayOfMonth(year, month);
		return createDate(year + "-" + m.substring(m.length() - 2, m.length()) + "-" + d.substring(d.length() - 2, d
				.length()), Date.class);
	}
	public static Calendar getLastDateOfMonth(Calendar t){
		if(null==t)return createCalendar(toFullDateTime(getLastDateOfMonth()));
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t.getTimeInMillis());
		c.set(Calendar.DAY_OF_MONTH, 1);
		return dateAdd(DAY, -1, dateAdd(MONTH, 1, t));
	}
	public static <T extends Date> T getLastDateOfMonth(T t){
		if (null == t) throw new IllegalArgumentException("泛型参数t为null");
		Calendar c = getFirstDateOfMonth(Calendar.getInstance());
		return createDate(toFullDateTime(dateAdd(DAY, -1, dateAdd(MONTH, 1, c))), (Class<T>) t.getClass());
	}
	public static Date getLastDateOfMonth(String t){
		if(null==t)return createDate(toFullDateTime(getLastDateOfMonth()), Date.class);
		return getLastDateOfMonth(createDate(t, Date.class));
	}
	/**
	 * 取每月第一天日期
	 * @return
	 */
	public static Date getFirstDateOfMonth(){
		Date d = null;
		return getFirstDateOfMonth(d);
	}
	public static Date getFirstDateOfMonth(int year,int month){
		return createDate(year+"-"+month+"-1",Date.class);
	}
	public static <T extends Date> T getFirstDateOfMonth(T t){
		if(null== t) return null;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t.getTime());
		c.set(Calendar.DAY_OF_MONTH,1);
		return createDate(toFullDateTime(c), (Class<T>)t.getClass());
	}
	public static Calendar getFirstDateOfMonth(Calendar t) {
		if(null==t)return createCalendar(toFullDateTime(getFirstDateOfMonth()));
		t.set(Calendar.DAY_OF_MONTH, 1);
		return t;
	}
	public static Date getFirstDateOfMonth(String t){
		if(null==t)return createDate(toFullDateTime(getFirstDateOfMonth()), Date.class);
		return getFirstDateOfMonth(createDate(t, Date.class));
	}


	/**
	 * 取两日期的大者，按日期的各个部分进行比较，如：DealDate.YEAR 只判断年份，DealDate.DATE
	 * @param flag
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static Calendar max(int flag,Calendar t1,Calendar t2){
		try{
			if(t1==null)return t2;
			if(t2==null)return t1;
			return dateDiff(flag,t1,t2)>=0?t2:t1;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Timestamp max(int flag,Timestamp t1,Timestamp t2){
		try{
			if(t1==null)return t2;
			if(t2==null)return t1;
			return dateDiff(flag,t1,t2)>=0?t2:t1;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static java.sql.Date max(int flag,java.sql.Date t1,java.sql.Date t2){
		try{
			if(t1==null)return t2;
			if(t2==null)return t1;
			return dateDiff(flag,t1,t2)>=0?t2:t1;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Date max(int flag, Date t1, Date t2){
		try{
			if(t1==null)return t2;
			if(t2==null)return t1;
			return dateDiff(flag,t1,t2)>=0?t2:t1;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Date max(int flag,String t,String t1,String t2){
		return max(flag,createDate(t1,Date.class),createDate(t2,Date.class));
	}
	public static Calendar min(int flag,Calendar t1,Calendar t2){
		try{
			if(t1==null || t2==null)return null;
			return dateDiff(flag,t1,t2)>=0?t1:t2;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Timestamp min(int flag,Timestamp t1,Timestamp t2){
		try{
			if(t1==null || t2==null)return null;
			return dateDiff(flag,t1,t2)>=0?t1:t2;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static java.sql.Date min(int flag,java.sql.Date t1,java.sql.Date t2){
		try{
			if(t1==null || t2==null)return null;
			return dateDiff(flag,t1,t2)>=0?t1:t2;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Date min(int flag, Date t1, Date t2){
		try{
			if(t1==null || t2==null)return null;
			return dateDiff(flag,t1,t2)>=0?t1:t2;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Date min(int flag,String t1,String t2){
		return min(flag,createDate(t1, Date.class),createDate(t2,Date.class));
	}
	public static boolean isBetween(int flag,Calendar t,Calendar t1,Calendar t2){
		boolean true1=true,true2=true;
		try{
			if(t==null) return false;
			if(null==t1) true1=true;
			else{
				true1 = dateDiff(flag,t1,t)>=0?true:false;
			}
			if(null==t2){
				true2=true;
			}else{
				true2 = dateDiff(flag,t,t2)>=0?true:false;
			}
		}catch(Exception e){
			return false;
		}
		return (true1 && true2);
	}
	public static boolean isBetween(int flag, Date t, Date t1, Date t2){
		boolean true1=true,true2=true;
		try{
			if(t==null) return false;
			if(null==t1) true1=true;
			else{
				true1 = dateDiff(flag,t1,t)>=0?true:false;
			}
			if(null==t2){
				true2=true;
			}else{
				true2 = dateDiff(flag,t,t2)>=0?true:false;
			}
		}catch(Exception e){
			return false;
		}
		return (true1 && true2);
	}
	public static boolean isBetween(int flag,String t,String t1,String t2){
		return isBetween(flag, createCalendar(t), createCalendar(t1),
				createCalendar(t2));
	}
	public static int getFullYear(Calendar t){
		if(null==t)return 0;
		return t.get(Calendar.YEAR);
	}
	public static int getFullYear(Date t){
		if(null==t)return 0;
		Calendar c = Calendar.getInstance();
		c.setTime(t);
		return getFullYear(c);
	}
	public static int getFullYear(String t){
		if(null==t)return 0;
		return getFullYear(createCalendar(t));
	}

	public static int getMonth(Calendar t){
		if(null==t)return 0;
		return t.get(Calendar.MONTH)+1;
	}
	public static int getMonth(Date t){
		if(null==t)return 0;
		Calendar c = Calendar.getInstance();
		c.setTime(t);
		return getMonth(c);
	}
	public static int getMonth(String t){
		if(null==t)return 0;
		return getMonth(createCalendar(t));
	}
	public static int getMonth(){
		return getMonth(createDate());
	}
	
	/**
	 * 格式化成 yyyy-MM-dd
	 * 
	 * */
	public static String format(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	/**
	 * 格式化成  yyyy-MM-dd HH:mm:ss
	 * 
	 * */
	public static String format(Date date,String pattern){
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static int getDayOfMonth(Calendar date) {
		if(date==null)return 0;
		return date.get(Calendar.DAY_OF_MONTH);
	}
	public static int getDayOfMonth(Date date) {
		if(date==null)return 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getDayOfMonth(c);
	}

	public static int getHours(Calendar date) {
		if(date==null)return 0;
		return date.get(Calendar.HOUR_OF_DAY);
	}
	public static int getHours(Date date) {
		if(date==null)return 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getHours(c);
	}

	public static int getMinutes(Calendar date) {
		if(date==null)return 0;
		return date.get(Calendar.MINUTE);
	}

	public static int getMinutes(Date date) {
		if(date==null)return 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getMinutes(c);
	}

	public static int getSeconds(Calendar date) {
		if(date==null)return 0;
		return date.get(Calendar.MINUTE);
	}

	public static int getSeconds(Date date) {
		if(date==null)return 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getSeconds(c);
	}


}