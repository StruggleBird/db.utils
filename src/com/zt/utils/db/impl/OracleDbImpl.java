
package com.zt.utils.db.impl;

import java.util.List;

import com.zt.utils.commons.StringUtil;
import com.zt.utils.db.AbstractDbUtil;
import com.zt.utils.db.mapping.Context;
import com.zt.utils.db.mapping.TableEntity;
import com.zt.utils.db.sql.Select;
/**
 * Oracle 数据操作实现类
 * @author zhoutao
 *
 */
public class OracleDbImpl extends AbstractDbUtil
{

    public <T> List<T> getList(Class<T> clazz, int pageSize, int pageIndex, String where, String order)
    {
        if (pageSize == 0)
        {
            return null;
        }

        TableEntity table = Context.getTablesMap().get(clazz.getSimpleName());

        Select select = new Select();

        String selectClause =
            "top "
                + pageSize
                + " "
                + StringUtil.join(",", openQuote(), table.getColumnNames().toArray(StringUtil.STRINGS_MODE),
                    closeQuote());
        select.setSelectClause(selectClause).setFromClause(openQuote() + table.getName() + closeQuote());

        StringBuilder sbWhere = new StringBuilder();
        if (!StringUtil.IsNullOrEmpty(where))
        {
            sbWhere.append(where);
        }
        else
        {
            sbWhere.append("1=1");
        }

        if (pageIndex != 0)
        {

            sbWhere.append(" and ").append(" rownum ").append(" between ").append(pageIndex * pageSize).append(" and ")
                .append((pageIndex + 1) * pageSize);
        }
        select.setWhereClause(sbWhere.toString());
        if (!StringUtil.IsNullOrEmpty(order))
        {
            select.setOrderByClause(order);
        }

        return getList(clazz, select.toStatementString());
    }

    @Override
    public char closeQuote()
    {

        return ' ';
    }

    @Override
    public char openQuote()
    {

        return ' ';
    }

}
