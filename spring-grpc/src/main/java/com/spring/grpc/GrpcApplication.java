package com.spring.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GrpcApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(GrpcApplication.class, args);
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new GreaterImpl()).build();
        server.start();
        server.awaitTermination();
    }
}

