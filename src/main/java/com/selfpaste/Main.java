package com.selfpaste;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class Main {

	public static void main(String[] args) throws Exception {

		File anchorFile = FileUtils.getFile("anchor");
		if(!anchorFile.exists()) {
			Bootstrap.boot();
			FileUtils.touch(new File("anchor"));
		}
	}
}
