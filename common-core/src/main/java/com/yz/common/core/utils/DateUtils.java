package com.yz.common.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 日期工具类
 * 
 * @author huangchangan
 */
public class DateUtils {

	/** 一周英文对应的数字 */
	public static final Map<String, Integer> WEEK_MAP = new HashMap<String, Integer>();

	static {
		WEEK_MAP.put("Monday", 1);
		WEEK_MAP.put("Tuesday", 2);
		WEEK_MAP.put("Wednesday", 3);
		WEEK_MAP.put("Thursday", 4);
		WEEK_MAP.put("Friday", 5);
		WEEK_MAP.put("Saturday", 6);
		WEEK_MAP.put("Sunday", 7);
	}

	/** 一天毫秒数 */
	public static final long ONE_DAY_TIME = 24 * 60 * 60 * 1000L;

	public static long getNowTime() {
		return getCalendar().getTimeInMillis();
	}

	public static Date getNowDate() {
		return getCalendar().getTime();
	}

	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/** 获取当前时间秒数 */
	public static int getNowTimeForSecond() {
		return (int) (DateUtils.getNowTime() / 1000);
	}

	/**
	 * 获取下一天的开始时间 00:00:00.000
	 */
	public static long getNextDayStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 将日期分解成 年、月、日、时、分、秒的int数组，用于发给前台时间
	 * 
	 * @param date
	 * @return
	 */
	public static int[] splitDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int[] dates = new int[6];
		dates[0] = c.get(Calendar.YEAR);// 年
		dates[1] = c.get(Calendar.MONTH) + 1;// 月
		dates[2] = c.get(Calendar.DAY_OF_MONTH);// 日
		dates[3] = c.get(Calendar.HOUR_OF_DAY);// 小时
		dates[4] = c.get(Calendar.MINUTE);// 分
		dates[5] = c.get(Calendar.SECOND);// 秒
		return dates;
	}

	/**
	 * 判断指定日期是否是指定星期几
	 * 
	 * @param week
	 * @return
	 */
	public static boolean isWeekDay(Date date, int week) {
		boolean flag = false;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (week == getDateToWeek(c)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 判断两个日期是否是同一周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDiffWeek(Date date1, Date date2) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(getFirstDayOfWeek(date1));
		GregorianCalendar gc1 = (GregorianCalendar) Calendar.getInstance();
		gc1.setTime(getFirstDayOfWeek(date2));
		if (gc.getTime().getTime() == gc1.getTime().getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 得到输入日期是一个星期的第几天
	 * 
	 * @param gc
	 * @return
	 */
	public static int getDateToWeek(Calendar gc) {
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			return 7;
		case (Calendar.MONDAY):
			return 1;
		case (Calendar.TUESDAY):
			return 2;
		case (Calendar.WEDNESDAY):
			return 3;
		case (Calendar.THURSDAY):
			return 4;
		case (Calendar.FRIDAY):
			return 5;
		case (Calendar.SATURDAY):
			return 6;
		}
		return 0;
	}

	/**
	 * 得到输入日期这个星期的星期一
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int i = gc.get(Calendar.DAY_OF_WEEK);
		if (i==1){
			i=-6;
		}else{
			i = 2-i;
		}
		gc.add(Calendar.DAY_OF_WEEK,i);
		gc.set(Calendar.HOUR_OF_DAY,00);
		gc.set(Calendar.MINUTE,00);
		gc.set(Calendar.SECOND,00);
		gc.set(Calendar.MILLISECOND,00);
		return gc.getTime();
	}

	/**
	 * 得到输入日期这个月的第一天
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,0);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY,00);
		calendar.set(Calendar.MINUTE,00);
		calendar.set(Calendar.SECOND,00);
		calendar.set(Calendar.MILLISECOND,00);
		return calendar.getTime();
	}

	/**
	 * 根据传入的时间获取周最后一天（23：59：59 999）
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Date firstDayOfWeek = getFirstDayOfWeek(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDayOfWeek);
		calendar.add(Calendar.DAY_OF_WEEK,6);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND,999);
		return calendar.getTime();
	}

	/**
	 * 根据传入的时间获取当月最后一天（23：59：59 999）
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Date firstDayOfMonth = getFirstDayOfMonth(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDayOfMonth);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND,999);
		return calendar.getTime();
	}

	/**
	 * 得到这个日期的最后一秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTodayEnd(Date date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String day = sdf1.format(date);
		day = day + " 23:59:59";
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = null;
		try {
			result = sdf2.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取间隔一定天数的日期(当前时间+N天)
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getIntervalDay(Date date, int day) {
		Date result = new Date();
		result.setTime(date.getTime() + day * 1000L * 24 * 60 * 60);
		return result;
	}

	/**
	 * 获取间隔一定分钟的日期
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date getIntervalMinute(Date date, int minute) {
		Date result = new Date();
		result.setTime(date.getTime() + minute * 1000 * 60);
		return result;
	}

	/**
	 * 得到当前月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取两个时间的小时差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getHourDiff(Date date1, Date date2) {
		int i = Math.abs((int) ((date1.getTime() - date2.getTime()) / 1000 / 60 / 60));
		return i;
	}

	/**
	 * 得到规定时间的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取两个时间的天数差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDateDiff(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		int i = Math.abs((int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / ONE_DAY_TIME));
		return i;
	}

	/**
	 * 获取两个时间的分钟差
	 * 
	 * @param date1 结束时间
	 * @param date2 开始时间
	 * @return
	 */
	public static int getMinuteDiff(Date date1, Date date2) {
		int i = Math.abs((int) ((date1.getTime() - date2.getTime()) / 1000 / 60));
		return i;
	}

	/**
	 * 获取两个时间的秒数差
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getSecondDiff(Date date1, Date date2) {
		int i = Math.abs((int) ((date1.getTime() - date2.getTime()) / 1000));
		return i;
	}

	/**
	 * 
	 * 判断是否是零时
	 * 
	 * @return
	 */
	public static boolean isWeeHour() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 比较时间差距<br>
	 * 
	 * 计算结果 = 结束时间 - 开始时间
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param field
	 *            Calendar的时间域常量
	 * @return
	 */
	public static int diff(long start, long end, int field) {
		Calendar cstart = Calendar.getInstance();
		cstart.setTimeInMillis(start);
		Calendar cend = Calendar.getInstance();
		cend.setTimeInMillis(end);
		return cend.get(field) - cstart.get(field);
	}

	/**
	 * 获取总天数
	 * 
	 * @param time
	 *            毫秒数
	 * @return 返回含当天
	 */
	public static int getDayCount(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return getDayCount(c);
	}

	/**
	 * 获取总天数
	 * 
	 * @param time
	 *            毫秒数
	 * @return 返回含当天
	 */
	public static int getDayCount(Calendar c) {
		// 不能用毫秒直接除以一天的总毫秒数计算总天数
		return c.get(Calendar.YEAR) * 365 + c.get(Calendar.DAY_OF_YEAR);
	}

	public static int getHoursCount(long time) {
		return (int) (time / (60 * 60 * 1000L));
	}

	/**
	 * 计算从开始到截止经过了多少个everyDayTime
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            截止时间
	 * @param everyDayTime
	 *            每天的这个时间
	 * @return
	 */
	public static int getDiffCount(long startTime, long endTime, long everyDayTime) {
		int agoDays = (int) ((endTime - startTime) / ONE_DAY_TIME);
		int startDayCount = getDayCount(startTime);
		int endDayCount = getDayCount(endTime);
		long starDayTime = getTodayTime(startTime);
		long endDayTime = getTodayTime(endTime);
		// 当天情况下
		if (startDayCount == endDayCount)
			if (starDayTime < everyDayTime && endDayTime >= everyDayTime) {
				// 开始时间未过everyDayTime，当天截止时间过了everyDayTime
				agoDays++;
			}
		// 非当天的情况下
		if (startDayCount < endDayCount) {
			// 补上头或尾一天经过everyDayTime
			if (starDayTime < everyDayTime || endDayTime >= everyDayTime) {
				agoDays++;
			}
		}
		return agoDays;
	}

	private static long getTodayTime(long startTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(startTime);
		return c.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000L + c.get(Calendar.MINUTE) * 60 * 1000L + c.get(Calendar.SECOND) * 1000L + c.get(Calendar.MILLISECOND);
	}

	/**
	 * 格式化时间，将日期型转化为字符型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String FormatFullDate(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 格式化时间，将日期型转化为字符型 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String FormatDate(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	/**
	 * 
	 * @param date
	 * @param format 格式化样式
	 * @return
	 */
	public static String FormatDate(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 格式化时间，将字符型转化为日期型 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseFullDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = df.parse(str);
			return date;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 格式化时间，将字符型转化为日期型 yyyy-MM-dd
	 * 
	 * @param str
	 * @return
	 */
	public static Date parseDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = df.parse(str);
			return date;
		} catch (Exception ex) {
			return null;
		}
	}

	public static Date parseDate(String str, String formatStr) {
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		try {
			Date date = df.parse(str);
			return date;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 获取间隔一定天数的日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDayDate(Date date, int day) {
		date.setTime(date.getTime() + day * 1000L * 24 * 60 * 60);
		return date;
	}

	/**
	 * 判断日期合法性
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);
		try {
			df.parse(str);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/** 判断两个日期是否是同一周 */
	public static synchronized boolean getDateDiffWeek(java.util.Date date1, java.util.Date date2) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(getFirstDayOfWeek(date1));
		GregorianCalendar gc1 = (GregorianCalendar) Calendar.getInstance();
		gc1.setTime(getFirstDayOfWeek(date2));
		if (gc.getTime().getTime() == gc1.getTime().getTime()) {
			return true;
		}
		return false;
	}

	/** 判断两个日期是否是同一天 */
	public static boolean getDateIsSame(java.util.Date date1, java.util.Date date2) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(parseDate(FormatDate(date1)));
		GregorianCalendar gc1 = (GregorianCalendar) Calendar.getInstance();
		gc1.setTime(parseDate(FormatDate(date2)));
		if (gc.getTime().getTime() == gc1.getTime().getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 周对应的数字，数字为1~7，没有则返回0
	 * 
	 * @param d
	 * @return
	 */
	public static int weekDay(String d) {
		int v = 0;
		try {
			v = WEEK_MAP.get(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 判断当前是否和传入的周相等，传入比较的应为0~6之间的数
	 * 
	 * @param week
	 * @return
	 */
	public static boolean isNowWeek(int week) {
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(new Date());
		int nowWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		if (nowWeek < 0) {
			nowWeek = 7 + nowWeek;
		}
		return nowWeek == week;
	}

	/**
	 * 将长整形数字转换为日期格式，支持完整日期格式与短日期格式
	 * 
	 * @param time
	 * @param isFull
	 * @return
	 */
	public static Date timeToDate(long time, boolean isFull) {
		if (time <= 0l)
			return null;
		Date date = new Date(time);
		if (isFull)
			return date;
		return parseDate(FormatDate(date));
	}

	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}

	public static String get45Date() {
		long currentTime = System.currentTimeMillis() + 45 * 60 * 1000;
		Date date = new Date(currentTime);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(date);
	}

	/**
	 * 通过时间戳获取时间
	 * @param time
	 * @return
	 */
	public static Date getDateByTimeStamp(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 当前时间加或减（天）
	 * @param day
	 * @return
	 */
	public static Date getDate(int day) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.add(Calendar.DATE, day);
		return gc.getTime();
	}
	/**
	 * 时间加或减分钟
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date getDate(Date date,int minute){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.add(Calendar.MINUTE, minute);
		return gc.getTime();
	}

	/**
	 * 获取nowDate时间的月份差
	 * @param nowDate
	 * @param month
	 * @return
	 */
	public static Date timeDifferenceForMonth(Date nowDate,int month){
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(nowDate);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 获取某年某月第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year,int month){
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime());
		return firstDayOfMonth;
	}

	/**
	 * 获取某年某月最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year,int month){
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

}
