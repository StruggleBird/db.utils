
package entity;

import com.zt.utils.db.annotation.Column;
import com.zt.utils.db.annotation.Entity;
import com.zt.utils.db.annotation.Id;

@Entity("tbl_user")
public class User
{
    @Id
    @Column("id")
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer age;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}
