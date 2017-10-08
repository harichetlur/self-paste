package com.selfpaste;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) throws Exception {



		File anchorFile = FileUtils.getFile("anchor");

		if(!anchorFile.exists()) {
			Injector injector = Guice.createInjector(new MainModule());

			final MainService mainService = injector.getInstance(MainService.class);

			try {
				mainService.start();
			} catch (Exception e) {
				if(mainService.isRunning())
					try {
						mainService.stop();
					} catch (Exception e1) {
						//TODO : Handle this better with logging
						throw new RuntimeException(e1);
					}
			}

			FileUtils.touch(new File("anchor"));

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					mainService.stop();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					FileUtils.deleteQuietly(FileUtils.getFile("anchor"));
				}
			}));

			Thread.currentThread().join();
		} else {
			System.err.println("anchor file already exists");
		}
	}
}
