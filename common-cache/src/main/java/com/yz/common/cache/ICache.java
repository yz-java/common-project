package com.yz.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 缓存处理接口
 * @author yangzhao 2015年10月16日
 */
public interface ICache {
	
	public Logger logger = LoggerFactory.getLogger(ICache.class);

	/** ---------------------String-----------------------------**/

	/**
	 *
	 * @param key
	 * @param value
     */
	public void set(String key,String value);

	public void set(String key,Object object);

	public String get(String key);

	public <T> T get(String key,Class<T> tClass);

	/** ---------------------Hash-------------------------------**/

	/**
	 * 设置hash值
	 * 
	 * @param mname
	 *            模块名
	 * @param key
	 *            hash key
	 * @param value
	 */
	public void hashSet(String mname, String key, Object value);

	/**
	 * 如果原来map里已经有了，则附加到map里去
	 * @param mname
	 * @param map
	 */
	public void hashSet(String mname, Map<String, Object> map);

	/**
	 * 从hash里得到指定key的value值
	 * @param <T>
	 * 
	 * @param mname
	 *            模块名
	 * @param key
	 *            hash key
	 */
	public <T> T hashGet(String mname, String key,Class<T> clazz);


	/**
	 * 得到所有数据
	 * 
	 * @param mname
	 *            模块名
	 */
	public Map hashGet(String mname);

	/**
	 * 移除
	 * 
	 * @param mname
	 *            模块名
	 * @param key
	 *            hash key
	 */
	public void hashRemove(String mname, String key);

	/**
	 * 移除
	 * 
	 * @param mname
	 *            模块名
	 */
	public void hashRemove(String mname);

	// ---------------------List-------------------------------

	/**
	 * 设置list值
	 * 
	 * @param mname
	 *            模块名
	 * @param value
	 */
	public void listAdd(String mname, String...value);

	/**
	 * 设置整个list，如果原来已经存在，则附加在原来的list里
	 * @param mname
	 * @param list
	 * @param <T>
	 */
	public <T> void listAdd(String mname, List<T> list);
	
	public void listAdd(String mname, Object obj);
	
	/**
	 * 得到整个list
	 * @param <T>
	 * 
	 * @param mname
	 * @return
	 */
	public <T> List<T> listGet(String mname,Class<T> clazz);

	/**
	 * 通过属性名和值 从List中获取该对象
	 * 
	 * @param mname
	 *            模块名
	 * @param field
	 *            属性名
	 * @param value
	 *            值
	 * @param clazz
	 * @return
	 */
	public default <T> List<T> listGet(String mname, String field, Object value, Class<T> clazz) {

		List<T> t  = this.listGet(mname,clazz);

		if (t==null||t.isEmpty()){
			return null;
		}

		List<T> result = t.stream().filter((obj) -> {
			boolean flag = false;
			String methodName = field.substring(0, 1).toUpperCase() + field.substring(1);
			try {
				Class<?> aClass = obj.getClass();
				Method method = aClass.getMethod("get" + methodName);
				Object data = method.invoke(obj);
				Field f = aClass.getDeclaredField(field);
				if (f.getName().equals(field)) {
					String fieldTypeName = f.getType().getSimpleName();
					if (fieldTypeName.equals("int") || fieldTypeName.equals("Integer")) {
						if (data instanceof Integer) {
							if ((int) data == (int) value) {
								flag = true;
							}
						}
					} else if (fieldTypeName.equals("Date")) {
						if (data instanceof Date) {
							if ((Date) data == (Date) value) {
								flag = true;
							}
						}
					} else if (fieldTypeName.equals("long") || fieldTypeName.equals("Long")) {
						if (data instanceof Long) {
							if ((long) data == (long) value) {
								flag = true;
							}
						}
					} else if (fieldTypeName.equals("String")) {
						if (data instanceof String) {
							if (((String) data).equals((String) value)) {
								flag = true;
							}
						}
					} else if (fieldTypeName.equals("double") || fieldTypeName.equals("Double")) {
						if (data instanceof Double) {
							if (((double) data) == ((double) value)) {
								flag = true;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return flag;
		}).collect(Collectors.toList());
		return result;
	}

	/**
	 * 通过键(key)-值(value)从List<Map>中获取匹配数据
	 * @param datas
	 * @param key
	 * @param value
     * @return
     */
	public default List<Map<String,Object>> listGet(List<Map<String,Object>> datas,String key,Object value){
		ArrayList<Map<String,Object>> objects = datas.stream().
				filter(map -> map.get(key).toString() == value).
				collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		return objects;
	}

	/**
	 * 移除
	 *
	 * @param mname
	 *            模块名
	 * @param value
	 *            值
	 */
	public <T> void listRemove(String mname, T value);

	/**
	 * 移除
	 * 
	 * @param mname
	 *            模块名
	 */
	public void listRemove(String mname);
	/**
	 * 通过属性名和值 从List中返回索引值
	 * @param mname
	 * @param field
	 * @param value
	 * @param clazz
	 * @return
	 */
	public default <T> int getListIndex(String mname, String field, Object value, Class<T> clazz) {
		List<T> ts = this.listGet(mname,clazz);
		try {
			for(int i=0;i<ts.size();i++){
				T t=ts.get(i);
				Field f = clazz.getDeclaredField(field);
				String methodName=field.substring(0,1).toUpperCase().concat(field.substring(1));
				Method declaredMethod = clazz.getDeclaredMethod("get"+methodName);
				Object val = declaredMethod.invoke(t);
				String name=f.getGenericType().getTypeName();
				if (name.equals("int") || name.equals("java.lang.Integer")) {
					if (val instanceof Integer) {
						if ((int) val == (int) value) {
							return i;
						}
					}
				} else if (name.equals("Date")) {
					if (val instanceof Date) {
						if ((Date) val == (Date) value) {
							return i;
						}
					}
				} else if (name.equals("long") || name.equals("Long")) {
					if (val instanceof Long) {
						if ((long) val == (long) value) {
							return i;
						}
					}
				} else if (name.equals("String")) {
					if (val instanceof String) {
						if (((String) val).equals((String) value)) {
							return i;
						}
					}
				} else if (name.equals("double") || name.equals("Double")) {
					if (val instanceof Double) {
						if (((double) val) == ((double) value)) {
							return i;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
