package com.changwan.game.service;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;

public class Context {
	protected IoAcceptor ioAcceptor;
	protected IoConnector ioConnector;
	
	public Context(IoAcceptor ioAcceptor) {
		this.ioAcceptor = ioAcceptor;
	}
	
	public Context(IoConnector ioConnector) {
		this.ioConnector = ioConnector;
	}

	public IoConnector getConnector() {
		return ioConnector;
	}

	public IoAcceptor getAcceptor() {
		return ioAcceptor;
	}

}
