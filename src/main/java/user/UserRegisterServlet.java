package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ȸ������ ��� ����
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		// ����ڿ��� �Է¹��� �� ������ ����
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String userProfile = request.getParameter("userProfile");
		
		// ���̵� �ߺ� �˻�
		int result = new UserDAO().register(userID, userPassword, userName, userProfile);
		if(result == 1) { // ���� ����
			request.getSession().setAttribute("messageType", "���� �޼���");
			request.getSession().setAttribute("messageContent", "ȸ�����Կ� �����߽��ϴ�.");
			response.sendRedirect("index.jsp"); // index�������� �̵�
		}
		else { // �ߺ� ���̵� �ִ� ���
			request.getSession().setAttribute("messageType", "���� �޼���");
			request.getSession().setAttribute("messageContent", "�̹� �����ϴ� ȸ���Դϴ�.");
			response.sendRedirect("join.jsp"); // �ش� �������� ���� �̵�
			return;
		}
	}
}
