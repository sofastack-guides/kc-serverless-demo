package com.demo.alipay;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new IndexHandler());
        server.setExecutor(null);

        server.start();

        System.out.printf("Server serving at :8080\n");

        while (true) {
            Thread.sleep(100);
        }
    }
}
