<!DOCTYPE html><%@ 
page import="java.util.Collection,org.leolo.web.dm.dao.*,org.leolo.web.dm.model.*,org.leolo.web.dm.util.*"
%><html>
	<head>
		<title>Download Manager</title>
	</head>
	<body>
		<h1>Download Manager</h1>
		<% Collection<Project> projs = new ProjectDao().getAllProjects(); %>
		<h2>Project List</h2>
		<ul><% for(Project proj:projs){ %>
		<li>
			<a href="project/<%=proj.getProjectPath()%>"><%= proj.getProjectName() %></a><br>
			<%= JSPUtils.nl2br(proj.getDescription()) %>
		</li>
		<%} %></ul>
	</body>
</html>