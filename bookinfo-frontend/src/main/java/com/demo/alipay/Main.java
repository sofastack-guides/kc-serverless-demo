package com.demo.alipay;

import com.sun.net.httpserver.HttpServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedWriter writer = new BufferedWriter(
            new FileWriter("/etc/hosts", true)
        );
        writer.newLine();
        writer.write(
            "139.224.240.157 " + Utils.rmProtocol(Utils.sanitizePath(Utils.valueOr(System.getenv("BACKEND_URL"), "")))
        );
        writer.newLine();
        writer.close();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new IndexHandler());
        server.createContext("/detail.js", new StaticFileHandler("detail.js"));
        server.createContext("/index.js", new StaticFileHandler("index.js"));
        server.createContext("/index.css", new StaticFileHandler("index.css"));
        server.setExecutor(null);

        server.start();

        System.out.printf("Server serving at :8080\n");

        while (true) {
            Thread.sleep(100);
        }
    }
}
