package com.dingsheng.decent.util.old;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class EncryptUtil {

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String HmacMD5 = "HmacMD5";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String DES = "DES";
    public static final String AES = "AES";

    public static final String AESKey = "8F3bQE54H6e5gG0G";
    public static final String DESencodeKey = "j45eVs3qGw7He56";

    /**
     * 编码格式；默认使用uft-8
     */
    public String charset = "utf-8";
    /**
     * DES
     */
    public int keysizeDES = 0;
    /**
     * AES
     */
    public int keysizeAES = 128;

    public static EncryptUtil me;

    private EncryptUtil() {
        //单例
    }

    //双重锁
    public static EncryptUtil getInstance() {
        if (me == null) {
            synchronized (EncryptUtil.class) {
                if (me == null) {
                    me = new EncryptUtil();
                }
            }
        }
        return me;
    }

    //<editor-fold desc="使用MessageDigest进行单向加密（无密码）">

    /**
     * 使用MessageDigest进行单向加密（无密码）
     *
     * @param res       被加密的文本
     * @param algorithm 加密算法名称
     * @return
     */
    private String messageDigest(String res, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
            return base64(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="使用KeyGenerator进行单向/双向加密（可设密码）">

    /**
     * 使用KeyGenerator进行单向/双向加密（可设密码）
     *
     * @param res       被加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密使用的秘钥
     * @return
     */
    private String keyGeneratorMac(String res, String algorithm, String key) {
        try {
            SecretKey sk = null;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            return base64(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错">

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     *
     * @param res       加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密的秘钥
     * @param keysize
     * @param isEncode
     * @return
     */
    private String keyGeneratorES(String res, String algorithm, String key, int keysize, boolean isEncode) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                kg.init(new SecureRandom(keyBytes));
            } else if (key == null) {
                kg.init(keysize);
            } else {
                byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
                kg.init(keysize, new SecureRandom(keyBytes));
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold desc="md5加密算法进行加密（不可逆）">

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public String MD5(String res) {
        return messageDigest(res, MD5);
    }
    //</editor-fold>

    //<editor-fold desc="md5加密算法进行加密（不可逆）">

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public String MD5(String res, String key) {
        return keyGeneratorMac(res, HmacMD5, key);
    }
    //</editor-fold>

    //<editor-fold desc="使用SHA1加密算法进行加密（不可逆）">

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public String SHA1(String res) {
        return messageDigest(res, SHA1);
    }
    //</editor-fold>

    //<editor-fold desc="使用SHA1加密算法+Key进行加密（不可逆）">

    /**
     * 使用SHA1加密算法+Key进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public String SHA1(String res, String key) {
        return keyGeneratorMac(res, HmacSHA1, key);
    }
    //</editor-fold>

    //<editor-fold desc="使用DES加密算法进行加密（可逆）">

    /**
     * 使用DES加密算法进行加密（可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public String DESencode(String res) {
        return keyGeneratorES(res, DES, DESencodeKey, keysizeDES, true);
    }
    //</editor-fold>

    //<editor-fold desc="对使用DES加密算法的密文进行解密（可逆）">

    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     *
     * @param res 需要解密的密文
     * @return
     */
    public String DESdecode(String res) {
        return keyGeneratorES(res, DES, DESencodeKey, keysizeDES, false);
    }
    //</editor-fold>

    //<editor-fold desc="使用AES加密算法经行加密（可逆）">

    /**
     * 使用AES加密算法经行加密（可逆）
     *
     * @param res 需要加密的密文
     * @return
     */
    public String AESencode(String res) {
        return keyGeneratorES(res, AES, AESKey, keysizeAES, true);
    }
    //</editor-fold>

    //<editor-fold desc="对使用AES加密算法的密文进行解密">

    /**
     * 对使用AES加密算法的密文进行解密
     *
     * @param res 需要解密的密文
     * @return
     */
    public String AESdecode(String res) {
        return keyGeneratorES(res, AES, AESKey, keysizeAES, false);
    }
    //</editor-fold>

    //<editor-fold desc="使用异或进行加密">

    /**
     * 使用异或进行加密
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return
     */
    public String XORencode(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }
    //</editor-fold>

    //<editor-fold desc="使用异或进行解密">

    /**
     * 使用异或进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public String XORdecode(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }
    //</editor-fold>

    //<editor-fold desc="直接使用异或（第一调用加密，第二次调用解密）">

    /**
     * 直接使用异或（第一调用加密，第二次调用解密）
     *
     * @param res 密文
     * @param key 秘钥
     * @return
     */
    public int XOR(int res, String key) {
        return res ^ key.hashCode();
    }
    //</editor-fold>

    //<editor-fold desc="Base64Encode">

    /**
     * 使用Base64进行加密
     *
     * @param res 密文
     * @return
     */
    public String Base64Encode(String res) {
        return Base64.encode(res.getBytes());
    }
    //</editor-fold>

    //<editor-fold desc="使用Base64进行解密">

    /**
     * 使用Base64进行解密
     *
     * @param res
     * @return
     */
    public String Base64Decode(String res) {
        return new String(Base64.decode(res));
    }
    //</editor-fold>

    //<editor-fold desc="将二进制转换成16进制 ">

    /**
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    //</editor-fold>

    //<editor-fold desc="将16进制转换为二进制">

    /**
     * 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="将Base64数组转换为字符串">

    /**
     * 将Base64数组转换为字符串
     *
     * @param res
     * @return
     */
    private String base64(byte[] res) {
        return Base64.encode(res);
    }
    //</editor-fold>

    //<editor-fold desc="获取随机数">

    /**
     * 获取随机数
     *
     * @param leng 随机数长度
     * @param type 随机数类型 -1：英文+数字 1：纯数字 2：纯英文
     * @return
     */
    public static String GetRandNum(int leng, int type) {
        Random random = new Random();
        String result = "";

        String[] source;
        switch (type) {
            case -1://英文加数字 英文大小写随机
                source = new String[]{
                        "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                        "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                        "2", "3", "4", "5", "6", "7", "8", "9"};
                break;
            case 1://纯数字
                source = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
                break;
            default://纯英文
                source = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
                break;
        }

        for (int i = 0; i < leng; i++) {
            result += source[random.nextInt(source.length)];
        }
        return result;
    }
    //</editor-fold>

    //<editor-fold desc="获取MD5加密">

    /**
     * 获取MD5加密
     *
     * @param sourceStr 需要加密的字符串
     * @param type      0 16位加密 1 32位加密
     * @return
     */
    public static String GetMD5(String sourceStr, int type) {

        try {
            byte[] c = sourceStr.getBytes();
            //生成md5加密算法照耀
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int j = 0; j < b.length; j++) {
                i = b[j];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            if (type == 0) {
                return buf.toString().substring(8, 24);    //16位加密
            } else {
                return buf.toString();        //32位加密
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return "";
    }
    //</editor-fold>

    // 定义字符集
    private static final String ENCODING = "UTF-8";

    /**
     * 根据提供的密钥生成AES专用密钥
     *
     * @param password 可以是中文、英文、16进制字符串
     * @return AES密钥
     * @throws Exception
     * @explain
     */
    public static byte[] generateKey(String password) throws Exception {
        byte[] keyByteArray = null;
        // 创建AES的Key生产者
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // 利用用户密码作为随机数初始化
        // 128位的key生产者
        // 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
        /*
         * 只适用windows
         * kgen.init(128, new SecureRandom(password.getBytes(ENCODING)));
         */

        // 指定强随机数的生成方式
        // 兼容linux
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes(ENCODING));
        kgen.init(128, random);// 只能是128位

        // 根据用户密码，生成一个密钥
        SecretKey secretKey = kgen.generateKey();
        // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null。
        keyByteArray = secretKey.getEncoded();
        return keyByteArray;
    }

    /*
     * AES加密字符串
     * @param content
     *            需要被加密的字符串
     * @param password
     *            加密需要的密码
     * @return 16进制的密文（密文的长度随着待加密字符串的长度变化而变化，至少32位）
     */
    public static String encrypt(String content) {
        String cipherHexString = "";// 返回字符串
        try {
            // 转换为AES专用密钥
            byte[] keyBytes = generateKey(AESKey);

            SecretKeySpec sks = new SecretKeySpec(keyBytes, AES);
            // 将待加密字符串转二进制
            byte[] clearTextBytes = content.getBytes(ENCODING);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            // 加密结果
            byte[] cipherTextBytes = cipher.doFinal(clearTextBytes);
            // byte[]--&gt;hexString
            cipherHexString = bytesToHexString(cipherTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("AES加密失败：" + e.getMessage());
        }
//        log.info("AES加密结果：" + cipherHexString);
        return cipherHexString;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @return 明文
     */
    public static String decrypt(String hexString) {
        String clearText = "";
        try {
            // 转换为AES专用密钥
            byte[] keyBytes = generateKey(AESKey);

            SecretKeySpec sks = new SecretKeySpec(keyBytes, AES);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES);
            // 初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, sks);
            // hexString--&gt;byte[]
            // 将16进制密文转换成二进制
            byte[] cipherTextBytes = hexStringToByte(hexString);
            // 解密结果
            byte[] clearTextBytes = cipher.doFinal(cipherTextBytes);
            // byte[]--&gt;String
            clearText = new String(clearTextBytes, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("AES解密失败：" + e.getMessage());
        }
//        log.info("AES解密结果：" + clearText);
        return clearText;
    }

    public static String bytesToHexString(byte... src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }
}
