package Swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EmpEx extends JFrame {

	JTextField tf1 = new JTextField(5);
	JTextField tf2 = new JTextField(5);
	JTextField tf3 = new JTextField(5);
	JTextField tf4 = new JTextField(5);
	JTextField tf5 = new JTextField(5);
	JTextField tf6 = new JTextField(5);
	JTextField tf7 = new JTextField(5);
	JTextField tf8 = new JTextField(5);

	JTextArea ta = new JTextArea(20, 65);

	Connection conn;
	Statement stmt;

	public EmpEx() {
		String url = "jdbc:mysql://localhost:3306/firm";
		String id = "root";
		String pass = "mysql";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		JLabel lb1 = new JLabel("사번:");
		JLabel lb2 = new JLabel("이름:");
		JLabel lb3 = new JLabel("직급:");
		JLabel lb4 = new JLabel("상사 사번:");
		JLabel lb5 = new JLabel("입사일:");
		JLabel lb6 = new JLabel("월급	:");
		JLabel lb7 = new JLabel("인센티브:");
		JLabel lb8 = new JLabel("부서 번호:");

		JButton bt1 = new JButton("전체 내용");
		JButton bt2 = new JButton("입력");
		JButton bt3 = new JButton("이름 검색");
		JButton bt4 = new JButton("수정");
		JButton bt5 = new JButton("삭제");

		bt1.setPreferredSize(new Dimension(120, 40));
		bt2.setPreferredSize(new Dimension(120, 40));
		bt3.setPreferredSize(new Dimension(120, 40));
		bt4.setPreferredSize(new Dimension(120, 40));
		bt5.setPreferredSize(new Dimension(120, 40));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		JPanel jp1 = new JPanel(new FlowLayout());
		jp1.add(bt1);
		jp1.add(bt2);
		jp1.add(bt3);
		jp1.add(bt4);
		jp1.add(bt5);
		con.add(jp1, BorderLayout.SOUTH);
		JScrollPane scroll = new JScrollPane(ta);
		JPanel jp2 = new JPanel(new FlowLayout());
		jp2.add(scroll);
		con.add(jp2, BorderLayout.CENTER);

		JPanel jp3 = new JPanel(new FlowLayout());
		con.add(jp3, BorderLayout.NORTH);
		jp3.add(lb1);
		jp3.add(tf1);
		jp3.add(lb2);
		jp3.add(tf2);
		jp3.add(lb3);
		jp3.add(tf3);
		jp3.add(lb4);
		jp3.add(tf4);
		jp3.add(lb5);
		jp3.add(tf5);
		jp3.add(lb6);
		jp3.add(tf6);
		jp3.add(lb7);
		jp3.add(tf7);
		jp3.add(lb8);
		jp3.add(tf8);

		this.setTitle("EMP 관리");
		this.setLocation(500, 200);
		this.setSize(900, 400);
		this.setVisible(true);

		bt1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				select();
				clearTextField();

			}
		});
		bt2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
				clearTextField();
				select();
			}
		});
		bt3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				select2();
			}
		});
		bt4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				clearTextField();
				select();
			}
		});
		bt5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
				clearTextField();
				select();

			}
		});

	}

	private void clearTextField() {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		tf4.setText("");
		tf5.setText("");
		tf6.setText("");
		tf7.setText("");
		tf8.setText("");
	}

	public void select() {
		String sql = "select * from emp";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ta.setText("");
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mgr = rs.getInt("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");
				String str = String.format(
						"사번: %d, 이름:'%s', 직급:'%s', 상사 사번:%d,입사일:'%s', 월급:%.2f, 인센티브: %.2f, 부서 번호:%d\n", empno, ename,
						job, mgr, hiredate, sal, comm, deptno);
				ta.append(str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert() {
		String sql = String.format("insert into emp values(%s, '%s', '%s', %s, '%s', %.2f, %.2f, %s)", tf1.getText(),
				tf2.getText(), tf3.getText(), tf4.getText(), tf5.getText(), Double.parseDouble(tf6.getText()),
				Double.parseDouble(tf7.getText()), tf8.getText());
		try {
//			
			int res = stmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void select2() {

		String sql = String.format("select * from emp where ename = '%s'", tf2.getText());
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ta.setText("");
			if (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String mgr = rs.getString("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");
				String str = String.format("사번:%d, 이름:%s,직급: %s, 상사 사번:%s,입사일:%s,월급:%.2f,인센티브:%.2f  부서 번호:%d ", empno,
						ename, job, mgr, hiredate, sal, comm, deptno);
				ta.append(str);

			} else {
				tf2.setText("");
				ta.append("해당 자료 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		String sql = String.format("update emp set  sal = %.2f, comm = %.2f, job = '%s' where empno = %s",

				Double.parseDouble(tf6.getText()), Double.parseDouble(tf7.getText()), tf3.getText(), tf1.getText());
		try {
			int res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		String sql = String.format("delete from emp where empno = %s", tf1.getText());
		try {
			int res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new EmpEx();
	}
}
