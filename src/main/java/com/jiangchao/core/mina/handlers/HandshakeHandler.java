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
//		context.getConnector();	当然在这里一般用不到Connector和Acceptor，这里仅仅是为了说明通过适当扩展ClientContext，就能拿到你想要的一切。
//		context.getAcceptor();
	}

}
