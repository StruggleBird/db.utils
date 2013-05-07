
package db;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zt.utils.db.DbUtil;
import com.zt.utils.db.DbUtilFactory;
import com.zt.utils.db.DbUtilProxy;

import entity.User;

public class DbUtilProxyTest
{
    private DbUtil dbUtilProxy;

    @Before
    public void setUp() throws Exception
    {
        if (dbUtilProxy == null)
        {
            DbUtil dbUtil = DbUtilFactory.createDbUtilInstance();
            dbUtilProxy = DbUtilProxy.getInstance().proxy(dbUtil);
        }
    }

    @Test
    public void dbUtilTest()
    {
        User user = new User();
        user.setAge(16);
        user.setName("zt");
        dbUtilProxy.add(user);
        boolean exist = dbUtilProxy.exist(user);
        user = dbUtilProxy.get(user);
        System.out.println(user.getId());
        Assert.assertTrue(exist);
    }

}
