package Obj;

public class Sells {
	private int bookID;
	private String memberID,status,deadline,dateOfCreate,dateOfComplete;
	private double depreciation;
	public Sells(int bookID,String memberID,double depreciation,String status,String deadline,String dateOfCreate,String dateOfComplete) {
		this.bookID=bookID;
		this.memberID=memberID;
		this.depreciation=depreciation;
		this.status=status;
		this.deadline=deadline;
		this.dateOfCreate=dateOfCreate;
		this.dateOfComplete=dateOfComplete;
	}
	public int getBookID() {
		return bookID;
	}
	public String getMemberID() {
		return memberID;
	}
	public double getDepreciation() {
		return depreciation;
	}
	public String getStatus() {
		return status;
	}
	public String deadline() {
		return deadline;
	}
	public String dateOfCreate() {
		return dateOfCreate;
	}
	public String dateOfComplete() {
		return dateOfComplete;
	}
}
