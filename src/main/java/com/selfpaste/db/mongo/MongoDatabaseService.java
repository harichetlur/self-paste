package com.selfpaste.db.mongo;

import java.io.IOException;

import com.mongodb.MongoClient;
import com.selfpaste.IService;
import com.selfpaste.db.IDatabaseService;
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
public class MongoDatabaseService implements IDatabaseService {

	private final String fBindIP;
	private final int fDbPort;

	//Safe to assume there would only be one MongoDB that we care about at a given time
	private MongodProcess fMongodProcess;

	public MongoDatabaseService(String bindIP, int dbPort) {
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

		createSchema();
		System.out.println("Started MongoDB successfully");
	}

	public void stop() {
		if(fMongodProcess == null)
			return;

		fMongodProcess.stop();
		System.out.println("Stopped MongoDB successfully");
	}

	public boolean isRunning() {
		return fMongodProcess != null && fMongodProcess.isProcessRunning();
	}

	private void createSchema() {
		MongoClient mongo = new MongoClient(fBindIP, fDbPort);
		com.mongodb.client.MongoDatabase db = mongo.getDatabase("selfpaste");
		db.createCollection("records");
	}
}
