package com.demo.alipay;

import com.demo.alipay.types.Book;
import com.demo.alipay.types.BookDetail;
import com.demo.alipay.types.PostBookReviewRequestBody;
import com.demo.alipay.types.Review;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IndexHandler implements HttpHandler {
    static private final Gson gson = new Gson();
    static private final String LOG_ROOT_PATH = Utils.sanitizePath(Utils.valueOr(System.getenv("LOG_ROOT_PATH"), "/home/admin/logs"));

    public void handle(HttpExchange t) {
        try {
            String path = t.getRequestURI().getPath();
            if ("/books".equals(path) || "/books/".equals(path)) {
                handleBookList(t);
            } else if (path.startsWith("/books/")) {
                int id = Integer.parseInt(path.substring("/books/".length()));

                String requestBody = Utils.readStreamAsString(t.getRequestBody(), StandardCharsets.UTF_8);
                if (requestBody.length() > 0) {
                    handlePostBookReview(t, id, requestBody);
                } else {
                    handleBookDetail(t, id);
                }
            } else {
                respond(t, "BookInfo server online");
            }
            writeLog(t);
        } catch (Throwable e) {
            try {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                respond(t, errors.toString(), 500);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void handlePostBookReview(HttpExchange t, int id, String requestBody) throws Exception {

        PostBookReviewRequestBody parsed = gson.fromJson(requestBody,
            PostBookReviewRequestBody.class);

        String sql = "insert into reviews (book_id, name, score, date, content, namespace) values (?, ?, ?, ?, ?, ?)";

        try (Connection connection = HikariCPDataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, parsed.getName());
            statement.setInt(3, parsed.getScore());
            statement.setString(4, Utils.getDateString());
            statement.setString(5, parsed.getContent());
            statement.setString(6, Utils.getNamespace());
            statement.execute();
        } catch (Throwable e) {
            respond(t, "{\"success\":\"false\"}");
            throw e;
        }

        respond(t, "{\"success\":\"true\"}");
    }

    private void handleBookDetail(HttpExchange t, int id) throws Exception {
        BookDetail bookDetail = null;
        String sql = "select * from books where id=?";

        try (Connection connection = HikariCPDataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                bookDetail = BookDetail.fromResultSet(resultSet);

            } catch (Throwable e) {
                throw e;
            }
        } catch (Throwable e) {
            throw e;
        }

        sql = "select * from reviews where book_id=? and namespace=? order by id desc";
        ArrayList<Review> reviews = new ArrayList<>();
        try (Connection connection = HikariCPDataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, Utils.getNamespace());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(Review.fromResultSet(resultSet));
                }
            } catch (Throwable e) {
                throw e;
            }
        } catch (Throwable e) {
            throw e;
        }

        bookDetail.setReviews(reviews.toArray(new Review[0]));
        respond(t, gson.toJson(bookDetail));

    }

    private void handleBookList(HttpExchange t) throws Exception {
        ArrayList<Book> books = new ArrayList<>();
        String sql
            = "select books.*, count(reviews.id) as reviewCount, avg(reviews.score) as avgReviewScore from books left"
            + " join reviews on books.id = reviews.book_id and reviews.namespace=? group by books.id "
            + "order by books.id desc;";

        try (Connection connection = HikariCPDataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Utils.getNamespace());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(Book.fromResultSet(resultSet));
                }
            } catch (Throwable e) {
                throw e;
            }
        } catch (Throwable e) {
            throw e;
        }

        respond(t, gson.toJson(books));
    }

    private void writeLog(HttpExchange t) throws Exception {
        File file = new File(LOG_ROOT_PATH + "/access.log");
        FileWriter fr = new FileWriter(file, true);
        fr.write(String.format("[%s] %s %s\n", Utils.getDateString(), getIpAddress(t), t.getRequestURI().getPath()));
        fr.close();
    }

    private String getIpAddress(HttpExchange t) {
        List<String> forwardedFor = t.getRequestHeaders().get("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()
            && forwardedFor.get(0) != null && !forwardedFor.get(0).equals("")) {
            String ff = forwardedFor.get(0);
            if (ff.contains(",")) {
                return ff.substring(0, ff.indexOf(","));
            }
            return ff;
        }
        return t.getRemoteAddress().getAddress().getHostAddress();
    }

    private void respond(HttpExchange t, String response) throws Exception {
        respond(t, response, 200);
    }

    private void respond(HttpExchange t, String response, int statusCode) throws Exception {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        t.getResponseHeaders().add("Content-Type", "application/json");
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        t.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        t.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

        t.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = t.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
