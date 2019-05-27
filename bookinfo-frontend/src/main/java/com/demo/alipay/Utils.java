package com.demo.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String valueOr(String value, String s) {
        if (value == null || value.equals("")) {
            return s;
        }
        return value;
    }

    public static String readStreamAsString(InputStream inputStream, Charset charset) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }

    public static String getDateString() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    public static String sanitizePath(String url) {
        if (url == null) {
            return url;
        }
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    public static String sanitizeUrl(String url) {
        if (url == null || url.contains("://")) {
            return url;
        }
        return "http://" + url;
    }

    public static String rmProtocol(String url) {
        if (!url.contains("://")) {
            return url;
        }
        return url.substring(url.indexOf("://") + 3);
    }
}

