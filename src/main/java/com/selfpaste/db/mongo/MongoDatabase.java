package com.selfpaste.db.mongo;

import java.io.IOException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

/**
 * Wrapper for actual MongoDB object
 */
public class MongoDatabase {

	private final String fBindIP;
	private final int fDbPort;

	//Safe to assume there would only be one MongoDB that we care about at a given time
	private MongodProcess fMongodProcess;

	public MongoDatabase(String bindIP, int dbPort) {
		fBindIP = bindIP;
		fDbPort = dbPort;
	}

	public void start() throws IOException {
		MongodStarter starter = MongodStarter.getDefaultInstance();
		IMongodConfig mongodConfig = new MongodConfigBuilder()
				.version(Version.Main.PRODUCTION)
				.net(new Net(fBindIP, fDbPort, Network.localhostIsIPv6()))
				.build();

		MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
		MongodProcess mongod = mongodExecutable.start();
		assert (mongod.isProcessRunning());

		fMongodProcess = mongod;
		System.out.println("Started MongoDB successfully");
	}

	public void stop() {
		if(fMongodProcess == null)
			return;

		fMongodProcess.stop();
		System.out.println("Stopped MongoDB successfully");
	}
}
