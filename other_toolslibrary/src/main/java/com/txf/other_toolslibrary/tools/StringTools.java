package com.txf.other_toolslibrary.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author txf
 * @Title 字符串工具
 * @package com.txf.prevention.tools
 * @date 2017/5/3 0003
 */

public class StringTools {
    /**
     * 全局数组
     **/
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String getString(char[] ch) {
        String a = ch[2] + "";
        String b = ch[8] + "";
        String c = ch[13] + "";
        String d = ch[26] + "";
        String e = ch[31] + "";
        String str = a + b + c + d + e;
        return str;
    }

    public static String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                str += (char) (65 + random.nextInt(26));// 取得大写字母
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }

    public static String GetMD5Code(String str) {
        String result = null;
        try {
            result = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param bByte
     * @return
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 返回形式为数字跟字符串
     *
     * @param bByte
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * 字符串非空验证
     */
    public static boolean isNull(String text) {
        if (text == null)
            return true;
        if (text.isEmpty())
            return true;
        return false;
    }

    /**
     * 字符串解密
     *
     * @param msg 字符串
     * @return String 返回加密字符串
     */
    public static String decrypt(String msg) {
        try {
            String name = new String();
            java.util.StringTokenizer st = new java.util.StringTokenizer(msg, "%");
            while (st.hasMoreElements()) {
                int asc = Integer.parseInt((String) st.nextElement()) - 27;
                name = name + (char) asc;
            }

            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串加密
     *
     * @param msg 字符串
     * @return String 返回加密字符串
     */
    public static String encrypt(String msg) {
        try {
            byte[] _ssoToken = msg.getBytes("ISO-8859-1");
            String name = new String();
            // char[] _ssoToken = ssoToken.toCharArray();
            for (int i = 0; i < _ssoToken.length; i++) {
                int asc = _ssoToken[i];
                _ssoToken[i] = (byte) (asc + 27);
                name = name + (asc + 27) + "%";
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * unicode转utf-8
     * */
    public static String unicodeToUtf_8(String src) {
        if (null == src) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();
    }
}
