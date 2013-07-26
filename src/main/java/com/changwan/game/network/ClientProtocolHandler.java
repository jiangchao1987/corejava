package com.changwan.game.network;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.coconut.protocol.client.Client.CoconutMessage;
import com.coconut.util.client.MessageDispatcher;
import com.google.protobuf.InvalidProtocolBufferException;

public class ClientProtocolHandler extends IoHandlerAdapter {
	private MessageDispatcher dispatcher = new MessageDispatcher();
	
	@Override
	public void messageReceived(IoSession session, Object message) {
		CoconutMessage msg = (CoconutMessage) message;
		try {
			dispatcher.dispatcher(session, msg);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
	public MessageDispatcher getDispatcher() {
		return dispatcher;
	}
}
