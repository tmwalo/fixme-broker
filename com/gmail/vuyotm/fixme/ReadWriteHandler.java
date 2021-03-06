package com.gmail.vuyotm.fixme;

import com.gmail.vuyotm.fixme.fixmsg.BrokerRequestCreation;
import com.gmail.vuyotm.fixme.validation.BrokerInputValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.CompletionHandler;

public class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {

    @Override
    public void completed(Integer result, Attachment attachment) {

        if (attachment.isRead()) {
            int                     limits;
            byte[]                  routerRespBytes;
            String                  routerResp;
            String                  brokerRequest;
            byte[]                  byteBrokerRequest;

            attachment.getBuffer().flip();
            limits = attachment.getBuffer().limit();
            routerRespBytes = new byte[limits];
            attachment.getBuffer().get(routerRespBytes, 0, limits);
            routerResp = new String(routerRespBytes);
            if (!attachment.isBrokerIdSet()) {
                BrokerData.setBrokerId(routerResp);
                attachment.setBrokerIdSet(true);
                System.out.print("Broker Id: ");
            }
            System.out.println(routerResp);

            brokerRequest = getBrokerRequest();

            if (BrokerInputValidation.isQuit(brokerRequest)) {
                attachment.getMainThread().interrupt();
                return ;
            }

            attachment.getBuffer().clear();
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

    public String getBrokerRequest() {
        String                  brokerRequest;
        String                  userInput;
        BrokerRequestCreation   brokerRequestCreation;

        while (true) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                userInput = bufferedReader.readLine();
                brokerRequestCreation = new BrokerRequestCreation(userInput);
                brokerRequest = brokerRequestCreation.createBrokerRequest();
                break ;
            }
            catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return (brokerRequest);
    }

}
