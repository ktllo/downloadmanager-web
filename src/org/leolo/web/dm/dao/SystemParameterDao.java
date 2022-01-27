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

	public String getString(String parameterName) {
		return getString(parameterName, null);
	}
	
	public String getString(String paramenterName, String defaultValue) {
		if(cache.containsKey(paramenterName)) {
			return cache.get(paramenterName);
		}
		return defaultValue;
	}

	public int getInt(String parameterName) {
		return getInt(parameterName, 0);
	}
	
	public int getInt(String paramenterName, int defaultValue) {
		if(cache.containsKey(paramenterName)) {
			return Integer.parseInt(cache.get(paramenterName));
		}
		return defaultValue;
	}

	public long getLong(String parameterName) {
		return getInt(parameterName, 0);
	}
	
	public long getLong(String paramenterName, long defaultValue) {
		if(cache.containsKey(paramenterName)) {
			return Long.parseLong(cache.get(paramenterName));
		}
		return defaultValue;
	}

	public boolean getBoolean(String parameterName) {
		return getBoolean(parameterName, false);
	}
	
	public boolean getBoolean(String paramenterName, boolean defaultValue) {
		if(cache.containsKey(paramenterName)) {
			return Boolean.parseBoolean(cache.get(paramenterName));
		}
		return defaultValue;
	}
	
}
