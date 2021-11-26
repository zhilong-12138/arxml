package com.zzl.demo.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ding
 * @date 2020-12-10 09:32
 */
public class ArrayUtil {

    /**
     * 判断字符串是否在 String 数组内
     *
     * @param input 要检查的字符串
     * @param array 字符串数组
     * @return boolean
     */
    public static boolean inArray(String input, String[] array) {
        for (String s : array) {
            if (s.equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inArray(Integer input, Integer[] array) {
        for (Integer s : array) {
            if (s.equals(input)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串数组 集中为一个字符串，以glue参数做分隔
     *
     * @param strings 字符串数组
     * @param glue    分隔符
     * @return String
     */
    public static String implode(String[] strings, String glue) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            result.append(strings[i]);
            if (i < strings.length - 1) {
                result.append(glue);
            }
        }
        return result.toString();
    }

    /**
     * 将Integer串数组 集中为一个字符串，以glue参数做分隔
     *
     * @param integers 数组
     * @param glue     用以分隔的字符串
     * @return String
     */
    public static String implode(Integer[] integers, String glue) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < integers.length; i++) {
            result.append(integers[i]);
            if (i < integers.length - 1) {
                result.append(glue);
            }
        }
        return result.toString();
    }

    /**
     * 将lists中的某一个值提取为map的key，做为索引，重复存在则覆盖
     *
     * @param lists 数据列表
     * @param key   确保 lists.get(idx).getKey()的值在此lists里唯一，否则向上覆盖
     * @param <T>   List<类>
     * @return Map
     * @throws Exception 当 指定 key getKey()不存在等一些异常时抛出
     */
    public static <T> Map<String, T> listToMap(List<T> lists, String key) throws Exception {
        Map<String, T> result = new HashMap<>(lists.size());

        for (T list : lists) {
            Method method = list.getClass().getMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
            result.put(method.invoke(list).toString(), list);
        }
        return result;
    }

    /**
     * 将lists中的某一个值提取为map的key，做为索引，重复存在则覆盖
     *
     * @param lists 数据列表
     * @param key   确保 lists.get(idx).getKey()的值在此lists里唯一，否则向上覆盖
     * @param <T>   List<类>
     * @return Map
     * @throws Exception 当 指定 key getKey()不存在等一些异常时抛出
     */
    public static <T> Map<Integer, T> listToIntMap(List<T> lists, String key) throws Exception {
        Map<Integer, T> result = new HashMap<>(lists.size());

        for (T list : lists) {
            Method method = list.getClass().getMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
            result.put(Integer.valueOf(method.invoke(list).toString()), list);
        }
        return result;
    }


    /**
     * 将lists中的某一个值提取为map的key，做为索引，以List为值，key重复不会覆盖
     *
     * @param lists 数据列表
     * @param key   lists.get(idx).getKey()的值在此lists 重复会以List形式返回
     * @return Map
     * @throws Exception 当 指定 key getKey()不存在等一些异常时抛出
     */
    public static <T> Map<String, List<T>> listToListMap(List<T> lists, String key) throws Exception {
        Map<String, List<T>> result = new HashMap<>(lists.size());

        for (T list : lists) {
            Method method = list.getClass().getMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
            String hashKey = method.invoke(list).toString();
            result.computeIfAbsent(hashKey, k -> new ArrayList<>());
            result.get(hashKey).add(list);
        }
        return result;
    }

    /**
     * 将lists中的每一个 key 对应的值提取出来，组成ArrayList 返回
     *
     * @param lists 数据列表
     * @param key   lists.get(idx).getKey()的值
     * @param <T>   List<类>
     * @return List<Object>
     * @throws Exception 当 指定 key getKey()不存在等一些异常时抛出
     */
    public static <T> List<Object> listToArray(List<T> lists, String key) throws Exception {
        List<Object> result = new ArrayList<>();
        for (T list : lists) {
            Method method = list.getClass().getMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
            result.add(method.invoke(list));
        }

        return result;
    }

    /**
     * 将lists中的每一个 key 对应的值提取出来，返回组成的String数组
     *
     * @param lists 数据列表
     * @param key   lists.get(idx).getKey()的值
     * @param <T>   List<类>
     * @return String[] 将获取的数据转成String返回数组
     * @throws Exception 可能会报 getXxx 方法不存在，所以需要捕获异常
     */
    public static <T> String[] listToStringArray(List<T> lists, String key) throws Exception {
        String[] result = new String[lists.size()];

        for (int i = 0; i < lists.size(); i++) {
            Method method = lists.get(i).getClass().getMethod("get" + key.substring(0, 1).toUpperCase() + key.substring(1));
            result[i] = method.invoke(lists.get(i)).toString();
        }

        return result;
    }

    /**
     * 将一个 List<String> 的列表转为 String[] 数组
     *
     * @return String[]
     */
    public static String[] convertToStringArray(List<String> list) {
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 将一个 List<Integer> 的列表转为 Integer[] 数组
     *
     * @return Integer[]
     */
    public static Integer[] convertToIntArray(List<Integer> list) {
        Integer[] result = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * String[] 数组 转 Integer[]
     * 前提：String[] 数组里的值都为数字
     *
     * @param strings String[]("111","222")
     * @return Integer[](111, 222)
     */
    public static Integer[] convertStringToIntArray(String[] strings) {
        Integer[] ids = new Integer[strings.length];
        try {
            for (int i = 0; i < strings.length; i++) {
                ids[i] = Integer.valueOf(strings[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    /**
     * String 数组去重
     *
     * @param strings String[]
     * @return String[]
     */
    public static String[] stringArrayUnique(String[] strings) {
        List<String> list = new ArrayList<>();

        for (String string : strings) {
            if (!list.contains(string)) {
                list.add(string);
            }
        }

        return convertToStringArray(list);
    }

    /**
     * Integer 数组去重
     *
     * @param ints Integer[]
     * @return Integer[]
     */
    public static Integer[] intArrayUnique(Integer[] ints) {
        List<Integer> list = new ArrayList<>();

        for (Integer num : ints) {
            if (!list.contains(num)) {
                list.add(num);
            }
        }

        return convertToIntArray(list);
    }

    // public static <T> T[] arrayMerge(T[]... arrays) {
    //     //T[] ts =(T[]) new Object[10];
    //     int size = 0;
    //     for (int i = 1; i < arrays.length; i++) {
    //         for (int j = 0; j < arrays[i].length; j++) {
    //             size++;
    //         }
    //     }
    //
    //     T[] merges = (T[])new Object[size];
    //     int x = 0;
    //     for (int i = 1; i < arrays.length; i++) {
    //         for (int j = 0; j < arrays[i].length; j++) {
    //             merges[x] = arrays[i][j];
    //             x++;
    //         }
    //     }
    //
    //     return merges;
    // }
    //
    // public static void main(String[] args) {
    //     String[] aa = (String[]) arrayMerge(new String[]{"11", "22"}, new String[]{"33", "44"}, new String[]{"55", "66"});
    //     System.out.println(implode(aa,","));
    // }
}
