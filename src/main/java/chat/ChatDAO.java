package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ChatDAO {
	DataSource dataSource;
	
	public ChatDAO() { // 생성자
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			// lookup(Resource의name값)
			dataSource = (DataSource)envContext.lookup("jdbc/MyChat");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 특정 아이디에 따라 채팅 내역 가져오기
	public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID){
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from chat where "
				+ "((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) "
				+ "AND chatID > ? order by chatTime";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, Integer.parseInt(chatID));
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				// 특수문자는 html에 사용되는 키워드로 바꿔주기
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				// chatTime 가져와서 오전 오후 나누기 => chatTime에 담기
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType ="오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + " : " + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	// 대화내역 최근 10개 가져오기 (채팅창 열었을 때 1번 실행)
	public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number){
		ArrayList<ChatDTO> chatList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from chat where "
				+ "((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) "
				+ "AND chatID > (select Max(chatID) - ? from chat) order by chatTime";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, toID);
			pstmt.setString(4, fromID);
			pstmt.setInt(5, number);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<ChatDTO>();
			while(rs.next()) {
				ChatDTO chat = new ChatDTO();
				chat.setChatID(rs.getInt("chatID"));
				// 특수문자는 html에 사용되는 키워드로 바꿔주기
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				// chatTime 가져와서 오전 오후 나누기 => chatTime에 담기
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType ="오후";
					chatTime -= 12;
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + " : " + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chatList;
	}
	
	// 다른 사람에게 채팅 보내기
	public int submit(String fromID, String toID, String chatContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int upd = 0;
		String sql = "insert into chat(fromID, toID, chatContent, chatTime) values(?, ?, ?, NOW())";
		try {
			conn = dataSource.getConnection(); // db 연결
			pstmt = conn.prepareStatement(sql); // sql문 사용
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			upd = pstmt.executeUpdate();  // sql문 실행 결과(영향받은 행 개수) 반환
			System.out.println(upd);
			return upd;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // 데이터베이스 오류 발생 시 반환할 값
	}
	
	// 로그인한 아이디와의 채팅 내역이 있는 아이디 목록 들고오기
	public ArrayList<String> getChatIDList(String userID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 리턴할 리스트 생성
		ArrayList<String> idList = new ArrayList<String>();
		
		// userID에 따른 채팅 내역 있는 아이디 목록 불러오기
		String sql = "select toID, fromID from chat "
				+ "GROUP BY fromID, toID " 
				+ "HAVING toID = ? OR fromID = ?";
		
		try{
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			while(rs.next()){ // 값 있으면
				// 중복 값 걸러내기
				if(!idList.contains(rs.getString("toID"))) { // 포함하지 않으면
					idList.add(rs.getString("toID")); // 리스트에 추가
				}
				if(!idList.contains(rs.getString("fromID"))) {
					idList.add(rs.getString("fromID"));
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return idList;
	}
}
