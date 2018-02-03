package com.yz.common.core.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {
	public static int isAcronym(String word)
	 {
	  for(int i = 0; i < word.length(); i++)
	  {
	   char c = word.charAt(i);
	   if (!Character.isLowerCase(c))
	   {
	    return i;
	   }
	  }
	  return -1;
	 }
	/**
	 * 在字符串大写字母前添加指定字符
	 * @param
	 * @param character
	 * @param addChar
	 * @return
	 */
	public static String addCharByIndexFront(String character,String addChar){
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<character.length();i++){
			char c=character.charAt(i);
			boolean result=Character.isLetter(c);
			if(!result){
				continue;
			}
			if(!Character.isLowerCase(c)){
				builder.append(addChar).append(Character.toLowerCase(c));
				continue;
			}
			builder.append(c);
		}
		return builder.toString();
	}
	
	public static boolean isEmpty(String...params) {
		for (String param : params) {
			if (param == null || param.equals("")||param.length()==0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmpty(String param) {
		if (param == null || param.equals("")||param.length()==0||param.equals("null")) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串首字母大写
	 * @param chars
	 * @return
     */
	public static String firstCharToUpper(String chars){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(chars.substring(0,1).toUpperCase()).append(chars.substring(1));
		return stringBuilder.toString();
	}

	/**
	 * 判断一个字符串是否含有中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {

		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 生成随机数字和字母
	 * @param length
	 * @return
	 */
	public String createStringRandom(int length) {

		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char)(random.nextInt(26) + temp);
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
}
