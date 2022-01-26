package org.leolo.web.dm.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.leolo.web.dm.Constant;
import org.leolo.web.dm.dao.APIKeyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(BaseServlet.class);
    
	protected String userName = null;
	protected int userId = Constant.COM_DEFAULT_USER_ID;
	protected boolean fatalError = false;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Request - [GET] {}", request.getRequestURI());
		//Reset parameters
		userName = null;
		userId = Constant.COM_DEFAULT_USER_ID;
		fatalError = false;
		
		if(request.getAttribute(Constant.SESSION_USER_NAME)!=null) {
			userName = request.getSession().getAttribute(Constant.SESSION_USER_NAME).toString();
			userId = (Integer) request.getSession().getAttribute(Constant.SESSION_USER_ID);
		}
		if(request.getParameter("key") != null && !"".equals(request.getParameter("key").trim())) {
			APIKeyDao apiDao = new APIKeyDao();
			String key = request.getParameter("key");
			log.info("Using API key {}", key);
			Map<String, Object> buim = apiDao.getBasicUserInfomationBuApiKey(key);
			if(buim!=null) {
				userName = buim.get(Constant.BUI_KEY_USER_NAME).toString();
				userId = (Integer) buim.get(Constant.BUI_KEY_USER_ID);
				apiDao.markKeyUsed(key);
			}else {
				response.setContentType("application/json");
				JSONObject obj = new JSONObject();
				response.setStatus(403);
				obj.put("status", "error");
				obj.put("message", "Invalid API key");
				obj.write(response.getWriter());
				fatalError = true;
				return;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
