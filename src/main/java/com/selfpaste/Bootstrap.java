package com.selfpaste;

import java.io.IOException;

import com.mongodb.MongoClient;
import com.selfpaste.db.mongo.MongoDatabase;

/**
 * Created by harichetlur on 4/10/17.
 */
public class Bootstrap {

	private static final String BIND_IP = "localhost";
	private static final int DB_PORT = 12345;

	public static void boot() throws IOException {
		MongoDatabase db = new MongoDatabase(BIND_IP, DB_PORT);
		db.start();
		createSchema();
	}

	private static void createSchema() {
		MongoClient mongo = new MongoClient(BIND_IP, DB_PORT);
		com.mongodb.client.MongoDatabase db = mongo.getDatabase("selfpaste");
		db.createCollection("records");
	}


}
