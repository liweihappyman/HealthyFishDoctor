package com.healthyfish.healthyfishdoctor.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述：Sha256加密算法
 * 作者：WKJ on 2017/7/24.
 * 邮箱：
 * 编辑：WKJ
 */

public class Sha256 {
    // 将字节数组转换为十六进制字符串
    public static String byteArrayToHexString(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexString(bytearray[i]);
        }
        return strDigest;
    }

    // 将字节转换为十六进制字符串
    private static String byteToHexString(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }


    //对密码进行Sha256加密
    public static String getSha256(String originPasswrod) {
        MessageDigest hash = null;
        String password = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
            byte[] passwordSha256 = new byte[0];
            try {
                passwordSha256 = hash.digest(originPasswrod.getBytes(("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            password = byteArrayToHexString(passwordSha256);
            Log.i("password",password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  password.toLowerCase();
    }



}
