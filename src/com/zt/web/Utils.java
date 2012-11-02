package com.zt.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

public class Utils
{
  public static String filter(String paramString)
  {
    if (paramString == null)
      return null;
    char[] arrayOfChar = new char[paramString.length()];
    paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    StringBuffer localStringBuffer = new StringBuffer(arrayOfChar.length + 50);
    for (int i = 0; i < arrayOfChar.length; ++i)
      switch (arrayOfChar[i])
      {
      case '<':
        localStringBuffer.append("&lt;");
        break;
      case '>':
        localStringBuffer.append("&gt;");
        break;
      case '&':
        localStringBuffer.append("&amp;");
        break;
      case '"':
        localStringBuffer.append("&quot;");
        break;
      case '\'':
        localStringBuffer.append("&#39;");
        break;
      default:
        localStringBuffer.append(arrayOfChar[i]);
      }
    return localStringBuffer.toString();
  }

  public static void write(PageContext paramPageContext, String paramString)
    throws JspException
  {
    JspWriter localJspWriter = paramPageContext.getOut();
    try
    {
      localJspWriter.print(paramString);
    }
    catch (IOException localIOException)
    {
      throw new JspException("write(" + paramPageContext + "," + paramString + ")" + localIOException.getMessage());
    }
  }

  public static void writePrevious(PageContext paramPageContext, String paramString)
    throws JspException
  {
    JspWriter localJspWriter = paramPageContext.getOut();
    if (localJspWriter instanceof BodyContent)
      localJspWriter = ((BodyContent)localJspWriter).getEnclosingWriter();
    try
    {
      localJspWriter.print(paramString);
    }
    catch (IOException localIOException)
    {
      throw new JspException("writePrevious(" + paramPageContext + "," + paramString + ")" + localIOException.getMessage());
    }
  }

  public static HttpServletRequest getRequest(PageContext paramPageContext)
  {
    return ((HttpServletRequest)paramPageContext.getRequest());
  }

  public static HttpServletResponse getResponse(PageContext paramPageContext)
  {
    return ((HttpServletResponse)paramPageContext.getResponse());
  }

  public static HttpSession getSession(PageContext paramPageContext)
  {
    return paramPageContext.getSession();
  }
}