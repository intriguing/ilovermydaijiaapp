package com.daijia.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
  private static final Pattern mailPattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
  
  public static String getLocDesc(double paramDouble)
  {
    if (paramDouble < 0.0D) {
      return "0m";
    }
    if (paramDouble < 100.0D) {
      return String.valueOf(paramDouble + "m");
    }
    paramDouble /= 1000.0D;
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
    localNumberFormat.setMaximumFractionDigits(2);
    return localNumberFormat.format(paramDouble) + "km";
  }
  
  public static String getTimeDesc(String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      Date paramString11 = localSimpleDateFormat.parse(paramString1);
      long l = (localSimpleDateFormat.parse(paramString2).getTime() - paramString11.getTime()) / 60000L;
      if (l < 1L) {
        return "刚刚";
      }
      if (l < 60L) {
        return l + "分前";
      }
      if (l / 60L < 24L) {
        return new SimpleDateFormat("HH:mm").format(paramString1);
      }
      paramString1 = new SimpleDateFormat("yy-MM-dd HH:mm").format(paramString1);
      return paramString1;
    }
    catch (ParseException paramString11)
    {
      paramString11.printStackTrace();
    }
    return "";
  }
  
  public static String getVoiceLengthDesc(int paramInt)
  {
    int i = paramInt / 1000;
    paramInt = paramInt % 1000 / 100;
    return i + "." + paramInt + "''";
  }
  
  public static boolean isEmail(String paramString)
  {
    return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(paramString).find();
  }
  
  public static boolean isInt(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return false;
    }
    try
    {
      Integer.parseInt(paramString);
      return true;
    }
    catch (Exception paramStrings) {}
    return false;
  }
  
  public static boolean isMobile(String paramString)
  {
    return paramString.length() == 11;
  }
  
  public static boolean isNullOrEmpty(String paramString)
  {
    if (paramString == null) {return true;}
    if (paramString.length() == 0) {
      return true;
    }
    return false;
  }
}
