package com.t251.springbootcrm.util;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

public class SecurityUtils {

    /**
     * md5加密
     *
     * @param value 要加密的值
     * @return md5加密后的值
     */
    public static String md5Hex1(String value) {
        return DigestUtils.md5Hex(value);
    }

    /**
     * 3次md5操作   
     * @param value
     * @return
     */
    public static String md5Hex3(String value) {
    	for (int i = 0; i < 3; i++) {
    		value = DigestUtils.md5Hex(value);
    	}
    	return value;
    }

    /*
    shiro+md5
     */
      public static String md5Hex(String src) {
               try {
                         MessageDigest md5 = MessageDigest.getInstance("MD5");
                         byte[] bs = md5.digest(src.getBytes());
                        return new String(new Hex().encode(bs));
                    } catch (Exception e) {
                        return null;
                     }
             }
    
    /**
     * sha256加密
     *
     * @param value 要加密的值
     * @return sha256加密后的值
     */
    public static String sha256Hex(String value) {
        return DigestUtils.sha256Hex(value);
    }

    public static String sha512Hex(String value) {
        return DigestUtils.sha512Hex(value);
    }
    
    public static void main(String[] args) {
        if ((2>1)&(3>1)){
            System.out.println("哈哈哈");
        }
        //System.out.println(SecurityUtils.md5Hex("salt"+"123456"));
       // Md5Hash md5Hash = new Md5Hash("123456", "MD5", 1024);
       // System.out.println(md5Hash);
	}
}
