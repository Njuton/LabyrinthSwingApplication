package com.mycompany.views;

import javax.swing.JScrollPane;

/**
 * Провкручиваемая панель с лабиринтом
 */
public class ScrollLabyrinthPanelView extends JScrollPane{
	private LabyrinthPanelView labyrinthPanel;
	
	public ScrollLabyrinthPanelView(LabyrinthPanelView lp) {
		super(lp);
		labyrinthPanel = lp;
		
	}
	
	public void repaintScrollGrid(int height, int width) {
		labyrinthPanel.repaintGrid(width, height);
		validate();
		repaint();
	}

	public LabyrinthPanelView getLabyrinthPanel() {
		return labyrinthPanel;
	}

}
