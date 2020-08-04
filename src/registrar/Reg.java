package registrar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Reg {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean quit = false;
		StudentOptions student = new StudentOptions();
		CourseOptions cs = new CourseOptions();
		
		do {
			System.out.println("Options:\n1. Add student\n2. Update student\n3. Search students\n4. Delete student\n0. Quit\n8. Show courses\n9. List DB");
			System.out.println("Select your option: ");
			switch(scanner.next()) {
			case "1":
				addStudent(scanner, student);
				break;
			case "2":
				updateStudent(scanner, student);
				break;
			case "3":
				student.findStudent();
				break;
			case "4":
				delStudent(student);
				break;
			case "0":
				quit = true;
				break;
			case "8":
				cs.showCourses();
				break;
			case "9":
				student.listDB();
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
		System.out.println("You have selected to add a new student");
		id = student.getIdInput();
		System.out.println(id);
//		scanner.nextLine();
		System.out.print("Enter last name: ");
//		scanner.nextLine();
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
		student.addStudent(id, fname, lname);
	}
	
	public static void delStudent(StudentOptions stud) {
		int id;
		System.out.println("You have selected to delete a student");
		id = stud.getIdInput();
		System.out.println(id);
		stud.delStudent(id);
	}
	public static void updateStudent(Scanner scan, StudentOptions stud) {
		int findId = 0;
		String lname = null, fname = null;
		System.out.print("You have selected to update student information\n");
		int id = stud.getIdInput();
		ResultSet rs = stud.getStudentInfo(id);
		try {
			if (rs!=null)
				while (rs.next()) {
					System.out.println(rs.getString(1));
					findId = Integer.parseInt(rs.getString(1));
					lname = rs.getString(2);
					fname = rs.getString(3);
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
			scan.nextLine();
			System.out.print("Update last name: ");
			while (!scan.hasNext("[a-zA-Z]+$")) {
				System.out.print("Enter a valid name: ");
				scan.nextLine();
			}
			String newLname = scan.nextLine();
			System.out.println(newLname);
			System.out.print("Update first name:");
			while (!scan.hasNext("[a-zA-Z]+")) {
				System.out.print("Enter a valid name: ");
				scan.nextLine();
			}
			String newFname = scan.nextLine();
			System.out.println(" Updating ID: "+findId+"\t"+newLname+", "+newFname);
			stud.updateStudent(findId, newFname, newLname);
		}
	}
}
