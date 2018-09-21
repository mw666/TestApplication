package newmatch.zbmf.com.testapplication.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by **
 * on 2018/9/21.
 * 集合，set，map，数组相互转化的工具类
 *  本文来自 Ricky_Fung 的CSDN 博客 ，全文地址请点击：
 *  https://blog.csdn.net/top_code/article/details/10552827?utm_source=copy
 */

public class ConvertStructureUtil {

    /**
     * 将map转成keyList
     * @param map
     * @return
     */
    public static List<String> map2KeyList(Map<String, String> map) {
       /* 案例
       Map<String, String> map = new HashMap<String, String>();
        map.put("A", "ABC");
        map.put("K", "KK");
        map.put("L", "LV");

        // 将Map Key 转化为List
        List<String> mapKeyList = new ArrayList<String>(map.keySet());
        System.out.println("mapKeyList:"+mapKeyList);

        // 将Map Key 转化为List
        List<String> mapValuesList = new ArrayList<String>(map.values());
        System.out.println("mapValuesList:"+mapValuesList);
        */
       return new ArrayList<>(map.keySet());
    }

    /**
     * 将map转成valueList
     * @param map
     * @return
     */
    public static List<String> map2ValueList(Map<String,String> map){
        return new ArrayList<>(map.values());
    }

    /**
     * 将map的key转成set
     * @param map
     * @return
     */
    public static Set<String> map2KeySet(Map<String, String> map) {
       /*
        Map<String, String> map = new HashMap<String, String>();
        map.put("A", "ABC");
        map.put("K", "KK");
        map.put("L", "LV");

        // 将Map 的键转化为Set
        Set<String> mapKeySet = map.keySet();
        System.out.println("mapKeySet:"+mapKeySet);

        // 将Map 的值转化为Set
        Set<String> mapValuesSet = new HashSet<String>(map.values());
        System.out.println("mapValuesSet:"+mapValuesSet);
        */
        return map.keySet();
    }

    /**
     * 将map的value转成set
     * @param map
     * @return
     */
    public static Set<String> map2ValueSet(Map<String,String> map){
        return new HashSet<>(map.values());
    }

    /**
     * 将数组转成set
     * @param arr
     * @return
     */
    public static Set<String> array2Set(String[] arr) {
        /*
        String[] arr = {"AA","BB","DD","CC","BB"};
        //数组-->Set
        Set<String> set = new HashSet<String>(Arrays.asList(arr));
        System.out.println(set);
        */
        return new HashSet<>(Arrays.asList(arr));
    }

    /**
     * 将set转成Array
     * @param set
     * @return
     */
    public static String[] set2Array(Set<String> set) {
        /*
        Set<String> set = new HashSet<String>();
        set.add("AA");
        set.add("BB");
        set.add("CC");

        String[] arr = new String[set.size()];
        //Set-->数组
        set.toArray(arr);
        System.out.println(Arrays.toString(arr));
        */
        String[] arr = new String[set.size()];
        return set.toArray(arr);
    }

    /**
     * list转成set
     * @param list
     * @return
     */
    public static Set<String> list2Set(List<String> list) {
       /*
       List<String> list = new ArrayList<String>();
        list.add("ABC");
        list.add("EFG");
        list.add("LMN");
        list.add("LMN");

        //List-->Set
        Set<String> listSet = new HashSet<String>(list);
        System.out.println(listSet);
        */
        return new HashSet<>(list);
    }
    /*
    set转成list
     */
    public static List<String> set2List(Set<String> set) {
       /*
       Set<String> set = new HashSet<String>();
        set.add("AA");
        set.add("BB");
        set.add("CC");

        //Set --> List
        List<String> setList = new ArrayList<String>(set);
        System.out.println(setList);
        */
        return new ArrayList<String>(set);
    }

    /**
     * 集合转成Array
     */
    public static String[] list2Array(List<String> list) {
        /*
        //List-->数组
        List<String> list = new ArrayList<String>();
        list.add("AA");
        list.add("BB");
        list.add("CC");
        Object[] objects = list.toArray();//返回Object数组
        System.out.println("objects:"+Arrays.toString(objects));

        String[] arr = new String[list.size()];
        list.toArray(arr);//将转化后的数组放入已经创建好的对象中
        System.out.println("strings1:"+Arrays.toString(arr));
        */
        String[] arr = new String[list.size()];
        list.toArray(arr);//将转化后的数组放入已经创建好的对象中
        return arr;
    }

    /**
     * 数组转成集合
     * @param ss
     * @return
     */
    public static List<String> array2List(String[] ss) {
        /*
        //数组-->List
        String[] ss = {"JJ","KK"};
        List<String> list1 = Arrays.asList(ss);
        List<String> list2 = Arrays.asList("AAA","BBB");
        System.out.println(list1);
        System.out.println(list2);
        */
        return Arrays.asList(ss);
    }



}
