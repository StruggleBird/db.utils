package com.zt.util;


import java.math.BigDecimal;

public class MoneyUtil
{

  public static double add(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1));
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2));
    return localBigDecimal1.add(localBigDecimal2).doubleValue();
  }

  public static double add(String paramString1, String paramString2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(paramString1);
    BigDecimal localBigDecimal2 = new BigDecimal(paramString2);
    return localBigDecimal1.add(localBigDecimal2).doubleValue();
  }

  public static double sub(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1));
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2));
    return localBigDecimal1.subtract(localBigDecimal2).doubleValue();
  }

  public static double sub(String paramString1, String paramString2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(paramString1);
    BigDecimal localBigDecimal2 = new BigDecimal(paramString2);
    return localBigDecimal1.subtract(localBigDecimal2).doubleValue();
  }

  public static double mul(double paramDouble1, double paramDouble2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1));
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2));
    return localBigDecimal1.multiply(localBigDecimal2).doubleValue();
  }

  public static double mul(String paramString1, String paramString2)
  {
    BigDecimal localBigDecimal1 = new BigDecimal(paramString1);
    BigDecimal localBigDecimal2 = new BigDecimal(paramString2);
    return localBigDecimal1.multiply(localBigDecimal2).doubleValue();
  }

  public static double div(double paramDouble1, double paramDouble2)
  {
    return div(paramDouble1, paramDouble2, 2);
  }

  public static double div(String paramString1, String paramString2)
  {
    return div(Double.parseDouble(paramString1), Double.parseDouble(paramString2), 2);
  }

  public static double div(double paramDouble1, double paramDouble2, int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble1));
    BigDecimal localBigDecimal2 = new BigDecimal(Double.toString(paramDouble2));
    return localBigDecimal1.divide(localBigDecimal2, paramInt, 4).doubleValue();
  }

  public static double div(String paramString1, String paramString2, int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    BigDecimal localBigDecimal1 = new BigDecimal(paramString1);
    BigDecimal localBigDecimal2 = new BigDecimal(paramString2);
    return localBigDecimal1.divide(localBigDecimal2, paramInt, 4).doubleValue();
  }

  public static double round(double paramDouble, int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    BigDecimal localBigDecimal1 = new BigDecimal(Double.toString(paramDouble));
    BigDecimal localBigDecimal2 = new BigDecimal("1");
    return localBigDecimal1.divide(localBigDecimal2, paramInt, 4).doubleValue();
  }

  public static double round(String paramString, int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    BigDecimal localBigDecimal1 = new BigDecimal(paramString);
    BigDecimal localBigDecimal2 = new BigDecimal("1");
    return localBigDecimal1.divide(localBigDecimal2, paramInt, 4).doubleValue();
  }

  public static void main(String[] paramArrayOfString)
  {
    System.out.println(mul(add(5.0D, 663.85000000000002D), 1.01D));
  }
}