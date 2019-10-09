package registrar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Reg {

	public static void main(String[] args) {
		final String db = "jdbc:postgresql://localhost:5432/registrar";
		final String user = "postgres";
		final String pass = "admin";
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection(db, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened DB succcessfully");
		
		StudentOptions addstudent = new StudentOptions(db, user, pass);
//		addstudent.addStudent(123456, "Sefs", "Sefsf");
		addstudent.delStudent(123456);
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM students;");
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
//			rs.close();
//			stmt.close();
//			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" "+e.getMessage());
			System.exit(0);
		}
	}

}
