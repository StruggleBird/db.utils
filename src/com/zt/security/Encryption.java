package com.zt.security;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption
{
  public static String generateKey(String paramString1, String paramString2)
  {
    MessageDigest localMessageDigest = null;
    try
    {
      localMessageDigest = MessageDigest.getInstance(paramString2);
      localMessageDigest.update(paramString1.getBytes("UTF8"));
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    byte[] arrayOfByte = localMessageDigest.digest();
    String str = "";
    for (int i = 0; i < arrayOfByte.length; ++i)
      str = str + Integer.toHexString(0xFF & arrayOfByte[i] | 0xFFFFFF00).substring(6);
    return str;
  }

  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    System.out.println(URLEncoder.encode(Passport.encrypt("modify:account=xxx&pwd=xxx&email=xxx&mid=xxx&verify=xxx", "turboshop"), "utf-8"));
  }
}