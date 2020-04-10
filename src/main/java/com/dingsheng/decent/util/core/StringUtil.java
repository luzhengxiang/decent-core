package com.dingsheng.decent.util.core;

import com.dingsheng.decent.util.common.DealDate;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
	public static final String maskS = "***********************************************************************************";
	public static final String ZERO_STR = "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
	public static BigDecimal getBigDecimalDefault(String v, String d) {
		return new BigDecimal(getDefault(v, d));
	}

	private static final String REGEX_MOBILE_EXACT = "^1(3|4|5|6|7|8|9)\\d{9}$";
	private static final Pattern PATTERN_REGEX_MOBILE_EXACT = Pattern.compile(REGEX_MOBILE_EXACT);


	/**
	 * 字符串v如果为空串，则返回默认值d，否则为v本身。
	 * 
	 * @param v
	 * @param d
	 * @return
	 */
	public static String getDefault(String v, String d) {
		return isEmpty(v) ? d : v;
	}
	/**
	 * 字符串转成Long
	 * 
	 * @param v
	 * @param d
	 * @return
	 */
	public static Long getLongDefault(String v, String d) {
		if (isNumeric(v) && isNumeric(d)) {
			return Long.parseLong(getDefault(v, d));
		}
		return null;
	}

	/**
	 * 判断字符串是否为空串
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim());
	}

	/**
	 * 判断字符串是否为有效手机号
	 * 
	 * @param m
	 * @return
	 */
	public static boolean isTelephone(String m) {
		return !isEmpty(m) && m.matches("^1[3-9]\\d{9}$");
	}

	/**
	 * 判断字符串是否为Email格式
	 * 
	 * @param em
	 * @return
	 */
	public static boolean isEmail(String em) {
		return !isEmpty(em)
				&& em.matches("^([a-zA-Z0-9_\\.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
	}

	/**
	 * 判断是否为有效密码，格式：字母加数字，长度6-16
	 * 
	 * @param m
	 * @return
	 */
	public static boolean isPwd(String m) {
		return !isEmpty(m) && m.matches("^[a-zA-Z0-9]{6,16}$");
	}

	/**
	 * 把字符串以splitBy为分隔符分成N个字符串，返回类型为List<String>
	 * 
	 * @param s
	 * @param splitBy
	 * @return
	 */
	public static List<String> splitToList(String s, String splitBy) {
		List<String> l = new ArrayList<String>();
		if (!isEmpty(s) && !isEmpty(splitBy)) {
			for (String f : s.split(splitBy)) {
				l.add(f);
			}
		}
		return l;
	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toLowerFirstChar(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperFirstChar(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	public static String getPattern(String fs) {
		return getPatternString(fs, "pattern *= *(.*?)(,|$)", 1).replace("。",
				".").replace("，", ",");

		// if(StringUtil.isEmpty(fs))return "";
		// Pattern p = Pattern.compile("pattern *= *(.*?)(,|$)");
		// Matcher m = p.matcher(fs);
		//
		// if(m.find()) {
		// return m.group(1).replace("。", ".").replace("，", ",");
		// }
		// return "";
	}
	public static String format(Object o, String pattern) {
		if (StringUtil.isEmpty(pattern))
			return String.valueOf(o);
		if (o instanceof Date) {
			return new SimpleDateFormat(pattern).format(o);
		} else if (o instanceof BigDecimal || o instanceof Integer
				|| o instanceof Float || o instanceof Double
				|| o instanceof Long) {
			return new DecimalFormat(pattern).format(o);
		} else
			return String.valueOf(o);
	}

	/**
	 * 获取指定类定义的成员变量（可获取父类成员变量）
	 * @param c 指定类
	 * @param field 指定成员名称
	 * @return Field
	 * @throws Exception
	 */
	public static Field getDeclaredField(Class<?> c, String field) throws Exception{
		if(c==null) throw new IllegalArgumentException(field + "变量不存在!");
		try{
			return c.getDeclaredField(field);
		}catch(NoSuchFieldException ex){
			if(c!=Object.class)return getDeclaredField(c.getSuperclass(), field);
		}
		throw new IllegalArgumentException(field + "变量不存在!");
	}

	/**
	 * 获取bean中的属性值
	 * 
	 * @param o
	 *            bean对象
	 * @param p
	 *            属性路径列表，一般直接使用 getValue(Object o,String p)
	 * @return
	 * @throws Exception
	 */
	public static String getValue(Object o, List<String> p) throws Exception {
		String ps = p.get(0);
		p.remove(0);

		try {
			if (p.size() > 0) {
				Field f =  getDeclaredField(o.getClass(),ps);
				f.setAccessible(true);
				return getValue(f.get(o), p);
			} else {
				String[] pps = ps.split(",", 2);
				Field f = getDeclaredField(o.getClass(),pps[0]);
				f.setAccessible(true);
				return format(f.get(o), getPattern(pps.length > 1
						? pps[1]
						: null));
				// return String.valueOf(f.get(o));
			}
		} catch (SecurityException e) {
			throw new Exception(ps + "变量不存在!");
		} catch (IllegalArgumentException e) {
			throw new Exception(ps + "变量不存在!");
		} catch (NoSuchFieldException e) {
			throw new Exception(ps + "变量不存在!");
		} catch (IllegalAccessException e) {
			throw new Exception(ps + "变量不存在!");
		} catch (Exception e) {
			throw new Exception(ps + "." + e.getMessage());
		}

	}

	/**
	 * 获取bean中属性值。
	 * 
	 * @param o
	 *            bean对象
	 * @param p
	 *            属性路径，可以多层访问，如
	 *            StringUtil.getValue(member,"account.totalAssets");
	 * @return
	 * @throws Exception
	 */
	public static String getValue(Object o, String p) throws Exception {
		return getValue(o, splitToList(p, "\\."));
	}

	/**
	 * 将 bean中的属性 替换到模板字符串中对应的{propertyName}中
	 * 
	 * @param s
	 *            模板字符串
	 * @param o
	 *            bean数据对象
	 * @return
	 * @throws Exception
	 */
	public static String fillBean(String s, Object o) {
		if (o == null)
			return s;
		if(isEmpty(s))return "";
		String v = null;
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("\\{(.*?)\\}");
		Matcher m = p.matcher(s);
		int i = 0;
		while (m.find()) {
			sb.append(s.substring(i, m.start()));
			try {
				v = getValue(o, m.group(1));
				sb.append(v);
			} catch (Exception e) {
				sb.append(m.group(0));
			}
			i = m.start() + m.group(0).length();
		}
		sb.append(s.substring(i));
		return sb.toString();
	}

	/**
	 * 获取一天划分成份对应中文名称
	 * 
	 * @return
	 */
	public static String getDayPart() {
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		if (h >= 20)
			return "晚上";
		else if (h >= 13)
			return "下午";
		else if (h >= 11)
			return "中午";
		else
			return "上午";
	}

	/**
	 * 判断字符串是否为有效数值，是则返回true，否则为false
	 * 
	 * @param page
	 *            被检查字符串
	 * @param checkFloat
	 *            判断是否包含小数点
	 * @return
	 */
	public static boolean isNumeric(String page, boolean checkFloat) {
		if (checkFloat) {
			return page != null && page.matches("^-?\\d+(\\.\\d+)?$");
		} else {
			return page != null && page.matches("^-?\\d+$");
		}
	}

	/**
	 * 判断字符串是否为纯数字组成，是则返回true，否则为false
	 * 
	 * @param page
	 *            被检查字符串
	 * @return
	 */
	public static boolean isNumeric(String page) {
		return isNumeric(page, false);
	}

	/**
	 * 将List以split串为分隔符合并成新的字符串
	 * 
	 * @param l
	 * @param split
	 * @return
	 */
	public static String join(List<?> l, String split) {
		StringBuffer sb = new StringBuffer();
		for (Object s : l) {
			sb.append(s).append(split);
		}
		if (sb.length() > 0)
			sb.delete(sb.length() - split.length(), sb.length());
		return sb.toString();
	}

	/**
	 * 将Object数组以split串为分隔符合并成新的字符串。
	 * 
	 * @param l
	 * @param split
	 * @return
	 */
	public static String join(Object[] l, String split) {
		StringBuffer sb = new StringBuffer();
		for (Object s : l) {
			sb.append(s).append(split);
		}
		if (sb.length() > 0)
			sb.delete(sb.length() - split.length(), sb.length());
		return sb.toString();
	}

	/**
	 * 将一个数值类型字符串按指定的进阶数和单位输出，如：1025输出成 1KB可以这样调用
	 * getBaseNum("1024","1024","KB,B")
	 * 
	 * @param v
	 * @param base
	 * @param unit
	 * @return
	 */
	public static String getBaseNum(String v, String base, String unit) {
		if (isNumeric(v, true) && isNumeric(base) && !isEmpty(unit)) {
			StringBuffer sb = new StringBuffer();
			long bv = new BigDecimal(v).setScale(0, BigDecimal.ROUND_DOWN)
					.longValue();
			String[] us = unit.split(",");
			int p = Integer.parseInt(base, 10);
			for (int i = 1; i <= us.length; i++) {
				long mod = bv % p;
				if (mod > 0) {
					sb.insert(0, us[i - 1]).insert(0, mod);
				}
				if ((bv = bv / p) <= 0)
					break;
			}
			return sb.toString();
		}
		return v;
	}
	public static String getPatternString(String source, String pattern,
			int index) {
		if (StringUtil.isEmpty(source))
			return "";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		while (m.find()) {
			return m.group(index);
		}
		return "";
	}
	/**
	 * 简化方法获取XML的标签值
	 * 
	 * @param source
	 * @param tag
	 * @return
	 */
	public static String getTagText(String source, String tag) {
		return getPatternString(source, "<" + tag
				+ ">(?:<!\\[CDATA\\[)?(.*?)(\\]\\]>)*</" + tag + ">", 1);
	}
	/**
	 * 将queryString串转成map
	 * 
	 * @param qp
	 * @return
	 */
	public static Map<String, String> queryToMap(String qp) {
		HashMap<String, String> k = new HashMap<String, String>();
		if (!isEmpty(qp)) {
			Pattern p = Pattern.compile("([^&]+?)=([^&]*)");
			Matcher m = p.matcher(qp);
			while (m.find()) {
				k.put(m.group(1), m.group(2));
			}
		}
		return k;
	}
	/**
	 * 将map转成queryString
	 * 
	 * @param map
	 * @return
	 */
	public static String mapToQueryString(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for ( Map.Entry< String, String> entry : map.entrySet() ) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	public static String getHighStep(BigDecimal v) {
		return getHighStep(v, 0);
	}

	public static String getHighStep(BigDecimal v, int scale) {
		BigDecimal wan = new BigDecimal("10000"), yi = new BigDecimal(
				"100000000");
		String step = "";
		if (yi.compareTo(v) <= 0) {
			v = v.divide(yi, scale, BigDecimal.ROUND_HALF_UP);
			step = "亿";
		} else if (wan.compareTo(v) <= 0) {
			v = v.divide(wan, scale, BigDecimal.ROUND_HALF_UP);
			step = "万";
		}
		return v.setScale(scale, BigDecimal.ROUND_HALF_UP) + step;
	}

	/**
	 * 返回字符串对应的数值，当无效字符串或null时，返回默认字符串指定的数值
	 * 
	 * @param vs
	 * @param defa
	 * @return
	 */
	public static int getNumberDefault(String vs, String defa) {
		int def = 0;
		int v = 0;
		try {
			def = Integer.parseInt(defa, 10);
			v = Integer.parseInt(vs, 10);
		} catch (Exception e) {
			return def;
		}
		return v;
	}
	public static String leftPad(long n, int len) {
		return right(ZERO_STR + n, len);
	}
	/**
	 * 取字符串左边指定长度子串
	 * 
	 * @param s
	 *            源字符串
	 * @param len
	 *            指定长度
	 * @return
	 */
	public static String left(String s, int len) {
		return s == null ? null : s.substring(0, len);
	}

	/**
	 * 取字符串右边指定长度子串
	 * 
	 * @param s
	 *            源字符串
	 * @param len
	 *            指定长度
	 * @return
	 */
	public static String right(String s, int len) {
		return s == null ? null : s.substring(s.length() - len, s.length());
	}
	public static Map<String, String> toStringMap(Map<String, String[]> m) {
		HashMap<String, String> map = new HashMap<String, String>();
		for ( Map.Entry< String, String[]> entry : m.entrySet() ) {
			String[] v = entry.getValue();
			map.put(entry.getKey(), v == null || v.length <= 0 ? "" : getDefault(v[0], ""));
		}
		return map;
	}
	public static Map<String, String> toStringMap(
			MultiValueMap<String, String> m) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (String k : m.keySet()) {
			map.put(k, getDefault(m.getFirst(k), ""));
		}
		return map;
	}
	public static Map<String, String[]> toArrayMap(Map<String, String> m) {
		HashMap<String, String[]> map = new HashMap<String, String[]>();
		for ( Map.Entry< String, String> entry : m.entrySet() ) {
			map.put(entry.getKey(), new String[]{getDefault(entry.getValue(), "")});
		}

		return map;
	}
	public static String HTMLEncode(String javaS) {
		if (javaS == null)
			return "";
		return javaS.replaceAll("&", "&amp;").replaceAll("\"", "&quot;")
				.replaceAll("\'", "&acute;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\r|\n", "<br/>");
	}
	public static String HTMLDecode(String javaS) {
		if (javaS == null)
			return "";
		return javaS.replaceAll("<br>", "\n").replaceAll("&quot;", "\"")
				.replaceAll("&acute;", "'").replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">").replaceAll("&amp;", "&");
	}
	public static String toJSString(String javaS) {
		if (javaS == null)
			return "";
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < javaS.length(); i++) {
			char c = javaS.charAt(i);
			switch (c) {
				case '\n' :
				case '\r' :
					sb.append("\\n");
					break;
				case '\'' :
				case '\"' :
				case '\\' :
					sb.append("\\");
				default :
					sb.append(c);
			}
		}
		return sb.toString();
	}
	public static String byteToString(byte[] digest) {
		StringBuilder str = new StringBuilder();
		String tempStr = "";
		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str.append("0").append(tempStr);
			} else {
				str.append(tempStr);
			}
		}
		return str.toString().toLowerCase();
	}
	public static boolean in(String source, String find, String splitBy) {
		if (source == null)
			return false;
		String[] ss = source.split(splitBy);
		for (String f : ss) {
			if (f.equals(find))
				return true;
		}
		return false;
	}
	public static boolean in(String source, String find) {
		return in(source, find, ",");
	}
	public static boolean isChinese(String s) {
		return s != null && s.matches("^[\u4e00-\u9fa5]+$");
	}
	public static boolean isTrue(String v) {
		return "true".equalsIgnoreCase(v) || "1".equals(v);
	}
	public static String toJSON(Map<String, Object> map) {
		return toJSON("yyyy-MM-dd HH:mm:ss", map);
	}
	public static String toJSON(String pattern, Map<String, Object> map) {
		return new GsonBuilder().setDateFormat(pattern).create().toJson(map);
	}
	public static String mask(String s) {
		if (isEmpty(s) || s.length() == 1)
			return s;
		if (s.length() == 2)
			return s.replaceAll("(.).*", "$1*");
		else if (s.length() == 3)
			return s.replaceAll("(.).(.)", "$1*$2");
		else
			return s.replaceAll(
					"(.).{" + (s.length() - 3) + "}(.{2})",
					String.format("$1%s$2",
							StringUtil.left(maskS, s.length() - 3)));
	}
	/**
	 * 校验QQ是否合法
	 * 
	 * @param qq
	 * @return
	 */
	public static boolean isQQ(String qq) {
		return !isEmpty(qq) && qq.matches("^[1-9]\\d{4,9}$");
	}

	/**
	 * 校验身份证是否合法
	 * 
	 * @param idcard
	 * @return
	 */
	public static boolean isIdcard(String idcard) {
		// 是否为空
		if (isEmpty(idcard)) {
			return false;
		}
		// 校验长度，类型
		if (isCardNo(idcard) == false) {
			return false;
		}
		// 检查省份
		if (checkProvince(idcard) == false) {
			return false;
		}
		// 校验生日
		if (checkBirthday(idcard) == false) {
			return false;
		}
		// 检验位的检测
		if (checkParity(idcard) == false) {
			return false;
		}
		return true;
	}

	// 检查号码是否符合规范，包括长度，类型
	private static boolean isCardNo(String idCard) {
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
		return idCard.matches("(^\\d{15}$)|(^\\d{17}(\\d|X|x)$)");
	};

	// 取身份证前两位,校验省份
	/*
	 * String[] vcity = {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
	 * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
	 * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",
	 * 41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",
	 * 46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",
	 * 54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",
	 * 65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" };
	 */
	private static boolean checkProvince(String idCard) {
		String province = idCard.substring(0, 2);
		String s = ",11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91,";
		if (s.indexOf("," + province + ",") > -1)
			return true;
		return false;
	}

	// 检查生日是否正确
	private static boolean checkBirthday(String idCard) {
		int len = idCard.length();
		// 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
		if (len == 15) {
			String re_fifteen = "^(\\d{6})(\\d{2})(\\d{2})(\\d{2})(\\d{3})$";
			Pattern p = Pattern.compile(re_fifteen);
			Matcher m = p.matcher(idCard);
			if (m.find()) {
				String year = m.group(2);
				String month = m.group(3);
				String day = m.group(4);
				Date birthday;
				try {
					birthday = new SimpleDateFormat("yyyy/MM/dd").parse("19"
							+ year + "/" + month + "/" + day);
					return verifyBirthday("19" + year, month, day, birthday);
				} catch (ParseException e) {
					return false;
				}
			}
			return false;
		}
		// 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
		if (len == 18) {
			String re_eighteen = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)$";
			Pattern p = Pattern.compile(re_eighteen);
			Matcher m = p.matcher(idCard);
			if (m.find()) {
				String year = m.group(2);
				String month = m.group(3);
				String day = m.group(4);
				try {
					Date birthday = new SimpleDateFormat("yyyy/MM/dd")
							.parse(year + '/' + month + '/' + day);
					return verifyBirthday(year, month, day, birthday);
				} catch (ParseException e) {
					return false;
				}
			}
			return false;
		}
		return false;
	}

	// 校验日期
	private static boolean verifyBirthday(String year, String month,
			String day, Date birthday) {
		Calendar c1 = Calendar.getInstance();

		c1.setTimeInMillis(birthday.getTime());

		return DealDate.getFullYear(c1)==Integer.parseInt(year)
				&& DealDate.getMonth(c1)==Integer.parseInt(month)
				&& DealDate.getDayOfMonth(c1)==Integer.parseInt(day);




//		int now_year = c.get(Calendar.YEAR);
//		c1.setTime(birthday);
//		// 年月日是否合理
//		if (c1.get(Calendar.YEAR) == Integer.parseInt(year)
//				&& (birthday.getMonth() + 1) == Integer.parseInt(month)
//				&& birthday.getDate() == Integer.parseInt(day)) {
//			// 判断年份的范围（3岁到100岁之间)
//			int time = now_year - Integer.parseInt(year);
//			if (time >= 3 && time <= 100) {
//				return true;
//			}
//			return false;
//		}
//		return false;
	}

	// 校验位的检测
	private static boolean checkParity(String idCard) {
		// 15位转18位
		idCard = changeFivteenToEighteen(idCard);
		char[] card = idCard.toCharArray();
		int len = idCard.length();
		if (len == 18) {
			int[] arrInt = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
			char[] arrCh = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3',
					'2'};
			int cardTemp = 0;
			for (int i = 0; i < 17; i++) {
				cardTemp += Integer.parseInt(String.valueOf(card[i]))
						* arrInt[i];
			}
			String valnum = String.valueOf(arrCh[cardTemp % 11]);
			if (valnum.equalsIgnoreCase(idCard.substring(17))) {
				return true;
			}
			return false;
		}
		return false;
	};

	// 15位转18位身份证号
	private static String changeFivteenToEighteen(String idCard) {
		if (idCard.length() == 15) {

			int[] arrInt = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
			char[] arrCh = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3',
					'2'};
			int cardTemp = 0;

			idCard = idCard.substring(0, 6) + "19" + idCard.substring(6);
			char[] card = idCard.toCharArray();
			for (int i = 0; i < arrInt.length; i++) {
				cardTemp += (card[i] - '0') * arrInt[i];
			}
			idCard += arrCh[cardTemp % 11];
			return idCard;
		}
		return idCard;
	};

	/**
	 * 校验银行卡是否合法
	 * 
	 * @param bankCard
	 * @return
	 */
	public static boolean isBankCard(String bankCard) {
		return !isEmpty(bankCard) && bankCard.matches("^(\\d{16,22})$");
	}

	/**
	 * 加密手机号
	 * 
	 * @param s
	 * @return
	 */
	public static String maskTelephone(String s) {
		if (isMobileExact(s)) {
			return s.replaceAll(
					"(.{3}).{" + (s.length() - 7) + "}(.{4})",
					String.format("$1%s$2",
							StringUtil.left(maskS, s.length() - 7)));
		} else {
			return s;
		}
	}

	/**
	 * 加密银行卡号
	 * 
	 * @param s
	 * @return
	 */
	public static String maskBankCardNo(String s) {
		String mark = "**** **** **** ";
		if (isBankCard(s))
			return mark + s.substring(s.length() - 4, s.length());
		else
			return s;
	}

	/**
	 * 加密身份证号
	 * 
	 * @param s
	 * @return
	 */
	public static String maskIDcard(String s) {
		if (isIdcard(s)) {
			return s.replaceAll(
					"(.{0}).{" + (s.length() - 4) + "}(.{4})",
					String.format("$1%s$2",
							StringUtil.left(maskS, s.length() - 4)));
		} else {
			return s;
		}
	}
	
	public static String filterEmojiChar(String s) {
		return StringUtil.isEmpty(s)?s:s.replaceAll("([\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff])*", "");
	}

	public static void main(String[] args) {
		System.out.println(checkAmount(new BigDecimal("10098.922")));
	}

	/**
	 * 返回默认UTF-8编码格式的字节数组
	 * @param str
	 * @return
	 */
	public static byte[] getDefaultByte(String str){
		byte[] returnValue = null;
		try {
			returnValue = str.getBytes(StringUtil.getDefault(PropertyConfig.getPropertyValue("project.charset"),"UTF-8"));
		}catch (Exception e){
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * 数字校验
	 * 任意正整数，正小数（小数位不超过2位）
 	 * @param amount
	 * @return
	 */
	public static boolean checkAmount(BigDecimal amount){
		if(amount==null) return false;

		return amount.toString().matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
//		Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
//		Matcher match=pattern.matcher(amount.toString());
//		return match.matches();


	}

	public static boolean isIpAddr(String ip){
		return !isEmpty(ip) && ip.matches("\\d+(\\.\\d+){3}");
	}

	/**
	 * 验证手机号（精确）
	 */
	public static boolean isMobileExact(String input) {
		return isMatch(PATTERN_REGEX_MOBILE_EXACT, input);
	}

	public static boolean isMatch(Pattern pattern, String input) {
		return !isEmpty(input) && pattern.matcher(input).matches();
	}

	public static String deleteWhitespace(String input){
		return  StringUtils.deleteWhitespace(input);
	}
}
