package registrar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
	private static DataSource ds;
	private static BasicDataSource bds;
	private final String DRIVER_NAME = "org.postgresql.Driver";
	private final String DB = "jdbc:postgresql://localhost:5432/registrar";
	private final String USER = "postgres";
	private final String PASS = "admin";
	
	private DataSource() throws IOException, SQLException {
		bds = new BasicDataSource();
		bds.setDriverClassName(DRIVER_NAME);
		bds.setUsername(USER);
		bds.setPassword(PASS);
		bds.setUrl(DB);
	}
	
	public static DataSource getInstance() throws IOException, SQLException {
		if (ds==null) {
			ds = new DataSource();
			return ds;
		} else return ds;
	}
	
	public Connection getConnection() throws SQLException {
		return bds.getConnection();
	}
	
	public static void shutDown() throws SQLException {
		bds.close();
	}
	
	public static void printDataSource() {
		System.out.println("Active: "+bds.getNumActive());
		System.out.println("Idle: "+bds.getNumIdle());
		System.out.println("DB: "+bds.getUrl());
	}
}
