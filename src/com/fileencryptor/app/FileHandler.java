package com.fileencryptor.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileHandler {

	public static String ReadFile(File fileToRead) throws IOException {

		BufferedReader mBufferedReader = new BufferedReader(new FileReader(
				fileToRead));
		StringBuilder mStringBuilder = new StringBuilder();

		String dataLine;

		while ((dataLine = mBufferedReader.readLine()) != null) {

			mStringBuilder.append(dataLine);
		}

		mBufferedReader.close();

		return mStringBuilder.toString();

	}

	public static boolean WriteFile(File file, String dataToWrite) {
		
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.write("");
			writer.write(dataToWrite);
			writer.close();
			return true;
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}

	}
}
