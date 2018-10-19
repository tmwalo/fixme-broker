package com.gmail.vuyotm.fixme;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        AsynchronousSocketChannel   clientChannel;
        Future<Void>                result;
        Attachment                  attachment;

        clientChannel = AsynchronousSocketChannel.open();
        result = clientChannel.connect(new InetSocketAddress("localhost", 5000));
        result.get();
        System.out.println("Connected to router");
        attachment = new Attachment();
        attachment.setClientChannel(clientChannel);
        attachment.setBuffer(ByteBuffer.allocate(2048));
        attachment.setRead(false);
        attachment.setBrokerIdSet(false);
        attachment.setMainThread(Thread.currentThread());

        attachment.getMainThread().join();

    }

}
