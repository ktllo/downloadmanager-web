package org.leolo.web.dm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemParameterDao extends BaseDao {
	private static Logger log = LoggerFactory.getLogger(SystemParameterDao.class);
	
	private static final Object SYNC_TOKEN = new Object();
	private static Map<String, String> cache = new HashMap<>();
	static {
		new SystemParameterDao().reloadCache();
	}
	public void reloadCache() {
		synchronized(SYNC_TOKEN) {
			try(
					Connection conn = getConnection();
					Statement stmt = conn.createStatement(); 
					ResultSet rs = stmt.executeQuery("SELECT parameter_id, parameter_value FROM system_parameter")
			){
				Map<String, String> cache = new HashMap<>();
				while(rs.next()) {
					cache.put(rs.getString(1), rs.getString(2));
				}
				SystemParameterDao.cache = cache;
			}catch(SQLException e) {
				log.error(e.getMessage(), e);
			}
			SYNC_TOKEN.notifyAll();
		}
	}

	public String getParameterValue(String parameterName) {
		return cache.get(parameterName);
	}
	
}
