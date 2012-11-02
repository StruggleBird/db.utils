package com.zt.security;

public class Escape
{
  private static final String[] _$2 = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E", "7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F", "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };
  private static final byte[] _$1 = { 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 63, 63, 63, 63, 63, 63, 63, 10, 11, 12, 13, 14, 15, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 10, 11, 12, 13, 14, 15, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63 };

  public static String escape(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramString.length();
    for (int j = 0; j < i; ++j)
    {
      int k = paramString.charAt(j);
      if ((65 <= k) && (k <= 90))
      {
        localStringBuffer.append((char)k);
      }
      else if ((97 <= k) && (k <= 122))
      {
        localStringBuffer.append((char)k);
      }
      else if ((48 <= k) && (k <= 57))
      {
        localStringBuffer.append((char)k);
      }
      else if ((k == 45) || (k == 95) || (k == 46) || (k == 33) || (k == 126) || (k == 42) || (k == 39) || (k == 40) || (k == 41))
      {
        localStringBuffer.append((char)k);
      }
      else if (k <= 127)
      {
        localStringBuffer.append('%');
        localStringBuffer.append(_$2[k]);
      }
      else
      {
        localStringBuffer.append('%');
        localStringBuffer.append('u');
        localStringBuffer.append(_$2[(k >>> 8)]);
        localStringBuffer.append(_$2[(0xFF & k)]);
      }
    }
    return localStringBuffer.toString();
  }

  public static String unescape(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = paramString.length();
    while (i < j)
    {
      int k = paramString.charAt(i);
      if ((65 <= k) && (k <= 90))
      {
        localStringBuffer.append((char)k);
      }
      else if ((97 <= k) && (k <= 122))
      {
        localStringBuffer.append((char)k);
      }
      else if ((48 <= k) && (k <= 57))
      {
        localStringBuffer.append((char)k);
      }
      else if ((k == 45) || (k == 95) || (k == 46) || (k == 33) || (k == 126) || (k == 42) || (k == 39) || (k == 40) || (k == 41))
      {
        localStringBuffer.append((char)k);
      }
      else if (k == 37)
      {
        int l = 0;
        if ('u' != paramString.charAt(i + 1))
        {
          l = l << 4 | _$1[paramString.charAt(i + 1)];
          l = l << 4 | _$1[paramString.charAt(i + 2)];
          i += 2;
        }
        else
        {
          l = l << 4 | _$1[paramString.charAt(i + 2)];
          l = l << 4 | _$1[paramString.charAt(i + 3)];
          l = l << 4 | _$1[paramString.charAt(i + 4)];
          l = l << 4 | _$1[paramString.charAt(i + 5)];
          i += 5;
        }
        localStringBuffer.append((char)l);
      }
      else
      {
        localStringBuffer.append((char)k);
      }
      ++i;
    }
    return localStringBuffer.toString();
  }

  public static void main(String[] args)
  {
	  System.out.println( escape("除数不能为0").toLowerCase());
  }
}