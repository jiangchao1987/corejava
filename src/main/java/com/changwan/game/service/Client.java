package com.changwan.game.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.util.SessionAttributeInitializingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.changwan.game.network.ClientProtocolHandler;
import com.changwan.game.network.CoconutClientProtocolFilter;
import com.changwan.game.network.MessageCodecFactory;
import com.jiangchao.core.mina.ClientContext;

public class Client {
	private ClientProtocolHandler clientProtocolHandler = new ClientProtocolHandler();

	protected IoConnector clientConnector;
	protected Context clientContext;

	public Client() {
		clientContext = new ClientContext(clientConnector);
	}

	public IoConnector getClientConnector() {
		return clientConnector;
	}
	
	public Context getClientContext() {
		return clientContext;
	}

	public com.coconut.util.client.MessageDispatcher getClientDispatcher() {
		return clientProtocolHandler.getDispatcher();
	}
	
	public ClientProtocolHandler getClientProtocolHandler() {
		return clientProtocolHandler;
	}


	public void start() throws UnknownHostException, IOException {
		NioSocketConnector clientConnector = new NioSocketConnector();
		clientConnector.setHandler(clientProtocolHandler);
		DefaultIoFilterChainBuilder chain = clientConnector.getFilterChain();
		HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
		sessionAttr.put("sendPos", 0);
		sessionAttr.put("recvPos", 0);
		sessionAttr.put("handshaked", false);
		chain.addLast("session.initialize", new SessionAttributeInitializingFilter(sessionAttr));
		// concatenate messages and pack them as an CoconutMessage Object
		chain.addLast("codec", new ProtocolCodecFilter(new MessageCodecFactory()));
		chain.addLast("coconut", new CoconutClientProtocolFilter());
		
		clientConnector.connect(new InetSocketAddress("niuniu.g.candou.com" , 10001));
	}
}
