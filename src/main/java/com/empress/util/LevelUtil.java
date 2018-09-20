package com.empress.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 层级操作工具类
 *
 * @author Hystar
 * @date 2018/9/6
 */
public class LevelUtil {

    /**
     * 分隔符
     */
    public final static String SEPARATOR = ".";

    /**
     * 定义顶级层级的默认值
     */
    public final static String ROOT = "0";

    /**
     * 计算层级
     * 得到的结果例如：
     * 顶级层级：0
     * 二级层级：0.1
     * 三级层级：0.1.2/0.1.3
     * 二级层级：0.4
     *
     * @param parentLevel 上级层级
     * @param parentId    上级ID
     * @return
     */
    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            /**
             * 如果上级层级为空，则为顶级层级，直接返回0，设定为顶级层级
             */
            return ROOT;
        } else {
            /**
             * 上级层级不为空，则为二级或三级层级
             * 如果parentLevel=0 parentId=1，则为二级层级，返回结果为：0.1
             * 如果parentLevel=0.2 parentId=3，则为三级层级，返回结果为：0.2.3
             */
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
