package registrar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Reg {

	public static void main(String[] args) {
		final String db = "jdbc:postgresql://localhost:5432/registrar";
		final String user = "postgres";
		final String pass = "admin";
		DataSource ds = null;
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		StudentOptions addstudent = null;
		CourseOptions cs = null;
		try {
			c = DataSource.getInstance().getConnection();
			addstudent = new StudentOptions();
			cs = new CourseOptions();
			DataSource.getInstance().printDataSource();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened DB succcessfully");
		
//		addstudent.addStudent(123456, "Sefs", "Sefsf");
//		addstudent.delStudent(123456);
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM students;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String lname = rs.getString("lname");
				String fname = rs.getString("fname");
				System.out.println(id+"\t"+lname+"\t"+fname);
			}
//			rs = stmt.executeQuery("SELECT * FROM class;");
//			while (rs.next()) {
//				int id = rs.getInt("id");
//				String cname = rs.getString("class_name");
//				System.out.println(id+"\t"+cname);
//			}
			cs.showCourses();
			DataSource.getInstance().printDataSource();
//			rs.close();
//			stmt.close();
//			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" "+e.getMessage());
			System.exit(0);
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stmt != null) try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
			if (c != null) try { c.close(); } catch (SQLException e) {e.printStackTrace();}
		}
	}

}
