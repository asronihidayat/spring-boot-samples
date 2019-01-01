package com.spring.grpc;

import io.grpc.stub.StreamObserver;
import io.spring.grpc.helloworld.GreeterGrpc;
import io.spring.grpc.helloworld.HelloReply;
import io.spring.grpc.helloworld.HelloRequest;

public class GreaterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String[] name = request.getName().split(":");
        for (int i = 0; i < name.length ; i++) {
            String greeting = new StringBuilder()
                    .append("Hello, ")
                    .append(name[i])
                    .toString();

            HelloReply response = HelloReply.newBuilder()
                    .setMessage(greeting)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}
