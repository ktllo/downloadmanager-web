package org.leolo.web.dm.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.leolo.web.dm.DatabaseManager;

public abstract class BaseDao {
	
	protected Connection getConnection() throws SQLException {
		return DatabaseManager.getConnection();
	}
	
}
