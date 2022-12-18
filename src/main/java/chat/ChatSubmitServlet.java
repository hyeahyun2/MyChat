package chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 채팅 보내기 
@WebServlet("/ChatSubmitServlet")
public class ChatSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 기본 세팅
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		// 사용자에게 입력받은 값 변수에 저장
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
		System.out.println("챗내용 : " + chatContent);
		// 공백 or 비어있는 경우
		if(fromID == null || fromID.equals("") 
				|| toID == null || toID.equals("")
				|| chatContent == null || chatContent.equals("")) {
			// "0" 문자 반환
			response.getWriter().write("0");
		}
		else { // 아닌 경우
			// 디코딩하기 (한글로 작성했을 경우 대비)
			System.out.println("채팅 받아옴");
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			// ChatDAO의 submit 메소드 호출해서 값 받아오기
			// 반환값 : sql문 실행 결과 영향 받은 행 (=1) / 데이터베이스 오류 = -1
			response.getWriter().write(new ChatDAO().submit(fromID, toID, chatContent) + "");
		}
	}

}
