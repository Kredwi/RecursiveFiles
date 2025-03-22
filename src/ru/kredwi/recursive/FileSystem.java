package ru.kredwi.recursive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileSystem {
	
	private BufferedWriter writer;
	private String[] enabledSuffix;
	private StringBuilder text = new StringBuilder();
	
	public FileSystem(BufferedWriter writer, String[] enabledSuffix) {
		this.writer = writer;
		if (enabledSuffix == null || enabledSuffix.length < 1) {
			this.enabledSuffix = new String[0];
		} else {
			this.enabledSuffix = enabledSuffix;	
		}
	}
	
	public String getText() {
		return text.toString();
	}
	
	public void start(String path) {
		getNextPath(path);
	}
	
	private FileSystem getNextPath(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					getNextPath(f.getPath());
				} else if (f.isFile()){
					writeFilesToOutputFile(f);
				}
			}
		}
		return this;
	}
	private void writeFilesToOutputFile(File file) {
		try {
			String path = file.getPath();
			if (file.isFile()) {
				boolean fileIsEnabled = enabledSuffix.length < 1;
				for (String suffix : enabledSuffix) {
					if (file.getName().endsWith(suffix)) {
						fileIsEnabled = true;
						break;
					}
				}
				if (!fileIsEnabled | file.getName().equalsIgnoreCase(Main.getFileName())) {
					return;
				}
				System.out.println(path);
				writer.write(path);
				writer.newLine();
				try (BufferedReader rb = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
					String line;
					while ((line = rb.readLine()) != null) {
						writer.write(line);
						writer.newLine();
					}
					writer.newLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
