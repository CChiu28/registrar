package registrar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Reg {

	public static void main(String[] args) {
		final String db = "jdbc:postgresql://localhost:5432/registrar";
		final String user = "postgres";
		final String pass = "admin";
		Scanner scanner = new Scanner(System.in);
		String input = null;
		boolean quit = false;
		StudentOptions student = new StudentOptions();
		CourseOptions cs = null;
		
		do {
//			input = scanner.next();
			System.out.println("Options:\n1. Add student\n2. Update student\n3. Search students\n4.Add course\n0. Quit\n9. List DB");
			System.out.println("Select your option: ");
			switch(scanner.next()) {
			case "1":
				addStudent(scanner, student);
				break;
			case "2":
				updateStudent(scanner, student);
				break;
			case "0":
				quit = true;
				break;
			case "9":
				listDB();
				break;
			default:
				System.out.println("Please select one of the options below: ");
			}
		} while (!quit);
		System.out.println("Thank you, come again");
		scanner.close();
		System.exit(0);
	}
	
	public static void addStudent(Scanner scanner, StudentOptions student) {
		int id;
		System.out.print("You have selected to add a new student\nPlease enter your ID number: ");
		scanner.nextLine();
		while (!scanner.hasNextInt() || (id = scanner.nextInt())<=0) {
			System.out.print("Please enter a valid ID: ");
			scanner.nextLine();
		}
		scanner.nextLine();
		System.out.println(id);
		System.out.print("Enter last name: ");
		while (!scanner.hasNext("[a-zA-Z]+$")) {
			System.out.print("Enter a valid name: ");
			scanner.nextLine();
		}
		String lname = scanner.nextLine();
		System.out.println(lname);
		System.out.print("Enter first name:");
		while (!scanner.hasNext("[a-zA-Z]+")) {
			System.out.print("Enter a valid name: ");
			scanner.nextLine();
		}
		String fname = scanner.nextLine();
		System.out.println(fname);
		student.addStudent(id, lname, fname);
	}
	
	public static void updateStudent(Scanner scanner, StudentOptions student) {
		int id, findId = 0, newId;
		String lname = null, fname = null;
		System.out.print("You have selected to update student information\nEnter your ID number: ");
		scanner.nextLine();
		while (!scanner.hasNextInt() || (id = scanner.nextInt())<=0) {
			System.out.print("Please enter a valid ID: ");
			scanner.nextLine();
		}
		ResultSet rs = student.findStudent(id);
		try {
			while (rs.next()) {
				findId = rs.getInt("id");
				lname = rs.getString("lname");
				fname = rs.getString("fname");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (findId==0) {
			System.out.println("Cannot find this ID");
		} else {
			System.out.println("We have found: ");
			System.out.println(findId+"\t"+lname+"\t"+fname);
			scanner.nextLine();
			System.out.print("Update last name: ");
			while (!scanner.hasNext("[a-zA-Z]+$")) {
				System.out.print("Enter a valid name: ");
				scanner.nextLine();
			}
			String newLname = scanner.nextLine();
			System.out.println(lname);
			System.out.print("Update first name:");
			while (!scanner.hasNext("[a-zA-Z]+")) {
				System.out.print("Enter a valid name: ");
				scanner.nextLine();
			}
			String newFname = scanner.nextLine();
			System.out.println(" Updating ID: "+findId+"\t"+newLname+", "+newFname);
			student.updateStudent(findId, newLname, newFname);
		}
	}
	
	public static void listDB() {
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			c = DataSource.getInstance().getConnection();
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM students;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String lname = rs.getString("lname");
				String fname = rs.getString("fname");
				System.out.println(id+"\t"+lname+"\t"+fname);
			}
		//		rs = stmt.executeQuery("SELECT * FROM class;");
		//		while (rs.next()) {
		//			int id = rs.getInt("id");
		//			String cname = rs.getString("class_name");
		//			System.out.println(id+"\t"+cname);
		//		}
			DataSource.getInstance().printDataSource();
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
