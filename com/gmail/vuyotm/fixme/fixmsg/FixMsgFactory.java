package com.gmail.vuyotm.fixme.fixmsg;

public abstract class FixMsgFactory {

    public static FixMsgOrder newOrderMsg(String senderCompId, String targetCompId, String clOrdId, String handlInst, String tickerSymbol, String side, int orderQty, String orderType) {

        FixMsgHeader    fixMsgHeader;
        FixMsgTrailer   fixMsgTrailer;
        FixMsgOrder     fixMsgOrder;

        fixMsgHeader = new FixMsgHeader(senderCompId, targetCompId);
        fixMsgTrailer = new FixMsgTrailer();
        fixMsgOrder = new FixMsgOrder(fixMsgHeader, fixMsgTrailer, clOrdId, handlInst, tickerSymbol, side, orderQty, orderType);
        return (fixMsgOrder);

    }

    public static FixMsgExecute newExecMsg(String senderCompId, String targetCompId, String orderId, String execId, String execTransType, String orderStatus, String tickerSymbol, String side, int orderQty, int cumQty, float avgPx) {

        FixMsgHeader    fixMsgHeader;
        FixMsgTrailer   fixMsgTrailer;
        FixMsgExecute   fixMsgExecute;

        fixMsgHeader = new FixMsgHeader(senderCompId, targetCompId);
        fixMsgTrailer = new FixMsgTrailer();
        fixMsgExecute = new FixMsgExecute(fixMsgHeader, fixMsgTrailer, orderId, execId, execTransType, orderStatus, tickerSymbol, side, orderQty, cumQty, avgPx);
        return (fixMsgExecute);

    }

    public static FixMsgReject newRejectMsg(String senderCompId, String targetCompId, String refSeqNum) {

        FixMsgHeader    fixMsgHeader;
        FixMsgTrailer   fixMsgTrailer;
        FixMsgReject    fixMsgReject;

        fixMsgHeader = new FixMsgHeader(senderCompId, targetCompId);
        fixMsgTrailer = new FixMsgTrailer();
        fixMsgReject = new FixMsgReject(fixMsgHeader, fixMsgTrailer, refSeqNum);
        return (fixMsgReject);

    }

}
