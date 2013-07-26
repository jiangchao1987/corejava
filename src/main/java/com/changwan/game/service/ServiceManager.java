package com.changwan.game.service;

public interface ServiceManager {
	/**
	 * 取得异步服务
	 * @return
	 */
	public AsyncService getAsyncService();

	/**
	 * 取得数据服务
	 * @return
	 */
	public DataService getDataService();

	/**
	 * 取得网络服务
	 * @return
	 */
	public NetworkService getNetworkService();

	/**
	 * 取得计时器服务
	 * @return
	 */
	public TimerService getTimerService();
}
