package registrar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentOptions {
	private Connection c = null;
	private Statement stmt = null;
	
	public StudentOptions() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DataSource.getInstance().getConnection();
			c.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	public void addStudent(int id, String lname, String fname) {
		try {
			stmt = c.createStatement();
			final String sql = "INSERT INTO students (id, lname, fname) "
					+"VALUES ('"+id+"', '"+lname+"', '"+fname+"');";
			stmt.executeUpdate(sql);
			stmt.close();
			DataSource.getInstance().printDataSource();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
	
	public void delStudent(int id) {
		try {
			stmt = c.createStatement();
			final String sql = "DELETE FROM students WHERE id="+id+";";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
	
	public ResultSet findStudent(int id) {
		ResultSet rs = null;
		try {
			stmt = c.createStatement();
			final String sql = "SELECT * FROM students WHERE id="+id+";";
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
		return rs;
	}
	
	public void updateStudent(int id, String lname, String fname) {
		try {
			stmt = c.createStatement();
			final String sql = "UPDATE students SET lname='"+lname+"', fname='"+fname+"' WHERE id="+id+";";
			int res = stmt.executeUpdate(sql);
			c.commit();
			System.out.println(res+" records have been updated");
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
}
