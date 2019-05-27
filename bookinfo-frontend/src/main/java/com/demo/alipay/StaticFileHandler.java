package com.demo.alipay;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StaticFileHandler implements HttpHandler {

    private byte[] content;
    private String contentType;

    public StaticFileHandler(String fileName) throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        content = Utils.readStreamAsString(resourceAsStream, StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);

        contentType = "text/plain; charset=utf-8";

        if (fileName.endsWith(".js")) {
            contentType = "text/javascript; charset=UTF-8";
        } else if (fileName.endsWith(".css")) {
            contentType = "text/css";
        }
    }

    public void handle(HttpExchange t) throws IOException {
        t.getResponseHeaders().add("Content-Type", contentType);
        t.sendResponseHeaders(200, content.length);
        OutputStream os = t.getResponseBody();
        os.write(content);
        os.close();
    }
}
