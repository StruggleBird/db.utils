
package com.zt.utils.db.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL WHERE
 * @author zt
 * @version 1.0  2010-3-26
 */
public class Where
{

    private StringBuilder sbWhere = new StringBuilder(); //where 条件语句

    private List<Object> params = new ArrayList<Object>();

    private boolean isFirstCondition = true;

    public boolean isEmpty()
    {
        return "".equals(sbWhere.toString().trim());
    }

    public Where addCondition(String condition)
    {
        sbWhere.append(appendAndStr()).append(condition);
        return this;
    }

    public Where eq(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" = ?");
        params.add(value);
        return this;
    }

    public Where like(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" like '%").append(value).append("%'");
        return this;
    }

    public Where leftLike(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" like '%").append(value).append("'");
        return this;
    }

    public Where rightLike(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" like '").append(value).append("%'");
        return this;
    }

    public Where gt(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" > ?");
        params.add(value);
        return this;
    }

    public Where ge(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" >= ?");
        params.add(value);
        return this;
    }

    public Where lt(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" < ?");
        params.add(value);
        return this;
    }

    public Where le(String key, Object value)
    {
        sbWhere.append(appendAndStr()).append(key).append(" <= ?");
        params.add(value);
        return this;
    }

    public String getWhereClause()
    {
        return this.sbWhere.toString();
    }

    public List<Object> getParams()
    {
        return this.params;
    }

    private String appendAndStr()
    {
        String andStr = " and ";
        if (isFirstCondition)
        {
            isFirstCondition = false;
            return "";
        }
        return andStr;
    }

}
