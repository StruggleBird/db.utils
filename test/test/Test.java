/**
 * 
 */

package test;

import com.zt.utils.db.DbUtil;
import com.zt.utils.db.DbUtilProxy;

import entity.User;

/**
 * @author zhoutao
 *
 */
public class Test
{
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        DbUtil dbUtil = DbUtilProxy.create();
        
        System.out.println(dbUtil.getList(User.class).size());


    }

}
