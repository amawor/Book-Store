package Obj;

public class Order {
	private int orderID,total;
	private String status, dateOfCreate, dateOfComplete,memberID;
	public Order(int orderID,int total,String status,String dateOfCreate,String dateOfComplete,String memberID) {
		this.orderID=orderID;
		this.total=total;
        this.status=status;
        this.dateOfComplete=dateOfComplete;
        this.dateOfCreate=dateOfCreate;
        this.memberID=memberID;
	}
	public int getID() {
		return orderID;
	}
	public int getTotal() {
        return total;
    }

    public String getDateOfComplete() {
        return dateOfComplete;
    }

    public String getDateOfCreate() {
        return dateOfCreate;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getStatus() {
        return status;
    }

}
