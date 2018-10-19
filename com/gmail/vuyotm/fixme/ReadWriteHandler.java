package com.gmail.vuyotm.fixme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.CompletionHandler;

public class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {

    @Override
    public void completed(Integer result, Attachment attachment) {

        if (attachment.isRead()) {
            int     limits;
            byte[]  bytes;
            String  routerResponse;
            String  brokerRequest;
            String  userInput;
            byte[]  byteBrokerRequest;

            attachment.getBuffer().flip();
            limits = attachment.getBuffer().limit();
            bytes = new byte[limits];
            attachment.getBuffer().get(bytes, 0, limits);
            routerResponse = new String(bytes);
            System.out.println(routerResponse);

            userInput = "";
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                userInput = bufferedReader.readLine();
            }
            catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (userInput.equalsIgnoreCase("q")) {
                attachment.getMainThread().interrupt();
                return ;
            }

            attachment.getBuffer().clear();
            brokerRequest = ;
            byteBrokerRequest = brokerRequest.getBytes();
            attachment.getBuffer().put(byteBrokerRequest);
            attachment.getBuffer().flip();
            attachment.setRead(false);
            attachment.getClientChannel().write(attachment.getBuffer(), attachment, this);
        }
        else {
            attachment.setRead(true);
            attachment.getBuffer().clear();
            attachment.getClientChannel().read(attachment.getBuffer(), attachment, this);
        }

    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        exc.printStackTrace();
    }

}
