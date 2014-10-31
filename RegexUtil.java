package com.fanli.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: cjp
 * Date: 2008-12-17
 * Time: 19:14:59
 */
public class RegexUtil {
    public static String getValue(String content, String regex) {
        Matcher matcher =getMatcher(content, regex);
        if (matcher.find()) {
            return StringUtils.trimToEmpty(matcher.group(1));
        } else {
            return "";
        }
    }

    public static boolean matched(String content, String regex) {
        Matcher matcher = getMatcher(content, regex);
        return matcher.find();
    }

    public static List<String> getValues(String text, String regex) {
        List<String> result = new ArrayList<String>();
        Matcher matcher = getMatcher(text, regex);
        while (matcher.find()) {
            result.add(StringUtils.trimToEmpty(matcher.group(1)));
        }
        return result;
    }

    public static List<String> getOneGroups(String text, String regex) {
        List<String> result = new ArrayList<String>();
        Matcher matcher = getMatcher(text, regex);
        if (matcher.find()) {
            for (int i = 1; i < matcher.groupCount() + 1; i++) {
                result.add(StringUtils.trimToEmpty(matcher.group(i)));
            }
        }
        return result;
    }

    /**
     * @param text
     * @param regex ʽ
     * @return
     */
    public static List<List<String>> getGroups(String text, String regex) {
        List<List<String>> result = new ArrayList<List<String>>();

        Matcher matcher = getMatcher(text, regex);
        while (matcher.find()) {
            List<String> list = new ArrayList<String>();
            for (int i = 1; i < matcher.groupCount() + 1; i++) {
                list.add(StringUtils.trimToEmpty(matcher.group(i)));
            }
            result.add(list);
        }
        return result;
    }

    private static Matcher getMatcher(String content, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE);
        return pattern.matcher(content);
    }
}
