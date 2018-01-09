package com.mycompany.views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.mycompany.controllers.RadioBtnPanelController;
import com.mycompany.models.LabyrinthModel;

/**
 * Панель с радиокнопками выбора режима постановки объектов
 */
public class RadioBtnPanel extends JPanel {
	
	private JRadioButton radioBtnFirst;
	private JRadioButton radioBtnSecond;
	private JRadioButton radioBtnThird;
	private JRadioButton radioBtnFourth;
	private JLabel jlab;
	private RadioBtnPanelController controller;
	private LabyrinthModel labyrinthModel;
	
	public RadioBtnPanel(LabyrinthModel labyrinthModel) {
		this.labyrinthModel = labyrinthModel;
		controller = new RadioBtnPanelController(labyrinthModel);
		
		radioBtnFirst = new JRadioButton("Постановка стен");
		radioBtnFirst.setActionCommand("1");
		radioBtnFirst.setSelected(true);
		radioBtnFirst.setMnemonic('1');
		
		radioBtnSecond = new JRadioButton("Постановка начала пути");
		radioBtnSecond.setActionCommand("2");
		radioBtnSecond.setMnemonic('2');
		
		radioBtnThird = new JRadioButton("Постановка конца пути");
		radioBtnThird.setActionCommand("3");
		radioBtnThird.setMnemonic('3');
		
		radioBtnFourth = new JRadioButton("Удалить отметку");
		radioBtnFourth.setActionCommand("4");
		radioBtnFourth.setMnemonic('4');
		
		
		jlab = new JLabel("Режим расстановки объектов:");
		jlab.setFont(new Font("Serif", Font.BOLD, 18));
		jlab.setToolTipText("Переключение между пунктами можно осуществлять при помощи комбинации Alt + цифра от 1 до 4");
		
		radioBtnFirst.addActionListener(controller);
		radioBtnSecond.addActionListener(controller);
		radioBtnThird.addActionListener(controller);
		radioBtnFourth.addActionListener(controller);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		buttonGroup.add(radioBtnFirst);
		buttonGroup.add(radioBtnSecond);
		buttonGroup.add(radioBtnThird);
		buttonGroup.add(radioBtnFourth);
		
		GridBagLayout gbag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbag.setConstraints(jlab, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbag.setConstraints(radioBtnFirst, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbag.setConstraints(radioBtnSecond, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbag.setConstraints(radioBtnThird, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbag.setConstraints(radioBtnFourth, gbc);
		
		
		setLayout(gbag);
		
		add(radioBtnFirst);
		add(radioBtnSecond);
		add(radioBtnThird);
		add(radioBtnFourth);
		add(jlab);
		
	}
	
	

}
