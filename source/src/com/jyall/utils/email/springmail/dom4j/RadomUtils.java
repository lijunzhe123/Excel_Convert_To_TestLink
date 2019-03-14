package com.jyall.utils.email.springmail.dom4j;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class RadomUtils
{
    public static String doubleToString(double d)
    {
        String i = DecimalFormat.getInstance().format(d);
        String result = i.replaceAll(",", "");
        return result;
    }

    public static String getrandomLengthString(int strLength)
    {
        int maxNum = 36;

        int count = 0;
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < strLength)
        {
            int i = Math.abs(r.nextInt(36));
            if ((i >= 0) && (i < str.length))
            {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    public static int getint(Long num)
    {
        Random random1 = new Random(num.longValue());
        System.out.println(random1.nextInt());
        return random1.nextInt();
    }

    public static float getFloat(Long num)
    {
        Random random1 = new Random(num.longValue());
        System.out.println(random1.nextFloat());
        return random1.nextFloat();
    }

    public static boolean getBoolean(Long num)
    {
        Random random1 = new Random(num.longValue());
        System.out.println(random1.nextBoolean());
        return random1.nextBoolean();
    }

    public static int getTen()
    {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(Math.abs(random.nextInt()) % 10);
        }
        return Math.abs(random.nextInt()) % 10;
    }

    public static long getBetweenAnd(Long Min, Long Max)
    {
        long Temp = Math.round(Math.random() * (Max.longValue() - Min.longValue()) + Min.longValue());
        return Temp;
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getBetweenAnd(Long.valueOf(10000L), Long.valueOf(100L)));
        }
    }
}
