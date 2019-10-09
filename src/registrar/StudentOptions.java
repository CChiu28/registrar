package registrar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class StudentOptions {
	String db;
	String user;
	String pass;
	Connection c = null;
	Statement stmt = null;
	
	public StudentOptions(String db, String user, String pass) {
		this.db = db;
		this.user = user;
		this.pass = pass;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection(db, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	public void addStudent(int id, String lname, String fname) {
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO students (id, lname, fname) "
					+"VALUES ('"+id+"', '"+lname+"', '"+fname+"');";
			stmt.execute(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
	
	public void delStudent(int id) {
		try {
			stmt = c.createStatement();
			String sql = "DELETE FROM students WHERE id="+id;
			stmt.execute(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
}
