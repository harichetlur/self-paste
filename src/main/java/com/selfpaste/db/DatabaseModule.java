package com.selfpaste.db;

import com.google.inject.AbstractModule;
import com.selfpaste.db.mongo.MongoDatabaseService;

/**
 * Created by hari.chetlur on 9/27/17.
 */
public class DatabaseModule extends AbstractModule {


	private static final String BIND_IP = "localhost";
	private static final int DB_PORT = 12345;

	private final IDatabaseService fDatabaseService;

	public DatabaseModule() {
		fDatabaseService = new MongoDatabaseService(BIND_IP, DB_PORT);
	}

	protected void configure() {
		bind(IDatabaseService.class).toInstance(fDatabaseService);
	}
}
