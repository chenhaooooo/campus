package com.campus.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DataBase64 {

	/**对字符串进行base64解密
	* @param data 进行解密的字符串
	* @return 解密后的字符串
	*/
	public static String decodeBase64(String data) {

		BASE64Decoder decoder = new BASE64Decoder();
		String str="";
		try {
			str = new String(decoder.decodeBuffer(data), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**对字符串进行base64加密
	 *
	 * @param data 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encodeBase64(String data) {
		BASE64Encoder encoder = new BASE64Encoder();
		String str="";
		try {
			str = new String(encoder.encode(data.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

}
