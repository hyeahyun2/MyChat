package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 채팅 보내기 
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("chatListServlet 실행");
		// 기본 세팅
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// 사용자에게 입력받은 값 변수에 저장
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		// 공백 or 비어있는 경우
		if(fromID == null || fromID.equals("") 
				|| toID == null || toID.equals("")
				|| listType == null || listType.equals("")) {
			// 공백 반환
			response.getWriter().write("");
		}
		else if(listType.equals("ten")) { // 맨 처음 로딩 시
			response.getWriter().write(getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
		}
		else { // 새로운 채팅 목록 불러오기 (값이 있을 경우)
			try { // 특정한 채팅 아이디값을 기준으로 대화 정보를 가져오기
				response.getWriter().write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
			} catch(Exception e) { // 예외처리 -> 오류 발생시 공백 문자열 반환
				e.printStackTrace();
				response.getWriter().write("");
			}
		}
	}
	
	// getTen 메소드 (문서 처음 로드됐을 때 최근 채팅 내역 10개 들고오기)
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		// json형태 만들어주기
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
		// json문장 끝 , 가장 마지막 chatID 담기
		result.append("], \"last\":\"" + chatList.get(chatList.size()-1).getChatID() + "\"}");
		return result.toString(); // json형태 문자열 반환
	}
	
	// getID 메소드
	public String getID(String fromID, String toID, String chatID) {
		// StringBuffer 인스턴스 생성 (""(공백문자)로 시작하도록)
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":["); // json 형태 !!!
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(fromID, toID, chatID);
		if(chatList.size() == 0) return ""; // chatList가 비어 있는 경우 -> 공백문자 반환
		for(int i=0; i<chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");//
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			if(i != chatList.size() -1) result.append(",");
		}
		// json문장 끝 , 가장 마지막 chatID 담기
		result.append("], \"last\":\"" + chatList.get(chatList.size()-1).getChatID() + "\"}");
		return result.toString(); // => json형태의 문자열 반환
	}
}
