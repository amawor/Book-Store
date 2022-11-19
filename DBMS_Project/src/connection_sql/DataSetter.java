package connection_sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataSetter{
	private Statement statement;
	public DataSetter(Statement statement) {
		this.statement=statement;
	}
	public boolean createMember(Obj.Member member) {
		try {
			statement.execute("INSERT INTO Bookstore.Member(member_ID,password,name,mail,phone,member_type)"
					+ "VALUES('"+member.getMemberID()+"','"+member.getPassword()+"','"+member.getName()+"','"+member.getMail()+"',"+member.getPhone()+","+member.getMemberType()+");");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean createNormalMember(Obj.Member member) {
		try {
			statement.execute("INSERT INTO Bookstore.Normal_Member(member_ID)"
					+ "VALUES('"+member.getMemberID()+"');");
			createMember(member);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean createCampusMember(Obj.Member member,String schoolID) {
		try {
			statement.execute("INSERT INTO Bookstore.Campus_Member(member_ID,student_ID)"
					+ "VALUES('"+member.getMemberID()+"','"+schoolID+"');");
			createMember(member);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public boolean upgradeMember(String memberID,String schoolID) {
		try {
			statement.execute("DELETE FROM Bookstore.Normal_Member WHERE member_ID='"+memberID+"'");
			statement.execute("UPDATE Bookstore.Member SET member_type=1 WHERE member_ID='"+memberID+"'");
			statement.execute("INSERT INTO Bookstore.Campus_Member(member_ID,student_ID)"
					+ "VALUES('"+memberID+"','"+schoolID+"');");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	public boolean createOrder(String memberID,ArrayList<String> bookIDs,ArrayList<String> amounts, ArrayList<String> prices) throws SQLException {
		int total=0,orderID=0;
		for(int i=0;i<amounts.size();i++) {
			total+=Integer.parseInt(amounts.get(i))*Double.parseDouble(prices.get(i));
		}
		if(!statement.execute("INSERT INTO Bookstore.Order (total,status,dateOfCreate,member_ID) VALUES("
			+total+",0,'"+LocalDate.now()+"','"+memberID+"')")) {
			
			ResultSet resultSet=statement.executeQuery("SELECT order_ID FROM Bookstore.Order "
					+ "WHERE total="+total+" AND member_ID='"+memberID+"' ORDER BY order_ID DESC limit 1");
			while(resultSet.next()) {
				orderID=resultSet.getInt("order_ID");
			}
			for(int i=0;i<bookIDs.size();i++) {
				if(statement.execute("INSERT INTO Bookstore.Include (book_ID,order_ID,amount,price) "
						+ "VALUES('"+bookIDs.get(i)+"',"+orderID+","+amounts.get(i)+","+prices.get(i)+")")) {
					return false;
				}
			}
		}else {
			return false;
		}
		return true;
	}
	public boolean createSells(int bookID,String memberID,double depreciation){
		LocalDate today=LocalDate.now();
		try {
			if(!statement.execute("INSERT INTO Bookstore.Sells (book_ID,member_ID,depreciation,status,deadline,dateOfCreate,dateOfComplete) "
					+ " VALUES("+bookID+",'"+memberID+"',"+depreciation+",0,'"+today.plusDays(7L)+"','"+today+"',null)")) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	public boolean cancel(int bookID,int memberID){
		try {
			if(!statement.execute("DELETE FROM Sells WHERE book_ID="+bookID+" AND memberID='"+memberID+"')")) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
}