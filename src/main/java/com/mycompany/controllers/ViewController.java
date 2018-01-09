package com.mycompany.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import com.mycompany.algorithm.AStar;
import com.mycompany.algorithm.Constants;
import com.mycompany.algorithm.Navigator;
import com.mycompany.models.LabyrinthModel;
import com.mycompany.views.LabyrinthPanelView;

public class ViewController implements ActionListener {
	private LabyrinthPanelView labyrinthView;
	
	/**
	 * Метод вызывается при нажатии клавиши "Построить путь"
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LabyrinthModel labyrinthModel = labyrinthView.getModel();
		char[][] states = labyrinthModel.getStates();
		
		boolean findStart = false;
		boolean findFinish = false;
		boolean findPlus = false;
		
		for (int i = 0; i < states.length; i++)
			for (int j = 0; j < states[0].length; j++) {
				switch(states[i][j]) {
				
				case Constants.PATH:
					findPlus = true;
					break;
					
				case Constants.FINISH:
					findFinish = true;
					break;
					
				case Constants.START:
					findStart = true;
					break;
				}
			}
		
		if (!findStart) {
			JOptionPane.showMessageDialog(null, "В лабиринте должен присуствовать \"Старт\"", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		if (!findFinish) {
			JOptionPane.showMessageDialog(null, "В лабиринте должен присуствовать \"Финиш\"", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		if (findPlus) {
			JOptionPane.showMessageDialog(null, "Путь уже построен.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return ;
		}	
		
		Navigator navigator = new AStar();
		char[][] result = navigator.searchRoute(states);
		
		if (result == null) {
			JOptionPane.showMessageDialog(null, "Путь не найден.", "Результат", JOptionPane.INFORMATION_MESSAGE);
			return ;
		}
		
		labyrinthModel.setSuccessPath(true);
		labyrinthModel.updateStates(result);
		labyrinthView.repaintGrid();
	}

	public void setLabyrinthView(LabyrinthPanelView labyrinthView) {
		this.labyrinthView = labyrinthView;
	}
	
}
