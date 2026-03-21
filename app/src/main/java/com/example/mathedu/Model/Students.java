package com.example.mathedu.Model;

public class Students {
    public String hoten;
    public String password;
    public String lop;
    public String avatar;
    public int CurrentStar;
    public String Status;
    public int CompletedLessonCount;
    public String UserID;

    public Students() {
    }

    public Students(String hoten, String password, String lop, String avatar, int currentStar, String status, String userID, int completedLessonCount) {
        this.hoten = hoten;
        this.password = password;
        this.lop = lop;
        this.avatar = avatar;
        CurrentStar = currentStar;
        Status = status;
        UserID = userID;
        CompletedLessonCount = completedLessonCount;
    }

   }