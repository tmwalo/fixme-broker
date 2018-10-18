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
            String  msg;
            byte[]  data;

            attachment.getBuffer().flip();
            limits = attachment.getBuffer().limit();
            bytes = new byte[limits];
            attachment.getBuffer().get(bytes, 0, limits);
            msg = new String(bytes);
            System.out.println("Server responded: " + msg);
            try {
                msg = getTextFromUser();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if (msg.equalsIgnoreCase("bye")) {
                attachment.getMainThread().interrupt();
                return ;
            }
            attachment.getBuffer().clear();
            data = msg.getBytes();
            attachment.getBuffer().put(data);
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

    private String getTextFromUser() throws IOException {
        BufferedReader  bufferedReader;
        String          msg;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        msg = bufferedReader.readLine();
        return (msg);
    }

}
