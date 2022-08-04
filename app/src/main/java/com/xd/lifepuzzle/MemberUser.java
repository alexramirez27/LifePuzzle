package com.xd.lifepuzzle;

public class MemberUser {

    public String name, age, email, phoneNo, gender, uniqueID;
    public Member member;

    public MemberUser(){

    }

    public MemberUser(String name, String age, String email, String phoneNo, String gender, String uniqueID) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.uniqueID = uniqueID;
    }

    public MemberUser(String name, String age, String email, String phoneNo, String gender, String uniqueID, Member member) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.uniqueID = uniqueID;
        this.member = member;
    }

}
