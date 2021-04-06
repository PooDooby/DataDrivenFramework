package com.dooby.scratchPad;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {

		Properties config = new Properties();
		Properties or = new Properties();

		FileInputStream fiss = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		config.load(fiss);
		
		
		fiss = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
		or.load(fiss);

		System.out.println(config.getProperty("testsiteurl"));
		
		System.out.println(or.getProperty("bmlBtn"));
	}

}
