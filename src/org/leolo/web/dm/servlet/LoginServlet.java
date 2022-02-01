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
import org.leolo.web.dm.dao.SystemParameterDao;
import org.leolo.web.dm.dao.UserDao;
import org.leolo.web.dm.util.QueuedJobs;
import org.leolo.web.dm.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static Logger log = LoggerFactory.getLogger(LoginServlet.class);
	private static Marker mark = MarkerFactory.getMarker("user");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDao udao = new UserDao();
		SystemParameterDao spdao = new SystemParameterDao();
		log.info("Login attempted! {}@{}:{}", request.getParameter("username"), request.getRemoteHost(), request.getParameter("password"));
		Map<String, Object> bui = udao.getBasicUserInfoByUsernameWithPassword(request.getParameter("username"));
		if(bui!=null) {
			if(BCrypt.verifyer().verify(request.getParameter("password").toCharArray(), bui.get(Constant.BUI_KEY_PASSWORD).toString().toCharArray()).verified) {
				log.info(mark, "User {}@{} logged in successfully!", bui.get(Constant.BUI_KEY_USER_NAME), ServletUtil.getClientIpAddr(request));
				request.getSession().setAttribute(Constant.SESSION_USER_ID, (int)bui.get(Constant.BUI_KEY_USER_ID));
				request.getSession().setAttribute(Constant.SESSION_USER_NAME, bui.get(Constant.BUI_KEY_USER_NAME));
				sendSuccessResponse(response);
				QueuedJobs.getInstance().queue(()->{
					udao.updateLastLogin((int)bui.get(Constant.BUI_KEY_USER_ID));
				});
			}else {
				log.info(mark, "User {}@{} attempted to login![ICP/UL]", bui.get(Constant.BUI_KEY_USER_NAME), ServletUtil.getClientIpAddr(request));
				sendFailedResponse(response);
				QueuedJobs.getInstance().queue(()->{
					udao.updateLoginFailedCount((int)bui.get(Constant.BUI_KEY_USER_ID));
					int failedCount = (int) bui.get(Constant.BUI_KEY_FAIL_CNT);
					failedCount++;
					if(failedCount >= spdao.getInt(Constant.SP_MAX_LOGIN_FAIL_CNT, 5)) {
						//lock account
						udao.lockUser((int)bui.get(Constant.BUI_KEY_USER_ID));
					}
				});
			}
		}else {
			//User does not exist!
			log.info(mark, "User {}@{} attempted to login![UNE]", request.getParameter("username"), ServletUtil.getClientIpAddr(request));
			BCrypt.withDefaults().hashToString(spdao.getInt(Constant.SP_BCRYPT_COST, 12), request.getParameter("password").toCharArray());
			sendFailedResponse(response);
		}
	}
	
	private void sendFailedResponse(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		JSONObject obj = new JSONObject();
		obj.put("status", "failed");
		obj.put("message", "Username and/or password incorrect, or account is being locked.");
		obj.write(response.getWriter());
	}
	
	private void sendSuccessResponse(HttpServletResponse response) throws IOException{
		response.setContentType("application/json");
		JSONObject obj = new JSONObject();
		obj.put("status", "success");
		obj.write(response.getWriter());
	}

}
