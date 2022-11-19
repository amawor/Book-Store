package Obj;

import java.util.ArrayList;

public class Book {
	private String bookID,bookName,ISBN,author,publicDate,publisher,language,courseName,teacher;
	private double originPrice;
	private int semester;
	//public ArrayList<Book> Books=new ArrayList();
	public Book(String bookID, String bookName,String ISBN, String author,String publicDate,String publisher,double originPrice,String language) {
		this.bookID=bookID;
		this.bookName=bookName;
		this.ISBN=ISBN;
		this.author=author;
		this.publicDate=publicDate;
		this.publisher=publisher;
		this.originPrice=originPrice;
		this.language=language;
	}
	public Book(String bookID, String bookName,String ISBN, String author,String publicDate,String publisher,double originPrice,String language,String courseName,String teacher,int semester) {
		this.bookID=bookID;
		this.bookName=bookName;
		this.ISBN=ISBN;
		this.author=author;
		this.publicDate=publicDate;
		this.publisher=publisher;
		this.originPrice=originPrice;
		this.language=language;
		this.courseName=courseName;
		this.teacher=teacher;
		this.semester=semester;
	}
	public String getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public String getPublisher() {
    	return publisher;
    }
    
    public double getOriginPrice() {
        return originPrice;
    }

    public String getLanguage() {
        return language;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getTeacher() {
        return teacher;
    }
    public int getSemester() {
        return semester;
    }
}
