package com.jiangchao.core.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;

import com.changwan.game.service.Context;

public class ClientContext extends Context {

	public ClientContext(IoConnector clientConnector) {
		super(clientConnector);
	}

}
