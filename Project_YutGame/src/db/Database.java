package db;

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Database {
	
	DefaultListModel<String> gamelistdata = new DefaultListModel<String>();
	
//	Game List를 보여주기 위한 함수
	public DefaultListModel gameShow() {
		gamelistdata.removeAllElements();
		Connection conn;
		Statement stmt = null;
		try {
			String url = "jdbc:mysql://localhost:3306/Opensource";
			String user = "root";
			String pw = "2017018023";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
//			user_game 테이블에 있는 모든 값을 선택
			ResultSet srs = stmt.executeQuery("select * from user_game");
			
			while (srs.next()) {
//				GameStatus가 false인 값만 gamelistdata에 입력
				if (!srs.getBoolean("GameStatus")) {
					gamelistdata.addElement(srs.getInt("RoomNum")
							+ "\t|\t" + srs.getString("HostId")
							+ "\t|\t" + srs.getInt("Totalpop")
							+ "\t|\t" + srs.getBoolean("GameStatus"));
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver load error");
		} catch (SQLException e) {
			System.out.println("SQL error");
		}
		
		return gamelistdata;
		
	}

//	Create Room 버튼 클릭 시 작동
	public void createGame() {
		Connection conn;
		Statement stmt = null;
		try {
			String url = "jdbc:mysql://localhost:3306/Opensource";
			String user = "root";
			String pw = "2017018023";
			
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, pw);
			stmt = conn.createStatement();
//			RoomNum의 마지막 값을 알려주는 쿼리문
			ResultSet srs = stmt.executeQuery("select last_value(RoomNum) over() as rn_last from game");

			srs.next();
//			RoomNum 마지막 값 + 1
			Integer rn_last = srs.getInt("rn_last") + 1;
			
			System.out.println(rn_last);
			
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver load error");
		} catch (SQLException e) {
			System.out.println("SQL error");
		}
	}

}
