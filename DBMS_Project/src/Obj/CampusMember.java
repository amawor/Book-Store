package Obj;

public class CampusMember extends Member{
	private String schoolID;
	public CampusMember(String memberID,String password,String name,String mail,String phone,int memberType,String SchoolID) {
		super(memberID,password,name,mail,phone,memberType);
		this.schoolID=schoolID;
	}
	public String getMemberID() {
		return super.getMemberID();
	}
	public String getSchoolID() {
		return schoolID;
	}
}
