
package com.zt.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.zt.utils.commons.StringUtil;

/**
 * 数据库连接管理类
 * 
 * @author zt
 * 
 */
public class ConnectionManager
{

    public static final String DRIVER_URL = DbConfigUtil.getInstance().getProperty("driverClassName");

    public static final String CONN_URL = DbConfigUtil.getInstance().getProperty("url");

    private static final String CONN_NAME = DbConfigUtil.getInstance().getProperty("username");

    private static final String CONN_PWD = DbConfigUtil.getInstance().getProperty("password");

    public static final String CONN_TYPE = DbConfigUtil.getInstance().getProperty("connectionType");

    private static final String JNDI_NAME = DbConfigUtil.getInstance().getProperty("jndiName");

    private static DataSource dataSource = null;

    public static void setDataSource(DataSource dataSource)
    {
        ConnectionManager.dataSource = dataSource;
    }

    /**
     *  获取数据库连接
     *  同步方法 保证同一时间只能被调用一次
     * @return java.sql.Connection
     */
    public static synchronized Connection GetConnection()
    {
        try
        {
            if (dataSource == null)
            {
                if ("dbcp".equalsIgnoreCase(CONN_TYPE))
                {
                    return getConnectionByDBCP();
                }
                else if ("jndi".equalsIgnoreCase(CONN_TYPE))
                {
                    return getConnectionByJNDI();
                }
                else
                {
                    return getConnectionByJDBC();
                }
            }
            else
            {
                return dataSource.getConnection();
            }

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * 通过JNDI获取数据连接
     * @return java.sql.Connection
     * @throws NamingException
     * @throws SQLException
     */
    private static Connection getConnectionByJNDI() throws NamingException, SQLException
    {
        if (StringUtil.IsNullOrEmpty(JNDI_NAME))
        {
            throw new NullPointerException("the db.properties is not set value of jndiName!");
        }

        Context context = new InitialContext();

        dataSource = (DataSource)context.lookup("java:comp/env/" + JNDI_NAME);
        return dataSource.getConnection();
    }

    /**
     * 通过JDBC获取数据连接
     * @return java.sql.Connection
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static Connection getConnectionByJDBC() throws SQLException, ClassNotFoundException
    {

        Class.forName(DRIVER_URL);
        return DriverManager.getConnection(CONN_URL, CONN_NAME, CONN_PWD);
    }

    /**
     * 通过DBCP获取数据连接
     * @return java.sql.Connection
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    private static Connection getConnectionByDBCP() throws Exception
    {
        Properties dbProperties = DbConfigUtil.getInstance();
        Object basicDSFactory = Class.forName("org.apache.commons.dbcp.BasicDataSourceFactory").newInstance();
        Object ds =
            basicDSFactory.getClass().getMethod("createDataSource", Properties.class).invoke(basicDSFactory,
                dbProperties);
        dataSource = (DataSource)ds;
        return dataSource.getConnection();

    }

    /**
     * 关闭数据库连接
     * @param conn 数据库连接对象
     */
    public static void closeConnection(Connection conn)
    {
        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
                conn = null;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    /**
     * 关闭ResultSet
     * @param res
     */
    public static void closeResultSet(ResultSet res)
    {
        try
        {
            if (res != null)
            {
                res.close();
                res = null;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭数据库操作对象
     * @param pstmt
     */
    public static void closeStatement(PreparedStatement pstmt)
    {
        try
        {
            if (pstmt != null)
            {
                pstmt.close();
                pstmt = null;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有对象
     * @param res
     * @param pstmt
     * @param conn
     */
    public static void close(ResultSet res, PreparedStatement pstmt, Connection conn)
    {
        try
        {
            if (res != null)
            {
                res.close();
                res = null;
            }

            if (pstmt != null)
            {
                pstmt.close();
                pstmt = null;
            }

            if (conn != null && !conn.isClosed())
            {
                conn.close();
                conn = null;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭数据库操作和连接对象
     * @param res
     * @param pstmt
     * @param conn
     */
    public static void close(PreparedStatement pstmt, Connection conn)
    {
        try
        {

            if (pstmt != null)
            {
                pstmt.close();
                pstmt = null;
            }

            if (conn != null && !conn.isClosed())
            {
                conn.close();
                conn = null;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

}
