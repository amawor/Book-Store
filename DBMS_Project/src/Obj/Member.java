package Obj;

import java.io.FileNotFoundException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import connection_sql.DataSetter;

public class Member {
    private String memberID,password,name,mail,phone;
    private int memberType;
    public Member(String memberID,String password,String name,String mail,String phone,int memberType) {
        this.memberID=memberID;
        this.password=password;
        this.name=name;
        this.mail=mail;
        this.phone=phone;
        this.memberType=memberType;
    }
    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMemberID() {
        return memberID;
    }

    public int getMemberType() {
        return memberType;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
