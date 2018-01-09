package com.mycompany;

import javax.swing.SwingUtilities;

import com.mycompany.views.View;

public class LabyrinthGUI {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void createAndShowGUI() throws Exception {
		new View();
	}

}
