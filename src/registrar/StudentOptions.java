package registrar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentOptions {
	private Connection c = null;
//	private PreparedStatement stmt = null;
	
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
		final String sql = "INSERT INTO students (id, lname, fname) "
				+"VALUES (?, ?, ?);";
		try (PreparedStatement pst = c.prepareStatement(sql)) {
//			stmt = c.prepareStatement(sql);
//			stmt.executeUpdate(sql);
			pst.setInt(1, id);
			pst.setString(2, lname);
			pst.setString(3, fname);
			pst.executeUpdate();
			DataSource.getInstance().printDataSource();
			c.commit();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
	
	public void delStudent(int id) {
		final String sql = "DELETE FROM students WHERE id=?;";
		try (PreparedStatement pst = c.prepareStatement(sql)){
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
	
	public ResultSet findStudent(int id) {
		ResultSet rs = null;
		final String sql = "SELECT * FROM students WHERE id=?;";
		PreparedStatement pst = null;
		try {
			pst = c.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
		return rs;
	}
	
	public void updateStudent(int id, String lname, String fname) {
		try {
			Statement stmt = c.createStatement();
			final String sql = "UPDATE students SET lname='"+lname+"', fname='"+fname+"' WHERE id="+id+";";
			int res = stmt.executeUpdate(sql);
			c.commit();
			System.out.println(res+" records have been updated");
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
}
