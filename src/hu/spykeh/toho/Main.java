package hu.spykeh.toho;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	private static String gameName = "Tohó";
	
	public static void main(String[] args) {
		Jatek jatek = new Jatek(gameName);
		jatek.frame.setResizable(false);
		jatek.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jatek.frame.setLayout(new BorderLayout());
		
		jatek.frame.add(jatek,BorderLayout.CENTER);
		jatek.frame.pack();
		
		jatek.frame.setLocationRelativeTo(null);
		jatek.frame.setVisible(true);
		jatek.start();
		System.out.println("Jatek elinditva");
		
		
	

	}

}
