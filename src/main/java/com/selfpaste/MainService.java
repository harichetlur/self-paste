package com.selfpaste;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.selfpaste.db.IDatabaseService;

/**
 * This is the main service class that would be started and stopped
 */
@Singleton
public class MainService implements IService {

	private final List<IService> fServices = new ArrayList<>();
	private volatile boolean fRunning = false;

	// The parameters of this class are expected to be injected by Guice
	@Inject
	public MainService(IDatabaseService databaseService) {
		fServices.add(databaseService);
	}

	@Override
	public void start() throws Exception {
		for(IService service : fServices)
			service.start();
		fRunning = true;
	}

	@Override
	public void stop() throws Exception {
		for(IService service : fServices)
			service.stop();
		fRunning = false;
	}

	@Override
	public boolean isRunning() {
		return fRunning;
	}
}
