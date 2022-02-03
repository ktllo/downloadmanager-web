package org.leolo.web.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

import org.leolo.web.dm.Constant;
import org.leolo.web.dm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao extends BaseDao {

	private static Logger log = LoggerFactory.getLogger(UserDao.class);

	public Map<String, Object> getBasicUserInfoByUsernameWithPassword(String username) {
		Map<String, Object> u = new Hashtable<>();
		try (Connection conn = getConnection()) {
			// Step 1: get basic user info
			try (PreparedStatement pstmt = conn
					.prepareStatement("SELECT user_id, user_name, password, pwd_err_cnt, last_login FROM user WHERE user_name = ?")) {
				pstmt.setString(1, username);
				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						u.put(Constant.BUI_KEY_USER_NAME, rs.getString(2));
						u.put(Constant.BUI_KEY_USER_ID, rs.getInt(1));
						u.put(Constant.BUI_KEY_PASSWORD, rs.getString(3));
						u.put(Constant.BUI_KEY_FAIL_CNT, rs.getInt(4));
						if(rs.getTimestamp(5)!=null) {
							u.put(Constant.BUI_KEY_LAST_LOGIN, rs.getTimestamp(5));
						}
					} else {
						return null;
					}
				}
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		return u;
	}
	
	public void updateLastLogin(int userId) {
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE user SET pwd_err_cnt = 0, last_login=NOW()  WHERE user_id = ?")
		){
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	public void updateLoginFailedCount(int userId) {
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE user SET pwd_err_cnt = nvl(pwd_err_cnt,0) + 1 WHERE user_id = ?")
		){
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void lockUser(int userId) {
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("UPDATE user SET password = concat('!!',password)  WHERE user_id = ? AND password NOT LIKE '!!%'")
		){
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		
	}
}
