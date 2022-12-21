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
		// 기본 세팅
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// 입력받은 값 변수에 저장
		String userID = (String)request.getSession().getAttribute("userID");
		// 공백 or 비어있는 경우
		if(userID == null || userID.equals("")) {
			response.getWriter().write(""); // 공백 반환
		}
		else { // 채팅 내역 있는 아이디 불러오기
			try {
				response.getWriter().write(getChatIDList(userID));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 채팅 내역 있는 아이디 목록 json형태로 얻기
	public String getChatIDList(String userID) {
		StringBuffer result = new StringBuffer("");
		// json형태로
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<String> chatIDList = chatDAO.getChatIDList(userID);
		if(chatIDList.size() == 0) return "";
		for(int i=0; i<chatIDList.size(); i++) {
			if(chatIDList.get(i).equals(userID)) continue; // 로그인한 아이디인 경우 제외
			result.append("{\"chatUserID\": \"" + chatIDList.get(i) + "\"}");
			if(i != chatIDList.size() - 1) result.append(",");
		}
		// json 문장 끝
		result.append("]}");
		System.out.println(result.toString());
		return result.toString();
	}

}
