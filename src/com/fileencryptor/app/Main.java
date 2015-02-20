package com.fileencryptor.app;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		
		FileEncryptor mFileEncryptor = new FileEncryptor();
		SwingUtilities.invokeLater(mFileEncryptor);

	}

}
