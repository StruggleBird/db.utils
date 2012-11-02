package com.zt.security;


import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Passport
{
  public static String encrypt(String paramString1, String paramString2)
  {
    Random localRandom = new Random();
    localRandom.setSeed(System.currentTimeMillis());
    String str1 = "" + (localRandom.nextInt() % 32000);
    String str2 = Encryption.generateKey(str1, "MD5");
    int i = 0;
    String str3 = "";
    for (int j = 0; j < paramString1.length(); ++j)
    {
      i = (i == str2.length()) ? 0 : i;
      str3 = str3 + str2.charAt(i);
      char c = (char)(paramString1.charAt(j) ^ str2.charAt(i));
      str3 = str3 + c;
      ++i;
    }
    String str4 = passportKey(str3, paramString2);
    return new BASE64Encoder().encode(str4.getBytes());
  }

  public static String decrypt(String paramString1, String paramString2)
  {
    byte[] arrayOfByte = null;
    try
    {
      arrayOfByte = new BASE64Decoder().decodeBuffer(paramString1);
      paramString1 = new String(arrayOfByte);
    }
    catch (Exception localException)
    {
      return null;
    }
    paramString1 = passportKey(paramString1, paramString2);
    String str = "";
    for (int i = 0; i < paramString1.length(); ++i)
    {
      char c = (char)(paramString1.charAt(i) ^ paramString1.charAt(++i));
      str = str + c;
    }
    return str;
  }

  public static String passportKey(String paramString1, String paramString2)
  {
    String str1 = Encryption.generateKey(paramString2, "MD5");
    int i = 0;
    String str2 = "";
    for (int j = 0; j < paramString1.length(); ++j)
    {
      i = (i == str1.length()) ? 0 : i;
      char c = (char)(paramString1.charAt(j) ^ str1.charAt(i));
      str2 = str2 + c;
      ++i;
    }
    return str2;
  }

  public static String passportEncode(Map<?,?> paramMap)
  {
    Set<?> localSet = paramMap.keySet();
    String str1 = "";
    String str2 = "";
    Iterator<?> localIterator = localSet.iterator();
    if (localIterator.hasNext())
    {
      str1 = (String)localIterator.next();
      try
      {
        str2 = str2 + str1 + "=" + ((String)paramMap.get(str1)) + "&";
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return "";
      }
    }
    if (str2.length() > 0)
      return str2.substring(0, str2.length() - 1);
    return "";
  }
}