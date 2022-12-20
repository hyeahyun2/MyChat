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
	
	public ChatDAO() { // ������
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			// lookup(Resource��name��)
			dataSource = (DataSource)envContext.lookup("jdbc/MyChat");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Ư�� ���̵� ���� ä�� ���� ��������
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
				// Ư�����ڴ� html�� ���Ǵ� Ű����� �ٲ��ֱ�
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				// chatTime �����ͼ� ���� ���� ������ => chatTime�� ���
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "����";
				if(chatTime > 12) {
					timeType ="����";
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
	
	// ��ȭ���� �ֱ� 10�� �������� (ä��â ������ �� 1�� ����)
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
				// Ư�����ڴ� html�� ���Ǵ� Ű����� �ٲ��ֱ�
				chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll("\n", "<br>"));
				chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				// chatTime �����ͼ� ���� ���� ������ => chatTime�� ���
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
				String timeType = "����";
				if(chatTime > 12) {
					timeType ="����";
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
	
	// �ٸ� ������� ä�� ������
	public int submit(String fromID, String toID, String chatContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int upd = 0;
		String sql = "insert into chat(fromID, toID, chatContent, chatTime) values(?, ?, ?, NOW())";
		try {
			conn = dataSource.getConnection(); // db ����
			pstmt = conn.prepareStatement(sql); // sql�� ���
			pstmt.setString(1, fromID);
			pstmt.setString(2, toID);
			pstmt.setString(3, chatContent);
			upd = pstmt.executeUpdate();  // sql�� ���� ���(������� �� ����) ��ȯ
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
		return -1; // �����ͺ��̽� ���� �߻� �� ��ȯ�� ��
	}
	
	// �α����� ���̵���� ä�� ������ �ִ� ���̵� ��� ������
	public ArrayList<String> getChatIDList(String userID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// ������ ����Ʈ ����
		ArrayList<String> idList = new ArrayList<String>();
		
		// userID�� ���� ä�� ���� �ִ� ���̵� ��� �ҷ�����
		String sql = "select toID, fromID from chat "
				+ "GROUP BY fromID, toID " 
				+ "HAVING toID = ? OR fromID = ?";
		
		try{
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			while(rs.next()){ // �� ������
				// �ߺ� �� �ɷ�����
				if(!idList.contains(rs.getString("toID"))) { // �������� ������
					idList.add(rs.getString("toID")); // ����Ʈ�� �߰�
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
