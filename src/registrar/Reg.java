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
			System.out.println("Options:\n1. Add student\n2. Update student\n3. Search students\n4.Add course\n0. Quit\n8. Show courses\n9. List DB");
			System.out.println("Select your option: ");
			switch(scanner.next()) {
			case "1":
				student.addStudent();
				break;
			case "2":
				student.updateStudent();
				break;
			case "3":
				student.findStudent();
				break;
			case "4":
				
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
	
//	public static void addStudent(Scanner scanner, StudentOptions student) {
//		int id;
//		System.out.println("You have selected to add a new student");
//		id = getIdInput(scanner);
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
//		student.addStudent(id, lname, fname);
//	}

}
