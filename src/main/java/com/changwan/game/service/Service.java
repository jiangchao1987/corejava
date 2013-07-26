package com.changwan.game.service;

public interface Service {

	/**
	 * 启动服务
	 * @return
	 */
	public void start();

	/**
	 * 停止服务
	 * @return
	 */
	public void stop();

	/**
	 * 取得服务状态
	 * @return
	 */
	public boolean isRunning();
}
