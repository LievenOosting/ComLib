/**  
 * @Title: MD5.java 
 * @Package com.example.adapter 
 * @Description: TODO() 
 * @author Derek 
 * @email renchun525@gmail.com
 * @date 2013-11-12 下午3:39:27 
 * @version V1.0  
 */ 
package com.qcj.common.util.localImageHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * @ClassName: MD5 
 * @Description: TODO() 
 * @author Derek
 * @date 2013-11-12 下午3:39:27  
 */

public class MD5 {
	// MD5変換
	public static String Md5(String str) {
		if (str != null && !str.equals("")) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
				byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < md5Byte.length; i++) {
					sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
					sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
				}
				str = sb.toString();
			} catch (NoSuchAlgorithmException e) {
			} catch (Exception e) {
			}
		}
		return str;
	}
	
}
