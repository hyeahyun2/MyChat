package chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ä�� ������ 
@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �⺻ ����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// ����ڿ��� �Է¹��� �� ������ ����
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
		System.out.println("ê���� : " + chatContent);
		// ���� or ����ִ� ���
		if(fromID == null || fromID.equals("") 
				|| toID == null || toID.equals("")
				|| chatContent == null || chatContent.equals("")) {
			// "0" ���� ��ȯ
			response.getWriter().write("0");
		}
		else { // �ƴ� ���
			// ���ڵ��ϱ� (�ѱ۷� �ۼ����� ��� ���)
			System.out.println("ä�� �޾ƿ�");
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			// ChatDAO�� submit �޼ҵ� ȣ���ؼ� �� �޾ƿ���
			// ��ȯ�� : sql�� ���� ��� ���� ���� �� (=1) / �����ͺ��̽� ���� = -1
			response.getWriter().write(new ChatDAO().submit(fromID, toID, chatContent) + "");
		}
	}

}
