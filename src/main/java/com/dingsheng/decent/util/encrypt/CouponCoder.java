package com.dingsheng.decent.util.encrypt;

public class CouponCoder {
	/**
	 * 由
	 * 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ
	 * 随机串组成
	 */
//	private static final String baseDigits = "nPJ8VzBbrde7iMkh6lC93WOS1wqIpTK2juL0yXZQgmARYsvat5NDU4FoHGxcEf";
	/**
	 * 由
	 * 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ
	 * 随机串组成
	 */
	private static final String[] baseDigits = new String[]{
			"BQOG740SUE12KJ6HM8ZWTDLVNFRCY53X9IAP",
			"7I3WFD9ZTJN1HC854EYBVP0LKGMQOARXU6S2",
			"OACEZ17G34MXIS08YTUR9LHPN6JBVDK5WF2Q",
			"JSMRBH47PE0Q6ADKFW9YZIOGX3UVNCL2518T",
			"DQMO98CLI2Z01SE7XRFVP6WUYT3KNAGB5J4H",
			"HT0NIZ7AXCGQEYBVD4W6R9LUPO13M5JKS8F2",
			"1WF8OU5BGZJLMK9PIVS26QAT34X0C7REYDNH",
			"UXDL3CYIEFRW0P6Q2HK4A5S1OZJGT9VNB87M",
			"NH4OGX70VQ6PIY9UAEBZ2K38CR51FTJMSDWL",
			"SLTP72JV4HG8KZE5YD0QRUA6NF9XIBM3CWO1"
	};
    private static final int BASE = 36;
    private static final char[][] digitsChar = new char[10][36];;
    private static final int FAST_SIZE = 'z';
    private static final int[][] digitsIndex = new int[10][FAST_SIZE + 1];
    
    private static int[] weight = new int[]{2,4,8,5,10,9,7,3,6,1,2,4,8,5,10,9,7},
			mask = new int[]{1, 0, 9, 8, 7, 6, 5, 4, 3, 2};
    /**
var a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
var as = [];
while(a.length>0){
    var r = parseInt(Math.random()*a.length);
    as = as.concat(a.splice(r,1));
}
System.debug(as.join("").length);
     */
    static {
    	for(int j=0;j<10;j++){
	        for (int i = 0; i < FAST_SIZE; i++) {
	            digitsIndex[j][i] = -1;
	        }
    	}
        //
    	for(int j=0;j<10;j++){
    		digitsChar[j] = baseDigits[j].toCharArray();
	        for (int i = 0; i < BASE; i++) {
	            digitsIndex[j][digitsChar[j][i]] = i;
	        }
    	}
    }
    
    public static long decode(String s) {
        long result = 0L;
        long multiplier = 1;
        int m = getMask(s);
//        s = s.substring(0, s.length()-1);
        for (int pos = s.length() - 2; pos >= 0; pos--) {
            int index = getIndex(s, pos, m);
            result += index * multiplier;
            multiplier *= BASE;
        }
        if(!checkMask(result,m))throw new IllegalArgumentException("Unknow character for CouponCode: " + s);
        return result;
    }
 
    public static String encode(long number) {
        if (number < 0) throw new IllegalArgumentException("Number(CouponCode) must be positive: " + number);
        if (number == 0) return "0";
        int m = mask(number);
        StringBuilder buf = new StringBuilder();
        while (number != 0) {
            buf.append(digitsChar[m][(int) (number % BASE)]);
            number /= BASE;
        }
        return buf.reverse().append(m).toString();
    }
    
    private static int getIndex(String s, int pos, int m) {
        char c = s.charAt(pos);
        if (c > FAST_SIZE) {
            throw new IllegalArgumentException("Unknow character for CouponCode: " + s);
        }
        int index = digitsIndex[m][c];
        if (index == -1) {
            throw new IllegalArgumentException("Unknow character for CouponCode: " + s);
        }
        return index;
    }
    public static int mask(long n){
    	long s = 0,vc = n;
    	int l = String.valueOf(n).length();
    	for(int i = 0;i<l;i++){
			long v = vc%10;
			s += v*weight[i];
			vc = vc/10;
		}
    	return mask[(int)s%10];
    }
    public static boolean checkMask(long n, int m){
    	return m == mask(n);
    }
    private static int getMask(String s){
    	int r = 0;
    	try{
    		r = Integer.parseInt(s.substring(s.length()-1,s.length()));
    	}catch(Exception e){
    	}
    	return r;
    }
}
