package com.changwan.game.network;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;

import com.coconut.protocol.client.Client.CoconutMessage;
import com.coconut.util.client.MessageDispatcher;
import com.google.protobuf.InvalidProtocolBufferException;

public class CoconutClientProtocolFilter extends IoFilterAdapter {
	public void filterWrite(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) {
		nextFilter.filterWrite(session, new DefaultWriteRequest(MessageDispatcher.serialize(writeRequest.getMessage())));
	}

	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) {
		try {
			nextFilter.messageReceived(session, CoconutMessage.parseFrom((byte[]) message));
		} catch (InvalidProtocolBufferException e) {
			System.out.println(e.getMessage());
		}
	}
}
