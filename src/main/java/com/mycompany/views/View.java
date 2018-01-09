package com.mycompany.views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.mycompany.controllers.ViewController;

/**
 * Основная панель, на которой располагаются остальные панели
 */
public class View extends JFrame{
	private TopPanelView topPanel;
	private LabyrinthPanelView labyrinthPanel;
	private ScrollLabyrinthPanelView scrollLabyrinthPanel;
	private RadioBtnPanel radioBtnPanel;
	private JButton jbtnFindPath;
	
	private ViewController controller;
	
	public View(){
		
		labyrinthPanel = new LabyrinthPanelView();
		scrollLabyrinthPanel = new ScrollLabyrinthPanelView(labyrinthPanel);
		topPanel = new TopPanelView(scrollLabyrinthPanel);
		radioBtnPanel = new RadioBtnPanel(labyrinthPanel.getModel());
		
		controller = new ViewController();
		controller.setLabyrinthView(labyrinthPanel);
		
		jbtnFindPath = new JButton("Построить короткий путь");
		jbtnFindPath.addActionListener(controller);
		
		GridBagLayout gbag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbag.setConstraints(topPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbag.setConstraints(radioBtnPanel, gbc);
		
		gbc.insets = new Insets(20,0,0,0);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbag.setConstraints(scrollLabyrinthPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbag.setConstraints(jbtnFindPath, gbc);
	
		add(topPanel);
		add(scrollLabyrinthPanel);
		add(jbtnFindPath);
		add(radioBtnPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JMenuItem jmi = new JMenuItem("О программе");
		jmi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = "<html>Графическая программа поиска кратчайшего пути в лабиринте для двух заданных точек.<br>" +
							  "Используется алгоритм A-Star.<br><br>" + 
					          "<div style='text-align:center;'>Автор: Мартынов Роман, 2018 г.</div></html>";
				JOptionPane.showMessageDialog(null, text, "О программе", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		menuBar.add(jmi);
		setJMenuBar(menuBar);
		setLayout(gbag);
		setBounds(300, 0, 700, 730);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(700,750));
		setVisible(true);
		toFront();
	}

}
