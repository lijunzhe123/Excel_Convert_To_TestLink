package com.jyall.utils.email.springmail.dom4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;

public class StringUtil
        extends StringUtils
{
    private static final String default_char_encode = "utf-8";

    public static String encode(String s)
    {
        String str = null;
        if (!isEmpty(s)) {
            try
            {
                str = URLEncoder.encode(s, "utf-8");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String filterControlLetter(String str)
    {
        String targetStr = str;
        if ((str != null) && (str.length() > 0))
        {
            char[] charArr = str.toCharArray();
            char[] targetCharArr = new char[charArr.length];
            int i = 0;
            char[] arrayOfChar1;
            int j = (arrayOfChar1 = charArr).length;
            for (i = 0; i < j; i++)
            {
                char c = arrayOfChar1[i];
                if (c >= ' ') {
                    targetCharArr[(i++)] = c;
                }
            }
            targetStr = new String(targetCharArr, 0, i);
        }
        return targetStr;
    }

    public static String[] stringToArray(String str, String sep_sr)
    {
        StringTokenizer strToken = new StringTokenizer(str, sep_sr);
        int tokenCount = strToken.countTokens();
        String[] str_array = new String[tokenCount];
        for (int i = 0; i < tokenCount; i++) {
            str_array[i] = strToken.nextToken();
        }
        return str_array;
    }
}
