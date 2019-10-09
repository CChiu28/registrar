package registrar;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StudentOptionsTest {
	StudentOptions studTest;
	static final String db = "jdbc:postgresql://localhost:5432/registrar";
	static final String user = "postgres";
	static final String pass = "admin";
	static Connection c = null;
	static Statement stmt = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		c = DriverManager.getConnection(db,user,pass);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStudentOptions() {
		try {
			studTest = new StudentOptions(db, user, pass);
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			fail("Exception occurred");
		}
	}

	@Test
	public void testAddStudent() {
		ResultSet rs = null;
		int id;
		String lname, fname;
		try {
			studTest = new StudentOptions(db,user,pass);
			studTest.addStudent(1234, "last", "first");
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM students WHERE id=1234;");
			assertTrue(rs.next());
			id = rs.getInt("id");
			lname = rs.getString("lname");
			fname = rs.getString("fname");
			assertEquals(1234, id);
			assertEquals("last", lname);
			assertEquals("first", fname);
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			fail("Exception occurred");
		}
	}

	@Test
	public void testDelStudent() {
		fail("Not yet implemented");
	}

}
