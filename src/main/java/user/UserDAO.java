package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// DAO : Data Access Object (접근객체)
public class UserDAO {
	// 커넥션풀
	DataSource dataSource;

	public UserDAO() {
		// 객체 생성되자마자 데이터베이스에 접속
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			// lookup(Resource의name값)
			dataSource = (DataSource)envContext.lookup("jdbc/MyChat");
		} catch(Exception e) { // 오류처리
			e.printStackTrace();
		}
	}
	
	// 회원가입
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
			rs = pstmt.executeQuery(); // sql문 실행 -> 결과 담기
			if(rs.next() || userID.equals("")) {
				return 0; // 이미 존재하는 회원일 경우 리턴값
			}
			else {
				return 1; // 가입 가능 회원 아이디일 경우 리턴값
			}
		} catch(Exception e) { // 오류 발생한 경우
			e.printStackTrace();
		} finally {
			try { // 다 쓴 리소스들 닫아주기
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // 데이터베이스 오류 발생한 경우 리턴값
	}
}
