package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// ä�� ������ 
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("chatListServlet ����");
		// �⺻ ����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// ����ڿ��� �Է¹��� �� ������ ����
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		// ���� or ����ִ� ���
		if(fromID == null || fromID.equals("") 
				|| toID == null || toID.equals("")
				|| listType == null || listType.equals("")) {
			// ���� ��ȯ
			response.getWriter().write("");
		}
		else if(listType.equals("ten")) { // �� ó�� �ε� ��
			response.getWriter().write(getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
		}
		else { // ���ο� ä�� ��� �ҷ����� (���� ���� ���)
			try { // Ư���� ä�� ���̵��� �������� ��ȭ ������ ��������
				response.getWriter().write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
			} catch(Exception e) { // ����ó�� -> ���� �߻��� ���� ���ڿ� ��ȯ
				e.printStackTrace();
				response.getWriter().write("");
			}
		}
	}
	
	// getTen �޼ҵ� (���� ó�� �ε���� �� �ֱ� ä�� ���� 10�� ������)
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		// json���� ������ֱ�
		result.append("{\"result\":["); 
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromID, toID, 10);
		if(chatList.size() == 0) return "";
		for(int i=0; i<chatList.size(); i++) {
//			System.out.println("getFromID : " + chatList.get(i).getFromID());
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() - 1) result.append(",");
		}
		// json���� �� , ���� ������ chatID ���
		result.append("], \"last\":\"" + chatList.get(chatList.size()-1).getChatID() + "\"}");
		return result.toString(); // json���� ���ڿ� ��ȯ
	}
	
	// getID �޼ҵ�
	public String getID(String fromID, String toID, String chatID) {
		// StringBuffer �ν��Ͻ� ���� (""(���鹮��)�� �����ϵ���)
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":["); // json ���� !!!
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(fromID, toID, chatID);
		if(chatList.size() == 0) return ""; // chatList�� ��� �ִ� ��� -> ���鹮�� ��ȯ
		for(int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");//
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() -1) result.append(",");
		}
		// json���� �� , ���� ������ chatID ���
		result.append("], \"last\":\"" + chatList.get(chatList.size()-1).getChatID() + "\"}");
		return result.toString(); // => json������ ���ڿ� ��ȯ
	}
}
