package registrar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentOptions {
	private Connection c = null;
	private final Scanner scanner = new Scanner(System.in);
//	private PreparedStatement stmt = null;
	
	public StudentOptions() {
		try {
//			Class.forName("org.postgresql.Driver");
			c = DataSource.getInstance().getConnection();
			c.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	public void addStudent(int id, String fname, String lname) {
//		int id;
//		System.out.println("You have selected to add a new student");
//		id = getIdInput();
//		System.out.println(id);
//		System.out.print("Enter last name: ");
//		while (!scanner.hasNext("[a-zA-Z]+$")) {
//			System.out.print("Enter a valid name: ");
//			scanner.nextLine();
//		}
//		String lname = scanner.nextLine();
//		System.out.println(lname);
//		System.out.print("Enter first name:");
//		while (!scanner.hasNext("[a-zA-Z]+")) {
//			System.out.print("Enter a valid name: ");
//			scanner.nextLine();
//		}
//		String fname = scanner.nextLine();
//		System.out.println(fname);
		final String sql = "INSERT INTO students (id, lname, fname) "
				+"VALUES (?, ?, ?);";
		try (PreparedStatement pst = c.prepareStatement(sql)) {
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
			c.commit();
			System.out.println("Student "+id+" is deleted\n");
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
		}
	}
	
	public void findStudent() {
		System.out.println("You have selected to search for a student");
		int id = getIdInput();
		ResultSet rs = getStudentInfo(id);
		String findId, lname, fname;
		try {
			if (rs==null)
				System.out.println("No student found with ID "+id);
			else while (rs.next()) {
				findId = rs.getString(1);
				lname = rs.getString(2);
				fname = rs.getString(3);
				System.out.println("Found\n"+findId+"\t"+lname+"\t"+fname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void updateStudent(int findId, String newFname, String newLname) {
//		int findId = 0;
//		String lname = null, fname = null;
//		System.out.print("You have selected to update student information");
//		int id = 0;
//		ResultSet rs = getStudentInfo(id);
//		try {
//			if (rs!=null)
//				while (rs.next()) {
//					System.out.println(rs.getString(1));
//					findId = Integer.parseInt(rs.getString(1));
//					lname = rs.getString(2);
//					fname = rs.getString(3);
//				}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (findId==0) {
//			System.out.println("Cannot find this ID");
//		} else {
//			System.out.println("We have found: ");
//			System.out.println(findId+"\t"+lname+"\t"+fname);
//			System.out.print("Update last name: ");
//			while (!scanner.hasNext("[a-zA-Z]+$")) {
//				System.out.print("Enter a valid name: ");
//				scanner.nextLine();
//			}
//			String newLname = scanner.nextLine();
//			System.out.println(newLname);
//			System.out.print("Update first name:");
//			while (!scanner.hasNext("[a-zA-Z]+")) {
//				System.out.print("Enter a valid name: ");
//				scanner.nextLine();
//			}
//			String newFname = scanner.nextLine();
//			System.out.println(" Updating ID: "+findId+"\t"+newLname+", "+newFname);
			final String sql = "UPDATE students SET lname=?, fname=? WHERE id=?;";
			try (PreparedStatement pst = c.prepareStatement(sql)) {
				pst.setInt(3, findId);
				pst.setString(1, newLname);
				pst.setString(2, newFname);
				int res = pst.executeUpdate();
				DataSource.getInstance().printDataSource();
				c.commit();
				System.out.println(res+" records have been updated");
			} catch (Exception e) {
				System.err.println(e.getClass().getName()+": "+e.getMessage());
			}
//		}
//		if(rs!=null)
//			try {
//				rs.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
	}
	
	public void listDB() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = c.prepareStatement("SELECT * FROM students;");
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String lname = rs.getString("LNAME");
				String fname = rs.getString("FNAME");
				System.out.println(id+"\t"+lname+"\t"+fname);
			}
			DataSource.getInstance().printDataSource();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+" "+e.getMessage());
			System.exit(0);
		} 
//		finally {
//			if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
//			if (stmt != null) try { stmt.close(); } catch (SQLException e) {e.printStackTrace();}
//			if (c != null) try { c.close(); } catch (SQLException e) {e.printStackTrace();}
//		}
	}
	
	public int getIdInput() {
		int id = 0;
		System.out.print("Please enter an ID: ");
		while (!scanner.hasNextInt()||(id=scanner.nextInt())<=0) {
			System.out.print("Please enter a valid ID: ");
			scanner.nextLine();
		}
		scanner.nextLine();
		return id;
	}
	
	public ResultSet getStudentInfo(int id) {
		ResultSet rs = null;
		final String sql = "SELECT * FROM students WHERE id=?;";
		PreparedStatement pst = null;
		try {
			pst = c.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
		} catch (Exception e) {
			System.err.print(e);
		}
		return rs;
	}
}
