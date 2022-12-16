package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		// 아이디, 비번 비어있는 경우
		if(userID == null || userID.equals("") 
				|| userPassword == null || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력해주세요.");
			response.sendRedirect("login.jsp");
		}
		// 비어있지 않은 경우 -> 로그인 시도
		// return값  : 1 = 로그인 성공, 0 = 로그인 정보 일치x , -1 = 데이터베이스 오류
		int result = new UserDAO().login(userID, userPassword);
		if(result == 1) { // 로그인 성공
			request.getSession().setAttribute("messageType", "성공 메세지");
			request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
			response.sendRedirect("index.jsp");
		}
		else if(result == 0) { // 아이디 or 비번 틀림
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "로그인 정보가 정확하지 않습니다.");
			response.sendRedirect("login.jsp");
		}
		else if(result == -1) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			response.sendRedirect("login.jsp");
		}
	}
}
