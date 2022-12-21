package chat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChatIDListServlet")
public class ChatIDListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �⺻ ����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// �Է¹��� �� ������ ����
		String userID = (String)request.getSession().getAttribute("userID");
		// ���� or ����ִ� ���
		if(userID == null || userID.equals("")) {
			response.getWriter().write(""); // ���� ��ȯ
		}
		else { // ä�� ���� �ִ� ���̵� �ҷ�����
			try {
				response.getWriter().write(getChatIDList(userID));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// ä�� ���� �ִ� ���̵� ��� json���·� ���
	public String getChatIDList(String userID) {
		StringBuffer result = new StringBuffer("");
		// json���·�
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<String> chatIDList = chatDAO.getChatIDList(userID);
		if(chatIDList.size() == 0) return "";
		for(int i=0; i<chatIDList.size(); i++) {
			if(chatIDList.get(i).equals(userID)) continue; // �α����� ���̵��� ��� ����
			result.append("{\"chatUserID\": \"" + chatIDList.get(i) + "\"}");
			if(i != chatIDList.size() - 1) result.append(",");
		}
		// json ���� ��
		result.append("]}");
		System.out.println(result.toString());
		return result.toString();
	}

}
