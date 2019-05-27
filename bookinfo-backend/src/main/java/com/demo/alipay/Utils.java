package com.demo.alipay;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Utils {

    private static String namespaceCache = null;
    private static final Gson gson = new Gson();

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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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

    public static String getNamespace() {
        if (namespaceCache != null) {
            return namespaceCache;
        }
        String sasContextText = Utils.valueOr(System.getenv("SOFASTACK_SAS_CONTEXT"), "{}");
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        HashMap<String, String> sasContext = gson.fromJson(sasContextText, type);
        namespaceCache = Utils.valueOr(sasContext.get("clusterName"), "") + "-"
            + Utils.valueOr(sasContext.get("serverlessAppServiceName"), "");
        return namespaceCache;
    }
}

