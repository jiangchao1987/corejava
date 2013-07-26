package com.coconut.util.client;

import org.apache.mina.core.session.IoSession;

import com.coconut.protocol.client.Client.*;
import com.google.protobuf.*;
import com.coconut.protocol.client.Client.CoconutMessage;
import com.google.protobuf.InvalidProtocolBufferException;

public class MessageDispatcher {
	private AbsHandshake handshake;

	private AbsLogin login;

	public void register(AbsHandshake p) {
		handshake = p;
	}

	public void register(AbsLogin p) {
		login = p;
	}

	public void dispatcher(IoSession session, CoconutMessage msg) throws InvalidProtocolBufferException {
		if (msg.getRoute().equals("handshake.syn") || msg.getRoute().equals("handshake")) {
			CsHandShake csHandShake = CsHandShake.parseFrom(msg.getBody());
			if (handshake != null) handshake.syn(session, csHandShake);
			return ;
		}
		if (msg.getRoute().equals("handshake.ack")) {
			ScHandShake scHandShake = ScHandShake.parseFrom(msg.getBody());
			if (handshake != null) handshake.ack(session, scHandShake);
			return ;
		}
		if (msg.getRoute().equals("login.guest")) {
			CsGuestLogin csGuestLogin = CsGuestLogin.parseFrom(msg.getBody());
			if (login != null) login.guest(session, csGuestLogin);
			return ;
		}
		if (msg.getRoute().equals("login.openid")) {
			CsOpenidLogin csOpenidLogin = CsOpenidLogin.parseFrom(msg.getBody());
			if (login != null) login.openid(session, csOpenidLogin);
			return ;
		}
		if (msg.getRoute().equals("login.result")) {
			ScLoginResult scLoginResult = ScLoginResult.parseFrom(msg.getBody());
			if (login != null) login.result(session, scLoginResult);
			return ;
		}
	}

	public static byte[] serialize(Object message) {
		ByteString body = null;
		String route = null;
		do {
			if (CsHandShake.class.isInstance(message)) {
				body = ((CsHandShake) message).toByteString();
				route = "handshake.syn";
				break;
			}
			if (ScHandShake.class.isInstance(message)) {
				body = ((ScHandShake) message).toByteString();
				route = "handshake.ack";
				break;
			}
			if (CsGuestLogin.class.isInstance(message)) {
				body = ((CsGuestLogin) message).toByteString();
				route = "login.guest";
				break;
			}
			if (CsOpenidLogin.class.isInstance(message)) {
				body = ((CsOpenidLogin) message).toByteString();
				route = "login.openid";
				break;
			}
			if (ScLoginResult.class.isInstance(message)) {
				body = ((ScLoginResult) message).toByteString();
				route = "login.result";
				break;
			}
		}while (false);

		CoconutMessage msg = CoconutMessage.newBuilder().setBody(body).setRoute(route).build();
		return msg.toByteArray();
	}
}