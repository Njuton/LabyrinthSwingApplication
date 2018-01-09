package com.mycompany.models;

import com.mycompany.enums.Modes;

public class LabyrinthModel {
	private int width = 10;
	private int height = 10;
	private Modes mode = Modes.WALL;
	
	// 1 - если при resize будет устанавливаться фоновое изображение на все кнопки
	// 2 - если различные изображения (при загрузке из файла)
	// 3 - если первое включение программы
	private int setDefaultIcon = 3;
	
	// true-если, путь был построен и на карте есть плюсики
	private boolean isSuccessPath = false;

	// координаты старта/конца пути
	private Integer start_x;
	private Integer start_y;
	private Integer finish_x;
	private Integer finish_y;

	private char[][] states = new char[height][width];

	public LabyrinthModel() {

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				states[i][j] = '.';
			}

	}

	public void updateStates() {
		states = new char[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				states[i][j] = '.';
			}
	}
	
	public void updateStates(char[][] states) {
		this.states = states;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Modes getMode() {
		return mode;
	}

	public char[][] getStates() {
		return states;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setMode(Modes mode) {
		this.mode = mode;
	}

	public void setStates(char[][] states) {
		this.states = states;
	}

	public Integer getStart_x() {
		return start_x;
	}

	public void setStart_x(Integer start_x) {
		this.start_x = start_x;
	}

	public Integer getStart_y() {
		return start_y;
	}

	public void setStart_y(Integer start_y) {
		this.start_y = start_y;
	}

	public Integer getFinish_x() {
		return finish_x;
	}

	public void setFinish_x(Integer finish_x) {
		this.finish_x = finish_x;
	}

	public Integer getFinish_y() {
		return finish_y;
	}

	public void setFinish_y(Integer finish_y) {
		this.finish_y = finish_y;
	}

	public boolean isSuccessPath() {
		return isSuccessPath;
	}

	public void setSuccessPath(boolean isSuccessPath) {
		this.isSuccessPath = isSuccessPath;
	}

	public int getSetDefaultIcon() {
		return setDefaultIcon;
	}

	public void setSetDefaultIcon(int setDefaultIcon) {
		this.setDefaultIcon = setDefaultIcon;
	}

}
