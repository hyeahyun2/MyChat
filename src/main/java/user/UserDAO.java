package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// DAO : Data Access Object (���ٰ�ü)
public class UserDAO {
	// Ŀ�ؼ�Ǯ
	DataSource dataSource;

	public UserDAO() {
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
	
	// ȸ������
	public int register(String userID, String userPassword, String userName, String userProfile) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "insert into user values(?, ?, ?, ?)";
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
