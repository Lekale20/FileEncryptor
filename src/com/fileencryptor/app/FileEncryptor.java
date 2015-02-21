package com.fileencryptor.app;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FileEncryptor implements Runnable {

	private CryptoMode mCurrentCryptoMode;
	private JFrame mJFrame;
	private JFileChooser mJFileChooser;
	private File inputFile, outputFile;

	@Override
	public void run() {

		mJFrame = new JFrame("File encryptor");
		mJFrame.setPreferredSize(new Dimension(320, 240));
		mJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mJFileChooser = new JFileChooser();

		mCurrentCryptoMode = CryptoMode.UNDEFINED;

		createComponents(mJFrame.getContentPane());

		mJFrame.pack();
		mJFrame.setVisible(true);
		mJFrame.setLocationRelativeTo(null);

	}

	private void createComponents(Container container) {

		GridBagLayout mGridBagLayout = new GridBagLayout();
		mGridBagLayout.columnWidths = new int[] { 0, 00, 0 };
		mGridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		mGridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0 };
		mGridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		container.setLayout(mGridBagLayout);

		GridBagConstraints mGridBagConstraints = new GridBagConstraints();
		mGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		mGridBagConstraints.insets = new Insets(5, 5, 5, 5);

		JLabel mJLabelModeTitle = new JLabel("Mode");
		mGridBagConstraints.gridx = 0;
		mGridBagConstraints.gridy = 0;
		container.add(mJLabelModeTitle, mGridBagConstraints);

		JRadioButton mJRadioButtonModeEncrypt = new JRadioButton("Encrypt");
		mGridBagConstraints.gridx = 1;
		mGridBagConstraints.gridy = 0;
		container.add(mJRadioButtonModeEncrypt, mGridBagConstraints);

		JRadioButton mJRadioButtonModeDecrypt = new JRadioButton("Decrypt");
		mGridBagConstraints.gridx = 2;
		mGridBagConstraints.gridy = 0;
		container.add(mJRadioButtonModeDecrypt, mGridBagConstraints);

		ButtonGroup mRadioButtonGroupMode = new ButtonGroup();
		mRadioButtonGroupMode.add(mJRadioButtonModeEncrypt);
		mRadioButtonGroupMode.add(mJRadioButtonModeDecrypt);

		JLabel mJLabelInputTitle = new JLabel("Input");
		mGridBagConstraints.gridx = 0;
		mGridBagConstraints.gridy = 1;
		container.add(mJLabelInputTitle, mGridBagConstraints);

		JButton mJButtonInputOpen = new JButton("Open");
		mGridBagConstraints.gridx = 1;
		mGridBagConstraints.gridy = 1;
		container.add(mJButtonInputOpen, mGridBagConstraints);

		JTextField mJTextFieldInputPath = new JTextField();
		mJTextFieldInputPath.setEditable(false);
		mGridBagConstraints.gridx = 2;
		mGridBagConstraints.gridy = 1;
		container.add(mJTextFieldInputPath, mGridBagConstraints);

		JLabel mJLabelOutputTitle = new JLabel("Output");
		mGridBagConstraints.gridx = 0;
		mGridBagConstraints.gridy = 2;
		container.add(mJLabelOutputTitle, mGridBagConstraints);

		JButton mJButtonOutputOpen = new JButton("Open");
		mGridBagConstraints.gridx = 1;
		mGridBagConstraints.gridy = 2;
		container.add(mJButtonOutputOpen, mGridBagConstraints);

		JTextField mJTextFieldOutputPath = new JTextField();
		mJTextFieldOutputPath.setEditable(false);
		mGridBagConstraints.gridx = 2;
		mGridBagConstraints.gridy = 2;
		container.add(mJTextFieldOutputPath, mGridBagConstraints);

		JLabel mJLabelKeyTitle = new JLabel("Key");
		mGridBagConstraints.gridx = 0;
		mGridBagConstraints.gridy = 3;
		container.add(mJLabelKeyTitle, mGridBagConstraints);

		JTextField mJTextFieldKeyField = new JTextField();
		mGridBagConstraints.gridx = 1;
		mGridBagConstraints.gridy = 3;
		mGridBagConstraints.gridwidth = 2;
		container.add(mJTextFieldKeyField, mGridBagConstraints);

		JButton mJButtonReset = new JButton("Reset");
		mGridBagConstraints.fill = GridBagConstraints.BOTH;
		mGridBagConstraints.gridwidth = 1;
		mGridBagConstraints.gridx = 1;
		mGridBagConstraints.gridy = 4;
		container.add(mJButtonReset, mGridBagConstraints);

		JButton mJButtonBegin = new JButton("Begin");
		mGridBagConstraints.gridx = 2;
		mGridBagConstraints.gridy = 4;
		container.add(mJButtonBegin, mGridBagConstraints);

		mJButtonInputOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int inputOpenReturnValue = mJFileChooser
						.showOpenDialog(mJFrame);

				if (inputOpenReturnValue == JFileChooser.APPROVE_OPTION) {

					inputFile = mJFileChooser.getSelectedFile();

					mJTextFieldInputPath.setText(inputFile.getAbsolutePath());
				}

			}
		});

		mJButtonOutputOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int outputOpenReturnValue = mJFileChooser
						.showOpenDialog(mJFrame);

				if (outputOpenReturnValue == JFileChooser.APPROVE_OPTION) {

					outputFile = mJFileChooser.getSelectedFile();

					mJTextFieldOutputPath.setText(outputFile.getAbsolutePath());
				}

			}
		});

		mJButtonReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mCurrentCryptoMode = CryptoMode.UNDEFINED;
				inputFile = null;
				outputFile = null;
				mRadioButtonGroupMode.clearSelection();
				mJTextFieldInputPath.setText("");
				mJTextFieldOutputPath.setText("");
				mJTextFieldKeyField.setText("");
			}
		});

		mJButtonBegin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (mCurrentCryptoMode.equals(CryptoMode.UNDEFINED)
						|| mJTextFieldInputPath.getText().isEmpty()
						|| mJTextFieldOutputPath.getText().isEmpty()
						|| mJTextFieldKeyField.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(container,
									"Incorrect input \n1. Check for empty fields \n2. Check for invalid data");

				} else if (mCurrentCryptoMode.equals(CryptoMode.ENCRYPT)) {

					try {
						String dataToEncrypt = FileHandler.ReadFile(inputFile);

						String encryptedData = AESHandler.encryptData(
								dataToEncrypt, mJTextFieldKeyField.getText());

						if (FileHandler.WriteFile(outputFile, encryptedData)) {
							JOptionPane.showMessageDialog(container,
									"Encryption done");
							mJButtonReset.doClick();
						} else {
							JOptionPane.showMessageDialog(container,
									"Error in file writing (Output file)");
						}

					} catch (IOException e1) {

						JOptionPane.showMessageDialog(container,
								"Error in file reading (Input file)");
						e1.printStackTrace();

					} catch (InvalidKeyException | NoSuchAlgorithmException
							| NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException
							| InvalidKeySpecException e2) {

						JOptionPane.showMessageDialog(container,
								"Error in encryption");

						e2.printStackTrace();
					}
				} else if (mCurrentCryptoMode.equals(CryptoMode.DECRYPT)) {

					try {
						String dataToDecrypt = FileHandler.ReadFile(inputFile);

						String decryptedData = AESHandler.decryptData(
								dataToDecrypt, mJTextFieldKeyField.getText());
						
						if (FileHandler.WriteFile(outputFile, decryptedData)) {
							JOptionPane.showMessageDialog(container,
									"Decryption done");
							mJButtonReset.doClick();
						} else {
							JOptionPane.showMessageDialog(container,
									"Error in file writing (Output file)");
						}
								

					} catch (IOException e1) {
						
						JOptionPane.showMessageDialog(container,
								"Error in file reading (Input file)");
						e1.printStackTrace();

					} catch (InvalidKeyException | NoSuchAlgorithmException
							| InvalidKeySpecException
							| InvalidAlgorithmParameterException
							| NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException e2) {
						
						JOptionPane.showMessageDialog(container,
								"Error in encryption");

						e2.printStackTrace();
					}

				}
			}
		});

		mJRadioButtonModeEncrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mCurrentCryptoMode = CryptoMode.ENCRYPT;
			}
		});
		mJRadioButtonModeDecrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mCurrentCryptoMode = CryptoMode.DECRYPT;
			}
		});

	}

	public enum CryptoMode {
		ENCRYPT, DECRYPT, UNDEFINED
	}

}
