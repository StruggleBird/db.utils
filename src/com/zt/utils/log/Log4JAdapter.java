
package com.zt.utils.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 日志接口 log4j的适配器
 * @author zhoutao
 *
 */
public class Log4JAdapter extends LogAdapter
{
    private Logger logger;

    public static final String SUPER_FQCN = LogAdapter.class.getName();
    public static final String SELF_FQCN = Log4JAdapter.class.getName();

    private static boolean hasTrace;

    public Log4JAdapter(String className)
    {
        logger = Logger.getLogger(className);
        isFatalEnabled = logger.isEnabledFor(Level.FATAL);
        isErrorEnabled = logger.isEnabledFor(Level.ERROR);
        isWarnEnabled = logger.isEnabledFor(Level.WARN);
        isInfoEnabled = logger.isEnabledFor(Level.INFO);
        isDebugEnabled = logger.isEnabledFor(Level.DEBUG);
        if (hasTrace)
            isTraceEnabled = logger.isEnabledFor(Level.TRACE);
    }

    static
    {
        try
        {
            Level.class.getDeclaredField("TRACE");
            hasTrace = true;
        }
        catch (Throwable e)
        {
        }
    }

    protected void log(int level, Object message, Throwable tx)
    {
        switch (level)
        {
            case LEVEL_FATAL:
                logger.log(SUPER_FQCN, Level.FATAL, message, tx);
                break;
            case LEVEL_ERROR:
                logger.log(SUPER_FQCN, Level.ERROR, message, tx);
                break;
            case LEVEL_WARN:
                logger.log(SUPER_FQCN, Level.WARN, message, tx);
                break;
            case LEVEL_INFO:
                logger.log(SUPER_FQCN, Level.INFO, message, tx);
                break;
            case LEVEL_DEBUG:
                logger.log(SUPER_FQCN, Level.DEBUG, message, tx);
                break;
            case LEVEL_TRACE:
                if (hasTrace)
                    logger.log(SUPER_FQCN, Level.TRACE, message, tx);
                break;
            default:
                break;
        }
    }

    public void debug(Object message, Throwable t)
    {
        if (isDebugEnabled)
        {
            logger.debug(message, t);
        }
    }

    public void error(Object message, Throwable t)
    {
        if (isErrorEnabled)
        {
            logger.error(message, t);
        }
    }

    public void fatal(Object message, Throwable t)
    {
        if (isFatalEnabled)
        {
            logger.fatal(message, t);
        }
    }

    public void info(Object message, Throwable t)
    {
        if (isInfoEnabled)
        {
            logger.info(message, t);
        }
    }

    public void trace(Object message, Throwable t)
    {
        if (isTraceEnabled)
        {
            logger.trace(message, t);
        }
    }

    public void warn(Object message, Throwable t)
    {
        if (isWarnEnabled)
        {
            logger.warn(message, t);
        }
    }

}
