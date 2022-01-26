package org.leolo.web.dm.model;

import org.json.JSONObject;

public class Project {
	private int projectId;
	private String projectPath;
	private String projectName;
	private ProjectMode mode;
	private String description;
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public ProjectMode getMode() {
		return mode;
	}
	public void setMode(ProjectMode mode) {
		this.mode = mode;
	}
	
	public JSONObject getShortJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("id", projectId);
		obj.put("name", projectName);
		obj.put("path", projectPath);
		obj.put("description", description);
		return obj;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
