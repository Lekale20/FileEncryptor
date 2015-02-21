package com.fileencryptor.customcomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class CustomJButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	private Color cliked = new Color(Integer.parseInt("525252", 16)) ;
	private Color nonCliked = new Color(Integer.parseInt("00ff96", 16));

	public CustomJButton(String text) {
		
		super(text);
		super.setForeground(new Color(Integer.parseInt("ffffff", 16)));
		super.setFocusPainted(false);
		super.setBorderPainted(true);
		super.setContentAreaFilled(false);
		super.setFont(new Font("Arial", Font.PLAIN, 12));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		if (getModel().isPressed()) {
			setBorder(BorderFactory.createLineBorder(cliked));
		} else {
			setBorder(BorderFactory.createLineBorder(nonCliked));
		}
		super.paintComponent(g);
	}

}
