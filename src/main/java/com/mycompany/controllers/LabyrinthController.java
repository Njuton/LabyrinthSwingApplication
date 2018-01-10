package com.mycompany.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.commons.io.FileUtils;

import com.mycompany.algorithm.Constants;
import com.mycompany.models.LabyrinthModel;

import net.coobird.thumbnailator.Thumbnails;

public class LabyrinthController extends ComponentAdapter implements ActionListener {
	private LabyrinthModel model;
	private JButton[][] grid;

	public LabyrinthController(LabyrinthModel model) {
		this.model = model;
	}

	/**
	 * Метод вызывается при изменении размера кнопок менеджером компоновки
	 * GridLayout
	 */
	@Override
	public void componentResized(ComponentEvent ce) {
		JButton btn = (JButton) ce.getSource();
		int width = btn.getWidth();
		int height = btn.getHeight();

		try {

			// создание папки "resized_img" для хранения перезаписываемых изображений
			// (меняется размер при каждом обновлении таблицы)
			File pathToImg = new File("resized_img");
			if (!pathToImg.exists())
				pathToImg.mkdir();

			// изменение размера изображений согласно новым размерам кнопок
			Thumbnails.of(ClassLoader.getSystemResource("source_img/background.png").openStream())
					.size((int) (width), (int) (height)).outputFormat("png").outputQuality(1)
					.toFile(new File("resized_img/background.png"));

			Thumbnails.of(ClassLoader.getSystemResource("source_img/box.png").openStream())
					.size((int) (width), (int) (height)).outputFormat("png").outputQuality(1)
					.toFile(new File("resized_img/box.png"));

			Thumbnails.of(ClassLoader.getSystemResource("source_img/finish.png").openStream())
					.size((int) (width), (int) (height)).outputFormat("png").outputQuality(1)
					.toFile(new File("resized_img/finish.png"));

			Thumbnails.of(ClassLoader.getSystemResource("source_img/circle.png").openStream())
					.size((int) (width * 0.6), (int) (height * 0.6)).outputFormat("png").outputQuality(1)
					.toFile(new File("resized_img/plus.png"));

			Thumbnails.of(ClassLoader.getSystemResource("source_img/start.png").openStream())
					.size((int) (width), (int) (height)).outputFormat("png").outputQuality(1)
					.toFile(new File("resized_img/start.png"));

			// если нажали кнопку "построить таблицу" => обновить фон
			if (model.getSetDefaultIcon() == 1) {
				for (int i = 0; i < grid.length; i++)
					for (int j = 0; j < grid[0].length; j++) {
						grid[i][j].setIcon(new ImageIcon(ImageIO.read(new File("resized_img/background.png"))));
						model.setSuccessPath(false);
					}
			} else {
				
				// если программа запускается впервые => загрузить стандартный лабиринт из файла
				if (model.getSetDefaultIcon() == 3) {
					
					// создание папки "labyrinths" в корневом каталоге
					File pathToLab = new File("labyrinths");
					if (!pathToLab.exists())
						pathToLab.mkdir();

					// заполнение папки "labyrinths" файлами, размещенными в каталогах-ресурсах
					// (размещаются
					// в jar-архиве, являются статичными, т.е. загружаются 1 раз JVM, используются
					// exe.

					FileUtils.copyInputStreamToFile(ClassLoader.getSystemResource("labyrinths/lab1").openStream(),
							new File("labyrinths/lab1"));

					FileUtils.copyInputStreamToFile(ClassLoader.getSystemResource("labyrinths/lab2").openStream(),
							new File("labyrinths/lab2"));
					
					File openFile = new File("labyrinths/lab1");
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
						model.setStates(labyrinth);
					}
				}
				
				// если программа загружается впервые или загружен лабиринт => обновить иконки на кнопках
				
				char[][] states = model.getStates();
				for (int i = 0; i < states.length; i++)
					for (int j = 0; j < states[0].length; j++) {
						switch (states[i][j]) {
						case Constants.ROAD:
							grid[i][j].setIcon(new ImageIcon(ImageIO.read(new File("resized_img/background.png"))));
							break;
						case Constants.WALL:
							grid[i][j].setIcon(new ImageIcon(ImageIO.read(new File("resized_img/box.png"))));
							break;
						case Constants.START:
							model.setStart_x(j);
							model.setStart_y(i);
							grid[i][j].setIcon(new ImageIcon(ImageIO.read(new File("resized_img/start.png"))));
							break;
						case Constants.FINISH:
							model.setFinish_x(j);
							model.setFinish_y(i);
							grid[i][j].setIcon(new ImageIcon(ImageIO.read(new File("resized_img/finish.png"))));
							break;
						}
					}
				model.setSetDefaultIcon(1);
				model.setSuccessPath(false);
			}
		} catch (Throwable exc) {
			try (FileWriter fw = new FileWriter("err.txt")) {
				fw.write(exc.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		JButton jbtn = (JButton) ae.getSource();
		String[] s = jbtn.getActionCommand().split("\\s");
		int i = Integer.valueOf(s[0]);
		int j = Integer.valueOf(s[1]);
		char[][] states = model.getStates();
		
		// если путь был построен и была нажата любая кнопка => удалить весь построенный путь
		if (model.isSuccessPath()) {
			for (int k = 0; k < states.length; k++)
				for (int l = 0; l < states[0].length; l++)
					if (states[k][l] == '+') {
						states[k][l] = '.';
						try {
							grid[k][l].setIcon(new ImageIcon(ImageIO.read(new File("resized_img/background.png"))));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			model.setSuccessPath(false);
		}

		// в соответсвии с выбранным режимом при нажатии кнопки обновить изображение и состояние кнопки
		switch (model.getMode()) {
		case WALL:
			states[i][j] = Constants.WALL;
	
			try {
				jbtn.setIcon(new ImageIcon(ImageIO.read(new File("resized_img/box.png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case START:
			// если был выставлен старт ранее => убираем его
			Integer oldStartX = model.getStart_x();
			Integer oldStartY = model.getStart_y();

			if (oldStartX != null) {
				try {
					grid[oldStartY][oldStartX]
							.setIcon(new ImageIcon(ImageIO.read(new File("resized_img/background.png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
				states[oldStartY][oldStartX] = Constants.ROAD;
			}
			// выставляем новый старт
			states[i][j] = Constants.START;
			model.setStart_x(j);
			model.setStart_y(i);

			try {
				jbtn.setIcon(new ImageIcon(ImageIO.read(new File("resized_img/start.png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;

		case FINISH:
			// если был выставлен финиш ранее => убираем его
			Integer oldFinishX = model.getFinish_x();
			Integer oldFinishY = model.getFinish_y();

			if (oldFinishX != null) {
				try {
					grid[oldFinishY][oldFinishX]
							.setIcon(new ImageIcon(ImageIO.read(new File("resized_img/background.png"))));
				} catch (IOException e) {
					e.printStackTrace();
				}
				states[oldFinishY][oldFinishX] = Constants.ROAD;
			}

			// выставляем новый финиш
			states[i][j] = Constants.FINISH;
			model.setFinish_x(j);
			model.setFinish_y(i);

			try {
				jbtn.setIcon(new ImageIcon(ImageIO.read(new File("resized_img/finish.png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		default:
			states[i][j] = Constants.ROAD;
			try {
				jbtn.setIcon(new ImageIcon(ImageIO.read(new File("resized_img/background.png"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public JButton[][] getGrid() {
		return grid;
	}

	public void setGrid(JButton[][] grid) {
		this.grid = grid;
	}

}
