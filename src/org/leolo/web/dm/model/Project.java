package org.leolo.web.dm.model;

public class Project {
	private int projectId;
	private String projectPath;
	private String projectName;
	private ProjectMode mode;
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
}
