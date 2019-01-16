package com.utils;

import java.util.ArrayList;
import java.util.List;

public class Pre_seg {

    private static final String PREFIX = "pre";//左前缀标识
    private static final String SELF = "self";//完整标识

    public static String deSpeChar(String s){
        String result;
        String regex = "[^\\dA-Za-z\\u4e00-\\u9fa5]";
        result = s.replaceAll(regex, "");
        return result;
    }

    public static String numConversion(String s){
        char[] list = s.toCharArray();

        for (int i = 0; i < list.length; i ++){
            switch (list[i]){
                case '0':
                    list[i] = '零';
                    break;
                case '1':
                    list[i] = '一';
                    break;
                case '2':
                    list[i] = '二';
                    break;
                case '3':
                    list[i] = '三';
                    break;
                case '4':
                    list[i] = '四';
                    break;
                case '5':
                    list[i] = '五';
                    break;
                case '6':
                    list[i] = '六';
                    break;
                case '7':
                    list[i] = '七';
                    break;
                case '8':
                    list[i] = '八';
                    break;
                case '9':
                    list[i] = '九';
                    break;
            }
        }

        String result = new String(list);
        return result;
    }

    public static List<String> segmentation(String s){
        List<String> list = new ArrayList<>();

        for (int i = 1; i < s.length(); i ++){
            list.add(PREFIX + s.substring(0,i));
        }

        list.add(SELF + s);

        return list;
    }

    public static void main(String[] args){
        String s = "你好*3(90,_hello$end结束:";
        List<String> list = segmentation(numConversion(deSpeChar(s)));

        for (String item : list){
            System.out.println(item);
        }
    }
}
