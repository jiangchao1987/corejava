package com.changwan.game.network;

import com.changwan.game.service.Context;

public abstract class AbsHandler {

	protected Context context;
	
	public AbsHandler(Context ic) {
		context = ic;
	}

	public Context getContext() {
		return context;
	}
}
