package registrar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CourseOptions {
	private Connection c = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private final Scanner scanner = new Scanner(System.in);
	
	public CourseOptions() {
		try {
			c = DataSource.getInstance().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addCourseForStudent() {
		System.out.println("You have chosen to add a course for a student");
		int id = getIdInput();
	}
	
	public void showCourses() {
		try {
			stmt = c.createStatement();
			String sql = "SELECT * FROM class";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String cname = rs.getString("class_name");
				System.out.println(id+"\t"+cname);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
			if (stmt != null) try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
			if (c != null) try { c.close(); } catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	private int getIdInput() {
		int id = 0;
		System.out.print("Please enter an ID: ");
		scanner.nextLine();
		while (!scanner.hasNextInt()||(id=scanner.nextInt())<=0) {
			System.out.print("Please enter a valid ID: ");
			scanner.nextLine();
		}
		scanner.nextLine();
		return id;
	}
}
