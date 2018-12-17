package org.poem;


import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author poem
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     *
     * @param strList
     * @param sep
     * @return
     */
    public static String mkString(Collection<String> strList, String sep) {
        StringBuilder sb = new StringBuilder();
        for (String str : strList) {
            if (isEmpty(str)) {
                continue;
            }
            sb.append(str).append(sep);
        }
        if (sb.length() >= sep.length()) {
            return sb.substring(0, sb.length() - sep.length());
        }
        return "";

    }


    /**
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否含有特殊字符或中文字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialCharOrChinaChar(String str) {
        String regEx = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        boolean special = m.find();
        if (special) {
            return true;
        }
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(str);
        boolean chinese = mat.find();
        if (chinese) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否含有特殊字符以及为空串
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        boolean special = m.find();
        if (special) {
            return true;
        }
        if (str.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    /**
     * 都是空的
     * @param strs
     * @return
     */
    public static boolean allIsEmpty(String... strs) {
        if (strs == null) {
            return true;
        }
        for (String str : strs) {
            if (!isEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 有的是空的
     * @param strs
     * @return
     */
    public static boolean anyIsEmpty(String... strs) {
        if (strs == null) {
            return true;
        }
        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除数据
     * @param sourceStr
     * @param subStr
     * @return
     */
    public static String dropLastStr(String sourceStr, String subStr) {
        if (isEmpty(sourceStr) || isEmpty(subStr)) {
            return sourceStr;
        }
        int lastIndex = sourceStr.lastIndexOf(subStr);
        if (lastIndex == -1) {
            return sourceStr;
        }
        return sourceStr.substring(0, lastIndex);

    }


}
