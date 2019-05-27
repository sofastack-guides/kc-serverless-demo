package com.demo.alipay.types;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDetail extends Book {
    Review[] reviews = null;

    public Review[] getReviews() {
        return reviews;
    }

    public void setReviews(Review[] reviews) {
        this.reviews = reviews;
    }

    public static BookDetail fromResultSet(ResultSet resultSet) throws SQLException {
        BookDetail book = new BookDetail();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setImage(resultSet.getString("image"));
        book.setDescription(resultSet.getString("description"));
        return book;
    }
}
