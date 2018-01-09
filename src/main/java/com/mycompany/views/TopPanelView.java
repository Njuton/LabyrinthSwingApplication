package com.mycompany.views;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.mycompany.controllers.TopPanelController;

/**
 * Панель "создание таблицы"
 */
public class TopPanelView extends JPanel {

	private TopPanelController controller;

	private JLabel lab0;
	private JLabel lab1;
	private JLabel lab2;
	private JSpinner field1;
	private JSpinner field2;
	private JButton jbtn1;
	private JButton jbtn2;
	private JLabel jlab;
	private ScrollLabyrinthPanelView slp;

	public TopPanelView(ScrollLabyrinthPanelView slp) {

		lab0 = new JLabel("Создание таблицы:");
		lab0.setFont(new Font("Serif", Font.BOLD, 18));
		lab1 = new JLabel("Введите число строк");
		lab2 = new JLabel("Введите число столбцов");
		field1 = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
		field2 = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
		jbtn1 = new JButton("<html><div style='text-align: center'>Загрузить лабиринт<br> из файла...</div></html>");
		jbtn1.setActionCommand("jbtn1");
		jbtn2 = new JButton("Построить таблицу");
		jbtn2.setActionCommand("2");

		controller = new TopPanelController((Integer) field1.getValue(), (Integer) field2.getValue(), field1, field2);
		controller.setSlp(slp);

		// обработчик кнопки "Построить таблицу"
		jbtn2.addActionListener(controller);
		
		// обработчик кнопки "Загрузить лабиринт из файла"
		jbtn1.addActionListener(controller);

		GridBagLayout gbag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		setLayout(gbag);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(0,0,10,0);
		gbag.setConstraints(lab0, gbc);
		
		gbc.gridwidth=1;
		gbc.insets = new Insets(0, 0, 10, 20);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbag.setConstraints(lab1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbag.setConstraints(field1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbag.setConstraints(lab2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbag.setConstraints(field2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbag.setConstraints(jbtn2, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbag.setConstraints(jbtn1, gbc);

		add(lab1);
		add(lab2);
		add(field1);
		add(field2);
		add(jbtn1);
		add(jbtn2);
		add(lab0);
	
	}

}
