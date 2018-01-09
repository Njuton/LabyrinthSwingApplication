package com.mycompany.views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.mycompany.algorithm.Constants;
import com.mycompany.controllers.LabyrinthController;
import com.mycompany.models.LabyrinthModel;

/**
 * Основная панель, на которой размещаются кнопки лабиринта
 */
public class LabyrinthPanelView extends JPanel{
	
	private LabyrinthModel model;
	private LabyrinthController controller;
	private JButton[][] grid;
	
	public LabyrinthPanelView() {
		
		model = new LabyrinthModel();
		
		controller = new LabyrinthController(model);
		
		int height = model.getHeight();
		int width = model.getWidth();
		
		setLayout(new GridLayout(height, width));
		grid = new JButton[height][width];
		
		// инициализация grid
		for (int i = 0; i < model.getHeight(); i++)
			for (int j = 0; j < model.getWidth(); j++) {
				JButton btn = new JButton();
				btn.setActionCommand(String.valueOf(i) + " " + String.valueOf(j));
				
				grid[i][j] = btn;
				grid[i][j].addActionListener(controller);
				btn.setPreferredSize(new Dimension(40,40));
				add(btn);
			}
		// об изменении размера кнопок должен оповещать только один компонент
		grid[0][0].addComponentListener(controller);
		
		controller.setGrid(grid);
		
	}
	
	/** перерисовка панели при создании таблицы */
	public void repaintGrid(int width, int height) {
		int oldHeight = model.getHeight();
		int oldWidth = model.getWidth();
		
		// убираем сведения о предыдущем состоянии
		model.setStart_x(null);
		model.setStart_y(null);
		model.setFinish_x(null);
		model.setFinish_y(null);
		
		for (int i = 0; i < oldHeight; i++)
			for (int j = 0; j < oldWidth; j++) {
				JButton btn = grid[i][j];
				remove(btn);
			}
		
		// вводим сведения о новом состоянии
		setLayout(new GridLayout(height, width));
		grid = new JButton[height][width];
		model.setHeight(height); 
		model.setWidth(width);
		
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
			{
				JButton btn = new JButton();
				btn.setActionCommand(String.valueOf(i) + " " + String.valueOf(j));
				grid[i][j] = btn;
				btn.setPreferredSize(new Dimension(40,40));
				grid[i][j].addActionListener(controller);
				add(btn);
			}
		grid[0][0].addComponentListener(controller);
		controller.setGrid(grid);
		
		model.updateStates();
	}
	
	// перерисовка панели в случае построения пути
	public void repaintGrid() {
		int width = model.getWidth();
		int height = model.getHeight();
		char[][] states = model.getStates();
		
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				if (states[i][j] == Constants.PATH) {
					try {
						grid[i][j].setIcon(new ImageIcon(ImageIO.read(new File("resized_img/plus.png"))));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	}

	public LabyrinthModel getModel() {
		return model;
	}
	
	
	
}
