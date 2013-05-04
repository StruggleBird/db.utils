
package com.zt.utils.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统日志的适配器
 * @author zhoutao
 *
 */
public class SysLogAdapter extends LogAdapter
{

    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SysLogAdapter()
    {
        isInfoEnabled = true;
        isDebugEnabled = true;
    }

    public void debug(Object message, Throwable t)
    {
        if (isDebugEnabled())
            printOut("DEBUG", message, t);
    }

    public void error(Object message, Throwable t)
    {
        if (isErrorEnabled())
            errorOut("ERROR", message, t);
    }

    public void fatal(Object message, Throwable t)
    {
        if (isFatalEnabled())
            errorOut("FATAL", message, t);
    }

    public void info(Object message, Throwable t)
    {
        if (isInfoEnabled())
            printOut("INFO", message, t);
    }

    public void trace(Object message, Throwable t)
    {
        if (isTraceEnabled())
            printOut("TRACE", message, t);
    }

    public void warn(Object message, Throwable t)
    {
        if (isWarnEnabled())
            errorOut("WARN", message, t);
    }

    private void printOut(String level, Object message, Throwable t)
    {
        System.out.printf("%s %s [%s] %s\n", DATE_FORMAT.format(new Date()), level, Thread.currentThread().getName(),
            message);
        if (t != null)
            t.printStackTrace(System.out);
    }

    private void errorOut(String level, Object message, Throwable t)
    {
        System.err.printf("%s %s [%s] %s\n", DATE_FORMAT.format(new Date()), level, Thread.currentThread().getName(),
            message);
        if (t != null)
            t.printStackTrace(System.err);
    }

    @Override
    protected void log(int level, Object message, Throwable tx)
    {
        switch (level)
        {
            case LEVEL_FATAL:
                fatal(message, tx);
                break;
            case LEVEL_ERROR:
                error(message, tx);
                break;
            case LEVEL_WARN:
                warn(message, tx);
                break;
            case LEVEL_INFO:
                info(message, tx);
                break;
            case LEVEL_DEBUG:
                debug(message, tx);
                break;
            case LEVEL_TRACE:
                trace(message, tx);
                break;
        }
    }

}
