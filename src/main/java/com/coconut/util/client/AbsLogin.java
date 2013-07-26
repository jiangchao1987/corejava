package com.coconut.util.client;

import org.apache.mina.core.session.IoSession;

import com.changwan.game.service.Context;

import com.changwan.game.network.AbsHandler;
import com.coconut.protocol.client.Client.*;

public abstract class AbsLogin extends AbsHandler {
	public AbsLogin(Context context) {
		super(context);
	}
	public void guest(IoSession session, CsGuestLogin csGuestLogin) {}
	public void openid(IoSession session, CsOpenidLogin csOpenidLogin) {}
	public void result(IoSession session, ScLoginResult scLoginResult) {}
}