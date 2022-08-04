package com.xd.lifepuzzle;

public class User {

    public String name, age, email, phoneNo, gender, uniqueID;
    public Member member;

    public User(){

    }

    public User(String name, String age, String email, String phoneNo, String gender, String uniqueID) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.uniqueID = uniqueID;
    }

    public User(String name, String age, String email, String phoneNo, String gender, String uniqueID, Member member) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.uniqueID = uniqueID;
        this.member = member;
    }


}
