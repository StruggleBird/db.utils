
package com.zt.utils.commons;

import java.math.BigDecimal;

/**
 * ¿‡–Õ∏®÷˙¿‡
 * @author zt
 *
 */
public class TypeUtil
{

    public static Object Change(Object obj, Class<?> clazz)
    {
        if (obj == null)
        {
            return null;
        }

        String typeName = clazz.getSimpleName();
        String object = obj.toString();
        if (typeName.equalsIgnoreCase("string"))
        {
            return object;
        }
        else if (typeName.equalsIgnoreCase("int") || typeName.equalsIgnoreCase("integer"))
        {
            return Integer.parseInt(object);
        }
        else if (typeName.equalsIgnoreCase("double"))
        {
            return object;
        }
        else if (typeName.equalsIgnoreCase("float"))
        {
            return Float.parseFloat(object);
        }
        else if (typeName.equalsIgnoreCase("boolean"))
        {
            if ("1".equals(obj) || "y".equalsIgnoreCase(object))
                return true;
            if ("0".equals(obj) || "n".equalsIgnoreCase(object))
                return false;

            return Boolean.parseBoolean(object);
        }
        else if (typeName.equalsIgnoreCase("short"))
        {
            return Short.parseShort(object);
        }
        else if (typeName.equalsIgnoreCase("bigdecimal"))
        {
            return BigDecimal.valueOf(Double.parseDouble(object));
        }
        else
        {
            return obj;
        }

    }
}
