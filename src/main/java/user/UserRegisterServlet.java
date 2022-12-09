package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 회원가입 담당 서블릿
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		// 사용자에게 입력받은 값 변수에 저장
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userPWCheck = request.getParameter("userPWCheck");
		String userName = request.getParameter("userName");
		String userProfile = request.getParameter("userProfile");
		// 해당 변수 중 하나라도 null이거나 공백인 경우
		if(userID == null || userID.equals("") 
				|| userPassword == null || userPassword.equals("")
				|| userPWCheck == null || userPWCheck.equals("") 
				|| userName == null || userName.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
			response.sendRedirect("join.jsp"); // 해당 페이지로 강제 이동
			return;
		}
		// 비밀번호와 비밀번호 확인이 일치하지 않는 경우
		if(!userPassword.equals(userPWCheck)) {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호가 서로 다릅니다.");
			response.sendRedirect("join.jsp"); // 해당 페이지로 강제 이동
			return;
		}
		// 오류 없는 경우
		// 아이디 중복 검사
	}
}
