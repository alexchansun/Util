package com.fanli.util;

import org.apache.commons.lang.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;

import static com.fanli.Constant.COOKIE_KEY;

/**
 * User:cjp
 * Date: 2006-3-13
 * Time: 9:42:11
 */
public class DesCryptUtil {
    public static byte[] desEncrypt(byte[] orgData, byte[] rawKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(rawKey);

        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");

        // 用密匙初始化Cipher对象
        cipher.init(1, key, sr);

        // 正式执行加密操作
       return cipher.doFinal(orgData);

    }

    public static byte[] desDecrypt(byte[] encryptData,byte[] rawKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec( rawKey );

        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance( "DES" );
        SecretKey key = keyFactory.generateSecret( dks );

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance( "DES" );

        // 用密匙初始化Cipher对象
        cipher.init( Cipher.DECRYPT_MODE, key, sr );

        // 正式执行解密操作
        return  cipher.doFinal( encryptData );
    }


    public static String encrypt(String orgStr, String key) throws Exception {
        String orgStr_hex = byteArr2HexStr(orgStr.getBytes());

        //System.out.println("orgStr_hex = " + orgStr_hex);

        String key_md5 = hashKey(key);

        //System.out.println("key_md5 = " + key_md5);

        //System.out.println("newStr_hex = " + newStr_hex);

        return orgStr_hex.substring(0, 2) + key_md5 + orgStr_hex.substring(2);

    }

    public static String dectrypt(String encStr, String key) throws Exception {
        int start = 2;
        String key_md5 = hashKey(key);

        String orgStr_hex = encStr.substring(0, start) + encStr.substring(key_md5.length() + start);
        //System.out.println("orgStr_hex = " + orgStr_hex);

        String key_hex_md5 = encStr.substring(start, key_md5.length() + start);
        if (key_hex_md5.equalsIgnoreCase(key_hex_md5)){
            return new String(hexStr2ByteArr(orgStr_hex));
        }
        return null;
    }

    private static String hashKey(String key){
        MD5 md5 = new MD5();
        return md5.getMD5ofStr(key);
    }

    /**
     * 将byte数组转换为表示16进制值的字符串，
     * 如：byte[]{8,18}转换为：0813，
     * 和public static byte[] hexStr2ByteArr(String strIn)
     * 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB)
            throws Exception {
        int iLen = arrB.length;
        //每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            //把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            //小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }


    /**
     * 将表示16进制值的字符串转换为byte数组，
     * 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static byte[] hexStr2ByteArr(String strIn)
            throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        //两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    public static String creatClientIdentityHashContent(String msnAccount){
        // change start2char with end2char each other;
        msnAccount = msnAccount.trim();
        int len = msnAccount.length();
        String start2char = StringUtils.substring(msnAccount,0,2);
        String middle = StringUtils.substring(msnAccount,2,len-2);
        String end2char = StringUtils.substring(msnAccount,len-2);
        //System.out.println("end2char = " + end2char);
        Calendar cal = Calendar.getInstance();
        String hashSeed = end2char+middle+start2char;

        //System.out.println("hashSeed=="+hashSeed);

        MD5 md5 = new MD5();
        return md5.getMD5ofStr(hashSeed);
    }

    public static void main(String[] args) throws Exception {
      String s=  dectrypt("63E8856888430ED87E03440C6B635DBC3079783633333338403136332e636f6d7b7c7c7d31393837303831357b7c7c7d6379783633333338403136332e636f6d7b7c7c7d31",COOKIE_KEY);
        System.out.println(s);
    }
}
