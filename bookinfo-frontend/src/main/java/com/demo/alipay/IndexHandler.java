package com.demo.alipay;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import okhttp3.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IndexHandler implements HttpHandler {
    static private final String LOG_ROOT_PATH = Utils.sanitizePath(Utils.valueOr(System.getenv("LOG_ROOT_PATH"), "/home/admin/logs"));
    static private final String BACKEND_URL = Utils.sanitizeUrl(Utils.sanitizePath(Utils.valueOr(System.getenv("BACKEND_URL"), "")));

    public void handle(HttpExchange t) {

        if(BACKEND_URL.isEmpty()){
            try {
                respondWithError(t, "Please Provide BACKEND_URL in Environment Variables");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            String path = t.getRequestURI().getPath();
            if (path.equals("/")) {
                handleIndex(t);
            } else if (path.startsWith("/book/")) {
                int id = Integer.parseInt(path.substring("/book/".length()));

                String requestBody = Utils.readStreamAsString(t.getRequestBody(), StandardCharsets.UTF_8);
                if (requestBody.length() > 0) {
                    handlePostBookReview(t, id, requestBody);
                } else {
                    handleBookDetail(t, id);
                }
            }

            writeLog(t);
        } catch (Throwable e) {
            try {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                respondWithError(t, errors.toString());
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void handlePostBookReview(HttpExchange t, int id, String requestBody) throws Exception {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, requestBody);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(BACKEND_URL + "/books/" + id)
            .post(body)
            .build();

        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            respond(t, response.body().string());
        } else {
            respondWithError(t, backendError(response.body().string()));
        }
    }

    private void handleBookDetail(HttpExchange t, int id) throws Exception {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("detail.html");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(BACKEND_URL + "/books/" + id)
            .get()
            .build();

        Response data = client.newCall(request).execute();
        if (data.code() == 200) {
            String response = Utils.readStreamAsString(resourceAsStream, StandardCharsets.UTF_8);
            respond(t, response.replace("___CONTENTS___", data.body().string()));
        } else {
            respondWithError(t, backendError(data.body().string()));
        }
    }

    private void handleIndex(HttpExchange t) throws Exception {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("index.html");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(BACKEND_URL + "/books/")
            .get()
            .build();

        Response data = client.newCall(request).execute();
        if (data.code() == 200) {
            String response = Utils.readStreamAsString(resourceAsStream, StandardCharsets.UTF_8);
            respond(t, response.replace("___CONTENTS___", data.body().string()));
        } else {
            respondWithError(t, backendError(data.body().string()));
        }
    }

    private void writeLog(HttpExchange t) throws IOException {
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

    private void respondWithError(HttpExchange t, String string) throws Exception {
        respond(t, "<code style='color:red;white-space:pre-wrap'>" + string + "</code>", 500);
    }

    private String backendError(String string) {
        return "<div><b>Backend Error:</b></div><hr/>" + string;
    }

    private void respond(HttpExchange t, String response, int statusCode) throws Exception {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        t.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
        t.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = t.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}
