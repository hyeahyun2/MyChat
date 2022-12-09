package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ȸ������ ��� ������
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		// ����ڿ��� �Է¹��� �� ������ ����
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userPWCheck = request.getParameter("userPWCheck");
		String userName = request.getParameter("userName");
		String userProfile = request.getParameter("userProfile");
		// �ش� ���� �� �ϳ��� null�̰ų� ������ ���
		if(userID == null || userID.equals("") 
				|| userPassword == null || userPassword.equals("")
				|| userPWCheck == null || userPWCheck.equals("") 
				|| userName == null || userName.equals("")) {
			request.getSession().setAttribute("messageType", "���� �޼���");
			request.getSession().setAttribute("messageContent", "��� ������ �Է��ϼ���.");
			response.sendRedirect("join.jsp"); // �ش� �������� ���� �̵�
			return;
		}
		// ��й�ȣ�� ��й�ȣ Ȯ���� ��ġ���� �ʴ� ���
		if(!userPassword.equals(userPWCheck)) {
			request.getSession().setAttribute("messageType", "���� �޼���");
			request.getSession().setAttribute("messageContent", "��й�ȣ�� ���� �ٸ��ϴ�.");
			response.sendRedirect("join.jsp"); // �ش� �������� ���� �̵�
			return;
		}
		// ���� ���� ���
		// ���̵� �ߺ� �˻�
	}
}