package com.dingsheng.decent.util.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数值类型工具类
 *
 * @author Lin.D.G
 *
 */
public class NumberUtil {
	/**
	 * 静态对象，提供快速引用
	 * 1到10
	 * 万，千，百
	 */

	public static final BigDecimal ONE = BigDecimal.ONE;
	public static final BigDecimal TWO = new BigDecimal(2);
	public static final BigDecimal THREE = new BigDecimal(3);
	public static final BigDecimal FOUR = new BigDecimal(4);
	public static final BigDecimal FIVE = new BigDecimal(5);
	public static final BigDecimal SIX = new BigDecimal(6);
	public static final BigDecimal SEVEN = new BigDecimal(7);
	public static final BigDecimal EIGHT = new BigDecimal(8);
	public static final BigDecimal NINE = new BigDecimal(9);
	public static final BigDecimal TEN = BigDecimal.TEN;

	public static final BigDecimal HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN);
	public static final BigDecimal THOUSAND = BigDecimal.TEN.multiply(HUNDRED);
	public static final BigDecimal WAN = HUNDRED.multiply(HUNDRED);

	/**
	 * 获取两者中的小者
	 *
	 * @param a 数值a
	 * @param b 数值b
	 * @return 两个数值中的小者
	 */
	public static BigDecimal min(BigDecimal a,BigDecimal b){
		return a==null||b==null?BigDecimal.ZERO:a.compareTo(b)>=0?b:a;
	}
	/**
	 * 获取两者中的大者
	 *
	 * @param a
	 * @param b
	 * @return 两个数值中的大者
	 */
	public static BigDecimal max(BigDecimal a,BigDecimal b){
		return a==null||b==null?BigDecimal.ZERO:a.compareTo(b)>=0?a:b;
	}

	/**
	 * 判断目标数值是否在min和max两个数值之间（min和max包含）
	 *
	 * tar=null，则为false
	 *
	 * 如果min=null，则判断tar<=max
	 *
	 * 如果max=null，则判断tar>=min
	 *
	 * 如果min=null，max=null，则为false
	 *
	 * @param tar 目标数值
	 * @param min 小值
	 * @param max 大值
	 * @return
	 */
	public static boolean isBetween(BigDecimal tar, BigDecimal min,
									BigDecimal max) {
		return tar != null && (min!=null || max!=null) && (min == null || tar.compareTo(min) >= 0)
				&& (max == null || tar.compareTo(max) <= 0);
	}

	/**
	 * 判断 tar>=dest
	 *
	 * @param tar
	 * @param dest
	 * @return
	 */
	public static boolean greaterEquals(BigDecimal tar, BigDecimal dest){
		return isBetween(tar, dest, null);
	}

	public static boolean equals(BigDecimal tar, BigDecimal dest){
		return tar!=null && dest!=null && tar.compareTo(dest)==0;
	}

	/**
	 * 判断tar<=dest
	 *
	 * @param tar
	 * @param dest
	 * @return
	 */
	public static boolean lessEquals(BigDecimal tar, BigDecimal dest){
		return isBetween(tar, null, dest);
	}
	/**
	 * 判断tar>dest
	 *
	 * @param tar
	 * @param dest
	 * @return
	 */
	public static boolean greater(BigDecimal tar, BigDecimal dest){
		return isBetween(tar, dest, null) && tar.compareTo(dest)!=0;
	}

	/**
	 * 判断tar<dest
	 *
	 * @param tar
	 * @param dest
	 * @return
	 */
	public static boolean less(BigDecimal tar, BigDecimal dest){
		return isBetween(tar, null, dest) && tar.compareTo(dest)!=0;
	}

	/**
	 * 判断 min<tar<max
	 *
	 * @param tar
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean greaterAndLess(BigDecimal tar, BigDecimal min,
										 BigDecimal max) {
		return greater(tar, min) && less(tar, max);
	}

	/**
	 * 判断min<=tar<max
	 *
	 * @param tar
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean greaterEqualsAndLess(BigDecimal tar, BigDecimal min,
											   BigDecimal max) {
		return greaterEquals(tar, min) && less(tar, max);
	}
	/**
	 * 判断min<tar<=max
	 *
	 * @param tar
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean greaterAndLessEquals(BigDecimal tar, BigDecimal min,
											   BigDecimal max) {
		return greater(tar, min) && lessEquals(tar, max);
	}

	/**
	 * 获取v的默认值
	 *
	 * 当v为null时，返回def
	 *
	 * @param v
	 * @param def
	 * @return
	 */
	public static BigDecimal getDefault(BigDecimal v, BigDecimal def) {
		return v==null?def:v;
	}

	/**
	 * 阶段统计计算
	 *
	 * base*loop+2*base*loop+...+Math.ceil(num/loop)*base*(num%loop|loop)
	 *
	 * @param num
	 * @param base
	 * @param loop
	 * @return
	 */
	public static BigDecimal stepSum(BigDecimal num, BigDecimal base,
									 BigDecimal loop) {
		if (num == null || base == null || loop == null)
			throw new IllegalArgumentException(String.format(
					"参数出现null值错误，num=%s,base=%s,loop=%s", num, base, loop));
		if (lessEquals(num, BigDecimal.ZERO)
				|| lessEquals(base, BigDecimal.ZERO)
				|| lessEquals(loop, BigDecimal.ZERO)) {
			throw new IllegalArgumentException(String.format(
					"参数出现负值错误，num=%s,base=%s,loop=%s", num, base, loop));
		}
		if (lessEquals(num, loop))
			return num.multiply(base);
		BigDecimal times = num.divide(loop, 0, RoundingMode.CEILING), inum = num
				.remainder(loop);
		inum = equals(inum, BigDecimal.ZERO) ? loop : inum;
		return inum.multiply(base).multiply(times)
				.add(stepSum(num.subtract(inum), base, loop));
	}

}
