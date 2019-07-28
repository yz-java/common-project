package com.yz.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证工具类
 * Created by yangzhao on 17/5/7.
 */
public class IDCardUtil {

    private static final Logger logger = LoggerFactory.getLogger(IDCardUtil.class);

    private static Hashtable areaCodeHashtable = new Hashtable();

    static {
        //将所有地址编码保存在一个Hashtable中
        areaCodeHashtable.put("11", "北京");
        areaCodeHashtable.put("12", "天津");
        areaCodeHashtable.put("13", "河北");
        areaCodeHashtable.put("14", "山西");
        areaCodeHashtable.put("15", "内蒙古");
        areaCodeHashtable.put("21", "辽宁");
        areaCodeHashtable.put("22", "吉林");
        areaCodeHashtable.put("23", "黑龙江");
        areaCodeHashtable.put("31", "上海");
        areaCodeHashtable.put("32", "江苏");
        areaCodeHashtable.put("33", "浙江");
        areaCodeHashtable.put("34", "安徽");
        areaCodeHashtable.put("35", "福建");
        areaCodeHashtable.put("36", "江西");
        areaCodeHashtable.put("37", "山东");
        areaCodeHashtable.put("41", "河南");
        areaCodeHashtable.put("42", "湖北");
        areaCodeHashtable.put("43", "湖南");
        areaCodeHashtable.put("44", "广东");
        areaCodeHashtable.put("45", "广西");
        areaCodeHashtable.put("46", "海南");
        areaCodeHashtable.put("50", "重庆");
        areaCodeHashtable.put("51", "四川");
        areaCodeHashtable.put("52", "贵州");
        areaCodeHashtable.put("53", "云南");
        areaCodeHashtable.put("54", "西藏");
        areaCodeHashtable.put("61", "陕西");
        areaCodeHashtable.put("62", "甘肃");
        areaCodeHashtable.put("63", "青海");
        areaCodeHashtable.put("64", "宁夏");
        areaCodeHashtable.put("65", "新疆");
        areaCodeHashtable.put("71", "台湾");
        areaCodeHashtable.put("81", "香港");
        areaCodeHashtable.put("82", "澳门");
        areaCodeHashtable.put("91", "国外");
    }

    public static boolean IDCardValidate(String IDStr) throws ParseException {
        //将身份证最后一位的x转换为大写，便于统一
        IDStr = IDStr.toUpperCase();
        String Ai = "";
        // 判断号码的长度 15位或18位
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            logger.error("身份证号码长度应该为15位或18位");
            return false;
        }


        // 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            logger.error("身份证15位号码都应为数字;18位号码除最后一位外，都应为数字");
            return false;
        }


        // 判断出生年月是否有效
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 日期
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            logger.error("身份证出生日期无效");
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                logger.error("身份证生日不在有效范围");
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            logger.error("身份证月份无效");
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            logger.error("身份证日期无效");
            return false;
        }


        // 判断地区码是否有效
        Hashtable areacode = areaCodeHashtable;
        //如果身份证前两位的地区码不在Hashtable，则地区码有误
        if (areacode.get(Ai.substring(0, 2)) == null) {
            logger.error("身份证地区编码错误");
            return false;
        }

        if(isVarifyCode(Ai,IDStr)==false){
            logger.error("身份证校验码无效，不是合法的身份证号码");
            return false;
        }
        return true;
    }


    /**
     * 判断第18位校验码是否正确
        第18位校验码的计算方式：
     　　1. 对前17位数字本体码加权求和
     　　公式为：S = Sum(Ai  Wi), i = 0, ... , 16
     　　其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     　　2. 用11对计算结果取模
     　　Y = mod(S, 11)
     　　3. 根据模的值得到对应的校验码
     　　对应关系为：
     　　 Y值：     0  1  2  3  4  5  6  7  8  9  10
     　　校验码： 1  0  X  9  8  7  6  5  4  3   2
     */
    private static boolean isVarifyCode(String Ai,String IDStr) {
        String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4","3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = sum % 11;
        String strVerifyCode = VarifyCode[modValue];
        Ai = Ai + strVerifyCode;
        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                return false;

            }
        }
        return true;
    }

    /**
     * 判断字符串是否为数字,0-9重复0次或者多次
     * @param strnum
     * @return
     */
    private static boolean isNumeric(String strnum) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(strnum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从身份证号码中截取生日
     * @param idNum
     * @return
     */
    public static String getBirthday(String idNum)
    {
        return idNum.substring(6, 14);
    }

    /**
     * 通过身份证号获取年龄
     * @param idNum
     * @return
     */
    public static int getAge(String idNum){
        int leh = idNum.length();
        String dates="";
        if (leh == 18) {
            dates = idNum.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else{
            dates = idNum.substring(6, 8);
            return Integer.parseInt(dates);
        }
    }

}
