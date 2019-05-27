package com.demo.alipay.types;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Review {
    private String name;
    private String content;
    private String date;
    private Float score;

    public static Review fromResultSet(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setName(resultSet.getString("name"));
        review.setContent(resultSet.getString("content"));
        review.setDate(resultSet.getString("date"));
        review.setScore(resultSet.getFloat("score"));
        return review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
