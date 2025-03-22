package ru.kredwi.recursive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

public class Main {
	
	private static final String FILE_NAME = "RECURSIVES-" +System.currentTimeMillis() +"-"+UUID.randomUUID() + ".txt";
	
	public static void main(String[] enabledSuffix) {
		
		try (BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(FILE_NAME))))) {
			
			FileSystem fileSystem = new FileSystem(bos, enabledSuffix);
			URL pathToJARFile = Main.class.getProtectionDomain().getCodeSource().getLocation();
			
			try {
				fileSystem.start(new File(pathToJARFile.toURI()).getParentFile().getPath());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getFileName() {
		return FILE_NAME;
	}
}
