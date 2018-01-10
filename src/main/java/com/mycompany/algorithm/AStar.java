package com.mycompany.algorithm;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import javax.swing.JButton;

/**
 * Реализация алгоритма поиска кратчайшего пути на графе - A* (A-Star).
 * 
 * @author Martynov Roman 2018.
 */

class Node {
	
	// координаты
	private int x;
	private int y;
	
	// значения функций
	private int g;
	private int h;
	private int f;
	
	// родительский узел
	private Node parent;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this)
			return true;
		
		if (obj == null || !(obj instanceof Node))
			return false;
		
		Node node = (Node) obj;
		
		if (node.getX() == x && node.getY() == y)
			return true;
		else 
			return false;
	}

	@Override
	public int hashCode() {
		int result = 25;
		result = result * 31 + x;
		result = result * 31 + y;
		return result;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

public class AStar implements Navigator{
	
	// карта узлов размерностью HxW
	private Node[][] lab;
	
	
	 // openSet - коллекция узлов, у которых заполнены поля и которые могут быть 
	 // нам интересны в дальейшем; соседи не просмотрены
	 // poll будет извлекать узел с наименьшим значением F за время O(logn)
	private Queue<Node> openSet = new PriorityQueue<Node>(new Comparator<Node>() {

		@Override
		public int compare(Node one, Node two) {
			if (one.getF() < two.getF())
				return -1;
			else
				if (one.getF() > two.getF())
					return 1;
			return 0;
		}
	});
	
	
	 // closeSet - коллекция узлов, которых мы исключили из дальнейшего просмотра; все соседи просмотрены
	 // add, contains за время O(1) 
	private Set<Node> closeSet = new HashSet<Node>();
	
	// смещение для соседних ячеек (правая, верхняя, левая, нижняя)
	private int dx[] = {1, 0, -1, 0};
	private int dy[] = {0, -1, 0, 1};
		
	// стоимость перехода в соседнюю ячейку
	private int v = 1;
	
	// координаты (x,y) стартового узла; координаты (x,y) конечного узла 
	private int ax, ay, bx, by;
	
	// конечный узел (по нему будет восстанавливаться путь)
	private Node goal;
	
	/**
	 * Рассчет эвристической функции, дающей приблизительную оценку расстояния
	 * от текущего узла до конечного
	 * 
	 * @param cx - координата x текущего узла
	 * @param cy - координата y текущего узла
	 */
	 private int heuristic_cost_estimate(int cx, int cy) {
		 
		 return Math.abs(cx - bx) + Math.abs(cy - by);
	}
	
	/**
	 * Этап 2 - алгоритм A*
	 * 
	 * @return true, если путь найден (+ устанавливается поле goal), false - не найден;
	 */
	private boolean algorithm() {
		
		Node start = lab[ay][ax];
		
		// инициализируем стартовый узел
		start.setG(0);
		start.setH(heuristic_cost_estimate(ax, ay));
		start.setF(start.getH());
		
		openSet.offer(start);
		
		while(!openSet.isEmpty()) {
			// извлекаем узел с наименьшим значением F
			Node current = openSet.poll();
			
			// если текущий узел конечный, завершаем работу
			if (current.getX() == bx && current.getY() == by) {
				goal = current;
				return true;
			}
			
			closeSet.add(current);
			
			// по всем соседям
			for (int t = 0; t < 4; t++) {
				
				// абсолютные координаты соседа
				int neigx = current.getX() + dx[t];
				int neigy = current.getY() + dy[t];
				
				// если сосед не выходит за границы
				if (neigx >= 0 && neigx < lab[0].length && neigy >= 0 && neigy < lab.length) {
					
					Node neig = lab[neigy][neigx];

					//если сосед находится в закрытом списке => переходим к другому соседу
					if (closeSet.contains(neig))
						continue;
					
					// вычисляем g(x) для обрабатываемого соседа
					int tentative_g_score = current.getG() + v;
					
					// если соседа нет в openSet => добавляем его
					
					if (!openSet.contains(neig)) {
						neig.setG(tentative_g_score);
						neig.setH(heuristic_cost_estimate(neig.getX(), neig.getY()));
						neig.setF(neig.getH() + neig.getG());
						neig.setParent(current);
						openSet.offer(neig);
					}
					
					// идет дублирование кода, поскольку в качестве openSet используется очередь с приоритетами
					// и изменять значения полей объекта после добавления объекта в очередь нельзя
					
					// если сосед есть в openSet, но к нему через текущий узел можно добраться
					// за меньшее G => обновляем очередь
					
					else {
						if (tentative_g_score < neig.getG()) {
							openSet.remove(neig);
							neig.setG(tentative_g_score);
							neig.setF(neig.getH() + neig.getG());
							neig.setParent(current);
							openSet.offer(neig);
						}
					}
				
				}
			}
		}
		return false;
	}
	
	/**
	 * 1 этап - Инициализация карты узлов
	 */
	private void initialization(char[][] map) {
		
		lab = new Node[map.length][map[0].length];
		
		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[0].length; x++) {
				
				lab[y][x] = new Node(x,y);
				
				switch(map[y][x]) {
				
					case Constants.WALL:
						closeSet.add(lab[y][x]);
						break;
						
					case Constants.START:
						ax = x;
						ay = y;
						break;
					case Constants.FINISH:
						bx = x;
						by = y;
						break;
				}
			}
	}
	
	/**
	 * Этап 3 - восстановление пути
	 */
	
	private void reconstruct_path(char[][] map) {
		Node current = goal;
		
		while(current.getParent() != null) {
			current = current.getParent();
			map[current.getY()][current.getX()] = Constants.PATH;
		}
		
		map[ay][ax] = Constants.START;	
	}
	
	@Override
	public char[][] searchRoute(char[][] map) {
		
		initialization(map);
		
		if (map[by][bx] == Constants.WALL || map[ay][ax] == Constants.WALL)
			return null;
		
		boolean flag = algorithm();
		
		if (flag){
			char[][] tmp = new char[map.length][];
			for (int i = 0; i < map.length; i++) {
				tmp[i] = map[i].clone();
			}
			reconstruct_path(tmp);
			return tmp;
		}
		return null;
	}
}
