package com.selfpaste;

/**
 * A Service, as the name suggests, would be a part of the implementation that would need to be configured to start
 * or stop. These are responsible for guiding flow and providing features 'as a service'. Think of these as
 * Controllers in an MVC architecture.
 */
public interface IService {

	/**
	 * Start this service
	 */
	void start() throws Exception;

	/**
	 * Stop this service
	 */
	void stop() throws Exception;

	/**
	 * Utility method to check if this service is running
	 */
	boolean isRunning();
}
