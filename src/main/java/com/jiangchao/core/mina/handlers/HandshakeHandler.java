package com.jiangchao.core.mina.handlers;

import org.apache.mina.core.session.IoSession;

import com.changwan.game.service.Context;
import com.coconut.protocol.client.Client.ScHandShake;
import com.coconut.util.client.AbsHandshake;

public class HandshakeHandler extends AbsHandshake {

	public HandshakeHandler(Context context) {
		super(context);
	}

	@Override
	public void ack(IoSession session, ScHandShake scHandShake) {
//		context.getConnector();
//		context.getAcceptor();
	}

}
