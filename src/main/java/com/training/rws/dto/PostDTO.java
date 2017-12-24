package com.training.rws.dto;

import java.util.Date;

public class PostDTO {

    private int id;
    private int userId;
    private Date date;
    private String title;
    private String content;

    public PostDTO() {}

    public PostDTO(int id, int userId, Date date, String title, String content) {
        this.setId(id);
        this.setUserId(userId);
        this.setDate(date);
        this.setTitle(title);
        this.setContent(content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
