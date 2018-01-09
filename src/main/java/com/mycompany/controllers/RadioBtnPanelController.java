package com.mycompany.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mycompany.enums.Modes;
import com.mycompany.models.LabyrinthModel;

public class RadioBtnPanelController implements ActionListener {
	private LabyrinthModel labyrinthModel;
	
	public RadioBtnPanelController(LabyrinthModel labyrinthModel) {
		this.labyrinthModel = labyrinthModel;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String buttonNum = ae.getActionCommand();

		// изменение режима расстановки объектов
		
		switch(buttonNum) {
			case "1":
				labyrinthModel.setMode(Modes.WALL);
				break;
				
			case "2":
				labyrinthModel.setMode(Modes.START);
				break;
			
			case "3":
				labyrinthModel.setMode(Modes.FINISH);
				break;
			default:
				labyrinthModel.setMode(Modes.CLEAN);
		}
		
		
	}
	
	

}
