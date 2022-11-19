package Obj;

public class NormalMember extends Member{
	public NormalMember(String memberID,String password,String name,String mail,String phone,int memberType) {
		super(memberID,password,name,mail,phone,memberType);
	}
	public String getMemberID() {
		return super.getMemberID();
	}
}
