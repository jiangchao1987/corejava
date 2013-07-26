package com.jiangchao.core.mina;

import java.io.IOException;
import java.net.UnknownHostException;

import com.changwan.game.service.Client;
import com.jiangchao.core.mina.handlers.HandshakeHandler;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		try {
			client.getClientDispatcher().register(new HandshakeHandler(client.getClientContext()));
			client.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
