package org.leolo.web.dm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.leolo.web.dm.Constant;
import org.leolo.web.dm.dao.SystemParameterDao;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet("/UserInfo")
public class UserInfoServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see BaseServlet#BaseServlet()
     */
    public UserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		if(this.fatalError) {
			return;
		}
		response.setContentType("application/json");
		JSONObject obj = new JSONObject();
		obj.put("status", "success");
		obj.put("identified", this.userName!=null);
		if(this.userName!=null) {
			obj.put("username", this.userName);
			obj.put("method", identifiedByKey?"api-key":"session");
		}
		obj.put("system_name", new SystemParameterDao().getString(Constant.SP_SYSNAME));
		obj.put("api-version", Constant.SI_API_VERSION);
		obj.write(response.getWriter());
	}

}
