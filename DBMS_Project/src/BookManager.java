import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import connection_sql.DataGetter;

public class BookManager {
	private ArrayList<Obj.Book> books;

	public BookManager() {
		books = new ArrayList<>();
	}

	
	
	public void addNewBook(Obj.Book book) {
		books.add(book);
	}
	
	public ArrayList<Obj.Book> getBooks() {
		return books;
	}
	
	public Obj.Book findBook(int bookID,Statement statement) throws SQLException {
		Obj.Book book=null;
		ResultSet resultSet = statement.executeQuery("SELECT * FROM Book AS B WHERE book_ID="+bookID+" limit 1");
		while (resultSet.next()) {			
			book=new Obj.Book(resultSet.getString("book_ID"),resultSet.getString("book_name").trim(),resultSet.getString("ISBN"),resultSet.getString("author"),resultSet.getString("publish_Date"),resultSet.getString("publisher"),resultSet.getDouble("origin_Price"),resultSet.getString("book_ID"));
		}
		return book;
	}
	public ArrayList<Obj.Book> findBook(String keyword,Statement statement) throws SQLException{
		DataGetter dataGetter=new DataGetter(statement);
		ArrayList<Obj.Book> books=new ArrayList();
		  if(isNumeric(keyword)) {
			  if(keyword.length()==13) {
				  books.add(dataGetter.getBookByISBN(keyword));
				  System.out.println(books.get(0).getBookName());
			  }else if(keyword.length()==9){
				  books=dataGetter.getBookByCourseID(keyword);
				  for(int i=0;i<books.size();i++) {
					  System.out.println(books.get(i).getBookName());
				  }
			  }else {
				  books=dataGetter.getBookByName(keyword);
				  for(int i=0;i<books.size();i++) {
					  System.out.println(books.get(i).getBookName()+" "+books.get(i).getCourseName()+" "+books.get(i).getTeacher()+" "+books.get(i).getSemester());
				  }
			  }
		  }else {
			  books=dataGetter.getBookByName(keyword);
			  for(int i=0;i<books.size();i++)
				  System.out.println(books.get(i).getBookName()+" "+books.get(i).getCourseName()+" "+books.get(i).getTeacher()+" "+books.get(i).getSemester());			  
		  }
		  return books;
	}
	public static boolean isNumeric(String keyword) {
		Pattern pattern=Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
		String bigStr;
		try {
			bigStr=new BigDecimal(keyword).toString();
		}catch (Exception e){
			return false;
		}
		Matcher isNum=pattern.matcher(bigStr);
		if(!isNum.matches()) {
			return false;
		}else {
			return true;
		}
	}
}
