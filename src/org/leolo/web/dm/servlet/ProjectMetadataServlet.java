package org.leolo.web.dm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.leolo.web.dm.dao.ProjectDao;
import org.leolo.web.dm.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet({"/metadata/*"})
public class ProjectMetadataServlet extends BaseServlet {
	private static Logger log = LoggerFactory.getLogger(ProjectMetadataServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectMetadataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		response.setContentType("application/json");
		log.info("URL: {}", request.getRequestURI());
		log.info("User is {}", userName);
		String projectPath = request.getRequestURI().substring(request.getContextPath().length()+10).trim();
		log.info("Project Requested: {}", projectPath);
		JSONObject retObj = new JSONObject();
		if(projectPath.length()==0) {
			//list all project
			retObj.put("status", "success");
			//TODO: list all project
			retObj.write(response.getWriter());
			return;
		}
		ProjectDao projDao = new ProjectDao();
		Project proj = projDao.getProjectByPath(projectPath);
		if(proj==null || projDao.canReadProject(proj.getProjectId(), userId)) {
			retObj.put("status", "error");
			retObj.put("message", "Specified project cannot be found");
			retObj.put("requested", projectPath);
			Collection<String> names = projDao.getProjectPathsIgnoreCase(projectPath);
			for(String name:names) {
				retObj.append("suggestedPaths", name);
			}
		}else {
			retObj.put("status", "success");
			retObj.put("name", proj.getProjectName());
			retObj.put("id", proj.getProjectId());
			//TODO: Handle other case
		}
		retObj.write(response.getWriter());
	}

}
