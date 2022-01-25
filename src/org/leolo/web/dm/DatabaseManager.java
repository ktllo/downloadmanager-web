package org.leolo.web.dm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseManager {
	
	private static DataSource ds;
	
	static {
		InitialContext context;
		try {
			context = new InitialContext();
	        ds = (DataSource) context.lookup("java:/dm");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private DatabaseManager() {
		
	}
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
}
