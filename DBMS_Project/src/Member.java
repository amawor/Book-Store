import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import connection_sql.DataGetter;
import connection_sql.DataSetter;

public class Member {
	private Boolean status;
	private String sid="";
	private String username;
	private String password;
	private Obj.Member member;

	public Member() {
		status = false;
		member=null;
	}

	public boolean login(String username, String password,Statement statement) {
		try {
			if (username.equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(null, "The username or password can't be null!", "Notification",
						JOptionPane.WARNING_MESSAGE);
				return status;
			} else {
				member=findUser(username,password,statement);
				if (member != null) {
			
				JOptionPane.showMessageDialog(null, "Login successfully!", "Notification", JOptionPane.WARNING_MESSAGE);
				status = true;
				if(member.getMemberType()==1)sid=new DataGetter(statement).getStudentID(member.getMemberID());
				this.username=member.getMemberID();
				this.password=member.getPassword();
				return status;
				}else {
					JOptionPane.showMessageDialog(null, "The username or password is incorrect, please try again!", "Notification",
							JOptionPane.WARNING_MESSAGE);
					return status;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The username or password is incorrect, please try again!", "Notification",
					JOptionPane.WARNING_MESSAGE);
			return status;
		}
		
	}

	public void logout() {
		status = false;
		sid = null;
		username = null;
		password = null;
		member=null;
		LoginFrame.MEMBER=null;
	}

	public boolean verify(String username,Statement statement) throws FileNotFoundException {
		try {
			if (findUser(username,statement) == null) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "The username has existed, please try another one.", "Notification",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (NoSuchElementException e1) {
			JOptionPane.showMessageDialog(null, "The username or password can't be null!", "Notification",
					JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}

	public boolean create(String sid, String username, String password,Statement statement)  {
		System.out.print(sid);
		DataSetter dataSetter=new DataSetter(statement);
		if(sid.trim().equals("")) {
			if(dataSetter.createNormalMember(new Obj.Member(username,password,null,null,null,0)))
				return true;
			else
				return false;
		}else {
			if(dataSetter.createCampusMember(new Obj.Member(username,password,null,null,null,1),sid))
				return true;
			else
				return false;
		}
		
		
	}


	public Obj.Member findUser(String username,String password,Statement statement) throws IOException, SQLException {
		member=new  DataGetter(statement).findMember(username);
		if(member==null) {
			return null;
		}else if(member.getPassword().trim().equals(password.trim())) {
			return member;
		}else {
			return null;
		}
		
	}
	public Obj.Member findUser(String username,Statement statement) throws IOException, SQLException {
		return new  DataGetter(statement).findMember(username);
		
	}


	public String getSID() {
		return sid;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Boolean getStatus() {
		return status;
	}
	
	public Obj.Member getMember(){
		return member;
	}
}
