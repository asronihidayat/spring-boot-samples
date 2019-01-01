package com.spring.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.spring.grpc.helloworld.GreeterGrpc;
import io.spring.grpc.helloworld.HelloReply;
import io.spring.grpc.helloworld.HelloRequest;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ClientMain {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public ClientMain(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    ClientMain(ManagedChannel channel){
        this.channel = channel;
        this.blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void  greet(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        Iterator<HelloReply> response;
        try {

            response = blockingStub.sayHello(request);
            while (response.hasNext()){
                System.out.println("message : " + response.next().getMessage());
            }
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return;
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ClientMain clientMain = new ClientMain("localhost", 8080);
        try {
            clientMain.greet("Roni:Egi:Frizky:Rendra");
        }finally {
            clientMain.shutdown();
        }
    }
}
