import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Databse{
	protected static final String database = "jdbc:mysql://localhost:3306/simple_project";
	protected static final String user = "root";
	protected static final String pass = "root";
	public static Connection conn = null;
	public static Statement stmt = null;
	public static PreparedStatement pstmt = null;
	public static void main(String[] args) throws SQLException {
		try {
		conn = DriverManager.getConnection(database, user, pass);
		System.out.println("Connection is built!!");
		conn.setAutoCommit(true);
		createTable();
		insertTable(101,"Yamini",15000);
		insertTable(102,"Allen",14000);
		insertTable(103,"Veronica",12000);
		displayTable();
		deleteTable();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
	}
	//create table if it does not exists
		private static void createTable() throws SQLException {
			stmt = conn.createStatement(); // statement is initialized
			stmt.executeUpdate("create table if not exists emp ( id int primary key, name varchar(255) not null, salary int);");
			System.out.println("Table is created");
		}
		//insert records
		private static void insertTable(int id, String name, int salary) throws SQLException {
			String sql = "insert into emp (id,name,salary) values (?,?,?)";
		    pstmt = conn.prepareStatement(sql); //initialization
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setInt(3, salary);
			int rowsInserted = pstmt.executeUpdate();
		if(rowsInserted>0) {
			System.out.println(rowsInserted+" Row been inserted");
		}
      }
		//display the entire table records
		private static void displayTable() throws SQLException {
			String sql = "Select * from emp";
			pstmt = conn.prepareStatement(sql); // you need to initialize any statements object before using
			ResultSet rs = pstmt.executeQuery();
			System.out.println("ID\t"+"Name\t"+"Salary\t");
			while(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int salary = rs.getInt("salary");
				System.out.println(id+"\t"+name+"\t"+salary);
			}
		}
		//delete a particular record from table
		private static void deleteTable() throws SQLException {
			String sql = "delete from emp where id = 101";
			pstmt = conn.prepareStatement(sql);
			int rowsDeleted = pstmt.executeUpdate();
			if(rowsDeleted>0) {
				System.out.println("The number of rows deleted is = "+rowsDeleted);
			}
			
		}
	}
