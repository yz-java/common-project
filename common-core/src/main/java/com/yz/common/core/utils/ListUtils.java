package com.yz.common.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @auther yangzhao
 * create by 17/10/10
 */
public class ListUtils {

    /**
     * 从list中随机获取指定个数元素
     * @param list
     * @param count
     * @param <T>
     * @return
     */
    public static  <T> List<T> getRandomFromArray(List<T> list, int count) {

        List result = new ArrayList();
        Random random = new Random();

        // 要随机取的元素个数
        if (count > list.size() || count < 0)
            return list;

        int n = 0;
        while (true) {

            if (n == count) // 取到足量随机数后退出循环
                break;
            int size = list.size();
            int temp = random.nextInt(size);
            result.add(list.get(temp));
            list.remove(temp);
            n++;
        }
        return result;
    }
}
