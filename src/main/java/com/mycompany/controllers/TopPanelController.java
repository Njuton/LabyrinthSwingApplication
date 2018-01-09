package com.mycompany.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JSpinner;

import com.mycompany.views.ScrollLabyrinthPanelView;

public class TopPanelController implements ActionListener {
	private int width;
	private int height;
	private JSpinner field1;
	private JSpinner field2;

	private ScrollLabyrinthPanelView slp;

	public TopPanelController(int width, int height, JSpinner field1, JSpinner field2) {
		this.width = width;
		this.height = height;
		this.field1 = field1;
		this.field2 = field2;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		// нажата клавиша "Построить таблицу"

		if (ae.getActionCommand().equals("2")) {
			int width = (Integer) field2.getValue();
			int height = (Integer) field1.getValue();
			slp.repaintScrollGrid(height, width);
			return;
		}

		// нажата клавиша "Загрузить лабиринт из файла"

		JFileChooser fc = new JFileChooser();
		
		fc.setCurrentDirectory(new File("labyrinths"));
		fc.showOpenDialog(null);

		File openFile = fc.getSelectedFile();
		
		if (openFile != null) {
			
			List<char[]> charArray = new ArrayList<char[]>();
			
			try (BufferedReader br = new BufferedReader(new FileReader(openFile))) {
				String strLine;
				
				while ((strLine = br.readLine()) != null)
					charArray.add(strLine.toCharArray());
			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			char[][] labyrinth = new char[charArray.size()][];
			
			for (int i = 0; i < charArray.size(); i++)
				labyrinth[i] = charArray.get(i);
			
			slp.repaintScrollGrid(labyrinth.length, labyrinth[0].length);
			slp.getLabyrinthPanel().getModel().updateStates(labyrinth);
			slp.getLabyrinthPanel().getModel().setSetDefaultIcon(2);
		}
	}

	public void setSlp(ScrollLabyrinthPanelView slp) {
		this.slp = slp;
	}

}
