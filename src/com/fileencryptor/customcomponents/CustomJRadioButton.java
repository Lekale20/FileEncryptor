package com.fileencryptor.customcomponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JRadioButton;

public class CustomJRadioButton extends JRadioButton{
	
	private static final long serialVersionUID = 1L;
	private Color nonSelected = new Color(Integer.parseInt("525252", 16)) ;
	private Color selected = new Color(Integer.parseInt("00ff96", 16));
	
	public CustomJRadioButton(String text) {
		
		super(text);
		super.setOpaque(false);
		super.setForeground(new Color(Integer.parseInt("ffffff", 16)));
		super.setFocusPainted(false);
		super.setBorderPainted(false);
		super.setContentAreaFilled(false);
		super.setFont(new Font("Arial", Font.PLAIN, 12));
		

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		if (getModel().isSelected()) {
				
			setIcon(createIcon(selected));
			
		} else {
			setIcon(createIcon(nonSelected));
		}
		super.paintComponent(g);
	}
	
	private Icon createIcon(Color iconColor) {
		
		Icon icon = new Icon() {
			
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {	
				g.setColor(iconColor);
			    g.fillOval (x, y, getIconWidth(), getIconHeight());				
			}
			
			@Override
			public int getIconWidth() {
				// TODO Auto-generated method stub
				return 12;
			}
			
			@Override
			public int getIconHeight() {
				// TODO Auto-generated method stub
				return 12;
			}
		};
		
		return icon;
		
	}

}
