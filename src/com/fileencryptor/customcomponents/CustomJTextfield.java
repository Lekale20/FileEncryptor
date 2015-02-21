package com.fileencryptor.customcomponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class CustomJTextfield extends JTextField {

	private static final long serialVersionUID = 1L;
	private Color border = new Color(Integer.parseInt("00ff96", 16));

	public CustomJTextfield() {
		
		super();
		super.setEditable(false);
		super.setOpaque(false);
		super.setFont(new Font("Arial", Font.PLAIN, 12));
		super.setForeground(new Color(Integer.parseInt("b5b5b5", 16)));
		super.setBorder(BorderFactory.createLineBorder(border));
	}

}
