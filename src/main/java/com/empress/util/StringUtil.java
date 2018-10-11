package com.empress.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hystar
 * @date 2018/10/11 0011
 */
public class StringUtil {

    /**
     * 将1,2,3,4 格式的字符串转换成[1,2,3,4] 类型的集合
     *
     * @param str
     * @return
     */
    public static List<Integer> splitToListInt(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }
}
