package com.zt.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.zt.db.ConnectionManager;
import com.zt.db.DbUtil;
import com.zt.db.DbUtilFactory;
import com.zt.db.impl.MySqlDbImpl;

public class Test1 {
	
	public static void main(String[] args) {
		File newFile = new File("e:\\db\\aa.rar");
		try {
			System.out.println(newFile.createNewFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

