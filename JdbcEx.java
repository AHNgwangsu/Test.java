package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcEx {
	static String url = "jdbc:mysql://localhost:3306/firm";
	static String id = "root";
	static String pass = "mysql";
	static Scanner scan = new Scanner(System.in);

	void datase() throws SQLException {

		String sql = "select * from emp";
		boolean run = true;
		Connection conn = DriverManager.getConnection(url, id, pass);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		try (conn; stmt; rs;) {
			while (run) {
				System.out.println("1.데이터 보기");
				System.out.println("2.데이터 입력");
				System.out.println("3.데이터 갱신");
				System.out.println("4.데이터 삭제");
				System.out.println("5.종료");
				System.out.println("입력: ");
				int bt = Integer.parseInt(scan.nextLine());

				switch (bt) {
				case 1:
					data1(conn);
					break;
				case 2:

					data2(conn);

					break;
				case 3:

					data3(conn);

					break;
				case 4:
					data4(conn);

					break;
				case 5:
					run = false;
					break;
				default:
					System.out.println("유효하지 않은 선택. 다시 시도하세요.");
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("시스템 종료");
	}

	public static void main(String[] args) {

		JdbcEx ts = new JdbcEx();
		try {
			ts.datase();
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}

	static void data1(Connection connection) throws SQLException {
		String sql = "select * from emp";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.print(rs.getInt("empno") + "\t");
			System.out.print(rs.getString("ename") + "\t");
			System.out.print(rs.getString("job") + "\t");
			System.out.print(rs.getInt("mgr") + "  \t");
			System.out.print(rs.getString("hiredate") + "\t");
			System.out.print(rs.getDouble("sal") + "\t");
			System.out.print(rs.getString("comm") + "\t");
			System.out.println(rs.getInt("deptno"));
		}

	}

	static void data2(Connection connection) throws SQLException {
		System.out.println("사번: ");
		int empno = Integer.parseInt(scan.nextLine());
		System.out.println("이름:");
		String ename = scan.nextLine();
		System.out.println("직급:");
		String job = scan.nextLine();
		System.out.println("상사 사번: ");
		int mgr = Integer.parseInt(scan.nextLine());
		System.out.println("입사일: ");
		String hiredate = scan.nextLine();
		System.out.println("월급: ");
		double sal = Double.parseDouble(scan.nextLine());
		System.out.println("인센티브: ");
		double comm = Double.parseDouble(scan.nextLine());
		System.out.println("부서번호:");
		int deptno = Integer.parseInt(scan.nextLine());
		Connection conn = DriverManager.getConnection(url, id, pass);
		Statement stmt = conn.createStatement();
		String sql = "insert into emp VALUES (" + empno + ", '" + ename + "', '" + job + "','" + mgr + "', '" + hiredate
				+ "', '" + sal + "','" + comm + "', '" + deptno + "')";
		System.out.println(sql);

		int result = stmt.executeUpdate(sql);
		if (result >= 1) {
			System.out.println("입력 성공");
		} else {
			System.out.println(" 실패");

		}
	}

	static void data3(Connection connection) throws SQLException {
		System.out.print("사번: ");
		String empno = scan.nextLine();
		System.out.print("수정 sal:");
		Double sal = Double.parseDouble(scan.nextLine());
		System.out.print("수정 직급:");
		String job = scan.nextLine();
		System.out.print("수정 인센티브:");
		Double comm = Double.parseDouble(scan.nextLine());
		Connection conn = DriverManager.getConnection(url, id, pass);
		Statement stmt = conn.createStatement();
		String sql = "update emp set sal = '" + sal + "', job = '" + job + "', comm = '" + comm + "' where empno = '"
				+ empno + "'";
		System.out.println(sql);
		int result = stmt.executeUpdate(sql);
		if (result == 1) {
			System.out.println("수정 성공");
		} else {
			System.out.println("수정 실패");
		}
	}

	static void data4(Connection connection) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/firm";
		String id = "root";
		String pass = "mysql";
		Connection conn = DriverManager.getConnection(url, id, pass);
		Statement stmt = conn.createStatement();
		System.out.println("삭제할 사번을 입력:");
		String empno = scan.nextLine();
		String sql = "delete from emp where empno =" + empno;
		int result = stmt.executeUpdate(sql);
		if (result >= 1) {
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
		}

	}
}
