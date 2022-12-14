package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// DAO : Data Access Object (���ٰ�ü)
public class UserDAO {
	// Ŀ�ؼ�Ǯ
	DataSource dataSource;

	public UserDAO() { // ������
		// ��ü �������ڸ��� �����ͺ��̽��� ����
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			// lookup(Resource��name��)
			dataSource = (DataSource)envContext.lookup("jdbc/MyChat");
		} catch(Exception e) { // ����ó��
			e.printStackTrace();
		}
	}
	
	// ���̵� �ߺ�Ȯ��
	public int registerCheck(String userID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user where userID = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next() || userID.equals("")) {
				return 0; // �̹� �����ϴ� ȸ��
			}
			else {
				return 1; // ���� ������ ȸ�� -> �ߺ��˻� ���
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return -1; // �����ͺ��̽� ���� �߻��� ��� ���ϰ�
	}
	
	// ȸ������
	public int register(String userID, String userPassword, String userName, String userProfile) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "insert into user (userID, userPassword, userName, userProfile) values(?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userName);
			pstmt.setString(4, userProfile);
			rs = pstmt.executeQuery(); // sql�� ���� -> ��� ���
			if(rs.next() || userID.equals("")) {
				return 0; // �̹� �����ϴ� ȸ���� ��� ���ϰ�
			}
			else {
				return 1; // ���� ���� ȸ�� ���̵��� ��� ���ϰ�
			}
		} catch(Exception e) { // ���� �߻��� ���
			e.printStackTrace();
		} finally {
			try { // �� �� ���ҽ��� �ݾ��ֱ�
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // �����ͺ��̽� ���� �߻��� ��� ���ϰ�
	}
	
	
}
