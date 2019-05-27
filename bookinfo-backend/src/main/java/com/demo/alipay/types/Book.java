package com.demo.alipay.types;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    private int id;
    private String title;
    private String author;
    private String image;
    private String description;
    private Integer reviewCount;
    private Float avgReviewScore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Float getAvgReviewScore() {
        return avgReviewScore;
    }

    public void setAvgReviewScore(Float avgReviewScore) {
        this.avgReviewScore = avgReviewScore;
    }

    public static Book fromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setImage(resultSet.getString("image"));
        book.setDescription(resultSet.getString("description"));
        book.setReviewCount(resultSet.getInt("reviewCount"));
        book.setAvgReviewScore(resultSet.getFloat("avgReviewScore"));
        return book;
    }
}
