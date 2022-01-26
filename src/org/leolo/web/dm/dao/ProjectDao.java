package org.leolo.web.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import org.leolo.web.dm.Constant;
import org.leolo.web.dm.model.Project;
import org.leolo.web.dm.model.ProjectMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class ProjectDao extends BaseDao{
	
	private static Logger log = LoggerFactory.getLogger(ProjectDao.class);
	private static Marker mark = MarkerFactory.getMarker("org.leolo.web.dm");
	
	public Collection<Project> getAllProjects(){
		Vector<Project> v = new Vector<>();
		try(
				Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT project_id, project_path, project_name, mode FROM project")
		){
			while(rs.next()) {
				Project proj = new Project();
				proj.setProjectId(rs.getInt(1));
				proj.setProjectPath(rs.getString(2));
				proj.setProjectName(rs.getString(3));
				if("master".equalsIgnoreCase(rs.getString(4))) {
					proj.setMode(ProjectMode.MASTER);
				}else {
					proj.setMode(ProjectMode.SLAVE);
				}
				v.add(proj);
			}
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		return v;
	}
	
	public Project getProjectByPath(String path) {
		try(
				Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT project_id, project_path, project_name, mode FROM project WHERE project_path = ?");
		){
			stmt.setString(1, path);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					Project proj = new Project();
					proj.setProjectId(rs.getInt(1));
					proj.setProjectPath(rs.getString(2));
					proj.setProjectName(rs.getString(3));
					if("master".equalsIgnoreCase(rs.getString(4))) {
						proj.setMode(ProjectMode.MASTER);
					}else {
						proj.setMode(ProjectMode.SLAVE);
					}
					return proj;
				}
			}
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}	
	
	@Deprecated
	public Collection<String> getProjectPathsIgnoreCase(String path){
		return getProjectPathsIgnoreCase(path, Constant.COM_DEFAULT_USER_ID);
	}
	public Collection<String> getProjectPathsIgnoreCase(String path, int userId) {
		Vector<String> names = new Vector<>();
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT project_path FROM project WHERE UPPER(project_path) = ?")
		){
			 pstmt.setString(1, path.toUpperCase());
			 try(ResultSet rs = pstmt.executeQuery()){
				 while(rs.next()) {
					 names.add(rs.getString(1));
				 }
			 }
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		return names;
	}
	
	public Project getProjectById(int projectId) {
		try(
				Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT project_id, project_path, project_name, mode FROM project WHERE project_id = ?");
		){
			stmt.setInt(1, projectId);
			try(ResultSet rs = stmt.executeQuery()){
				while(rs.next()) {
					Project proj = new Project();
					proj.setProjectId(rs.getInt(1));
					proj.setProjectPath(rs.getString(2));
					proj.setProjectName(rs.getString(3));
					if("master".equalsIgnoreCase(rs.getString(4))) {
						proj.setMode(ProjectMode.MASTER);
					}else {
						proj.setMode(ProjectMode.SLAVE);
					}
					return proj;
				}
			}
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public boolean canReadProject(int projectId, int userId) {
		try(
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT read FROM project_user WHERE project_id = ? AND user_id = ?")
		){
			pstmt.setInt(1, projectId);
			pstmt.setInt(2, userId);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return 1 == rs.getInt(1);
				}
			}
			pstmt.setInt(2, Constant.COM_DEFAULT_USER_ID);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return 1 == rs.getInt(1);
				}
			}
		}catch(SQLException e) {
			log.error(e.getMessage(), e);
		}
		return false;//default
	}
}
