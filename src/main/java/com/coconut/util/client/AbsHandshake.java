package com.coconut.util.client;

import org.apache.mina.core.session.IoSession;

import com.changwan.game.service.Context;

import com.changwan.game.network.AbsHandler;
import com.coconut.protocol.client.Client.*;

public abstract class AbsHandshake extends AbsHandler {
	public AbsHandshake(Context context) {
		super(context);
	}
	public void syn(IoSession session, CsHandShake csHandShake) {}
	public void ack(IoSession session, ScHandShake scHandShake) {}
}