package org.leolo.web.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.leolo.web.dm.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIKeyDao extends BaseDao {
	
	private static Logger log = LoggerFactory.getLogger(APIKeyDao.class);
	private static ExecutorService dbPool = Executors.newFixedThreadPool(1);
	
	public String getUsernameByApiKey(String key) {
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT user_name FROM api_key a JOIN user u ON a.user=u.user_id WHERE api_key = ?")
		){
			pstmt.setString(1, key);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return rs.getString(1);
				}
			}
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public Map<String, Object> getBasicUserInfomationBuApiKey(String key){
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT user_id, user_name FROM api_key a JOIN user u ON a.user=u.user_id WHERE api_key = ?")
		){
			pstmt.setString(1, key);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Map<String, Object> map = new Hashtable<>();
					map.put(Constant.BUI_KEY_USER_ID, rs.getInt(1));
					map.put(Constant.BUI_KEY_USER_NAME, rs.getString(2));
					return map;
				}
			}
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		
		return null;
	}

	public void markKeyUsed(String key) {
		final long LAST_USE_TIME = System.currentTimeMillis();
		dbPool.execute(new Runnable() {
			@Override
			public void run() {
				try(
						Connection conn = getConnection();
						PreparedStatement pstmt = conn.prepareStatement("UPDATE api_key SET last_key_use = ? WHERE api_key = ?");
				){
					pstmt.setTimestamp(1, new Timestamp(LAST_USE_TIME));
					pstmt.setString(2, key);
					pstmt.executeUpdate();
				}catch(SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		});
	}
}
