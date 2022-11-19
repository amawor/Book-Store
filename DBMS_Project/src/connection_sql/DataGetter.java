package connection_sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import Obj.Order;
import Obj.Sells;


public class DataGetter {
	private Statement statement;
	public DataGetter(Statement statement) {
		this.statement=statement;
	}
	public ArrayList<Obj.Book> getBookByCourseID(String CourseID) throws SQLException {
		ArrayList<Obj.Book> books=new ArrayList();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Contains AS C LEFT JOIN Book AS B ON C.book_ID=B.book_ID "
				+ "WHERE course_ID='"+CourseID+"' GROUP BY B.Book_ID limit 10");
		while (resultSet.next()) {
			books.add(new Obj.Book(resultSet.getString("book_ID"),resultSet.getString("book_name").trim(),resultSet.getString("ISBN"),resultSet.getString("author"),resultSet.getString("publish_Date"),resultSet.getString("publisher"),resultSet.getDouble("origin_Price"),resultSet.getString("book_ID")));
		}
		resultSet.next();
		return books; 
	}
	public ArrayList<Obj.Book> getBookByName(String name){
		ArrayList<Obj.Book> books=new ArrayList();
		ResultSet resultSet;
		try {
			resultSet = statement.executeQuery("SELECT B.*,C.course_name,C.teacher,C.semester "
					+ "FROM Bookstore.Book AS B "
					+ "LEFT JOIN ("
					+ "SELECT Con.book_ID,Cou.course_name,Cou.teacher,Con.semester FROM Bookstore.Contains AS Con "
					+ "LEFT JOIN Bookstore.Course AS Cou "
					+ "ON Con.course_ID=Cou.course_ID AND Con.semester=Cou.semester "
					+ "GROUP BY Con.book_ID) "
					+ "AS C ON B.book_ID=C.book_ID "
					+ "WHERE B.book_name LIKE '%"+name+"%' OR C.course_name LIKE '%"+name+"%' "
					+ "GROUP BY B.book_ID limit 10");
		
		while (resultSet.next()) {
			books.add(new Obj.Book(resultSet.getString("book_ID"),resultSet.getString("book_name").trim(),resultSet.getString("ISBN").trim(),resultSet.getString("author"),resultSet.getString("publish_Date"),resultSet.getString("publisher"),resultSet.getDouble("origin_Price"),resultSet.getString("book_ID"),resultSet.getString("course_name").trim(),resultSet.getString("teacher").trim(),resultSet.getInt("semester")));
		}} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return books;
	}
	public Obj.Book getBookByISBN(String ISBN) throws SQLException{
		Obj.Book book=null;
		
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Bookstore.Book AS B WHERE ISBN LIKE'%"+ISBN+"%' limit 1");
		while (resultSet.next()) {			
			book=new Obj.Book(resultSet.getString("book_ID"),resultSet.getString("book_name").trim(),resultSet.getString("ISBN"),resultSet.getString("author"),resultSet.getString("publish_Date"),resultSet.getString("publisher"),resultSet.getDouble("origin_Price"),resultSet.getString("book_ID"));
		}
		return book;
	}
	public double getPrice(String bookID,int bookStatus) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT price,amount FROM Price WHERE book_ID='"+bookID+"' limit 3");
		return resultSet.getDouble(bookStatus);
	}
	public Vector<Vector> getOrder(String memberID) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT B.book_name,O.amount,O.subtotal,O.dateOfCreate,O.status FROM Bookstore.Book AS B\r\n"
				+ "RIGHT JOIN\r\n"
				+ "(SELECT I.book_ID,I.amount,I.amount*I.price AS subtotal,O.dateOfCreate,O.status FROM Bookstore.Include AS I\r\n"
				+ "LEFT JOIN Bookstore.Order AS O ON I.order_ID=O.order_ID WHERE O.member_ID='"+memberID+"') AS O\r\n"
				+ "ON B.book_ID=O.book_ID ORDER BY dateOfCreate DESC limit 10");
		ArrayList<Obj.Order> orders=new ArrayList();
		Vector<String>data;
		Vector<Vector>datas=new Vector();
		while(resultSet.next()) {
			data=new Vector();
			data.addElement(resultSet.getString("book_name"));
			data.addElement(resultSet.getString("amount"));
			data.addElement(resultSet.getString("subtotal"));
			data.addElement(resultSet.getString("dateOfCreate"));
			if(resultSet.getString("status").trim().equals("0")) {
				data.addElement("unfinished");
			}else {
				data.addElement("completed");
			}
			datas.addElement(data);
		}
		return datas;
	}
	public Vector<Vector> getSells(String memberID) throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT B.book_name,S.status,CAST(B.origin_Price*S.depreciation as decimal(38, 2)) As price,S.dateOfCreate\r\n"
				+ "FROM Bookstore.Book AS B\r\n"
				+ "Right JOIN Bookstore.Sells AS S\r\n"
				+ "ON B.book_ID=S.book_ID\r\n"
				+ "WHERE S.member_ID='"+memberID+"'\r\n"
				+ "ORDER BY S.dateOfCreate DESC");
		Vector<String>data;
		Vector<Vector>datas=new Vector();
		while(resultSet.next()) {
			data=new Vector();
			data.addElement(resultSet.getString("book_name"));
			if(resultSet.getString("status").trim().equals("0")) {
				data.addElement("unfinished");
			}else {
				data.addElement("completed");
			}
			data.addElement(resultSet.getString("price"));
			data.addElement(resultSet.getString("dateOfCreate"));
			if(resultSet.getString("status").trim().equals("0")) {
				data.addElement("cancel");
			}else {
				data.addElement("completed");
			}
			datas.addElement(data);
		}
		return datas;
	}
	public Obj.Member findMember(String memberID) {
		Obj.Member member=null;
		ResultSet resultSet;
		try {
			resultSet = statement.executeQuery("SELECT * FROM Bookstore.Member WHERE member_ID = '"+memberID+"' limit 1");
		
		while(resultSet.next()) {
			member=new Obj.Member(resultSet.getString("member_ID"),resultSet.getString("password"),resultSet.getString("name"),resultSet.getString("mail"),resultSet.getString("phone"),resultSet.getInt("member_type"));
			
		}} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return member;
	}
	public String getStudentID(String memberID) throws SQLException {
		String studentID="";
		ResultSet resultSet = statement.executeQuery("SELECT student_ID FROM Bookstore.Campus_Member "
				+ "WHERE member_ID='"+memberID+"' ");
		while(resultSet.next()) {
			studentID=resultSet.getString("student_ID");
		}
		return studentID;
	}
}
