package com.mycompany.algorithm;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * <h3>Реализация алгоритма поиска кратчайшего пути на графе - A* (A-Star).</h3>
 * 
 * Используется окрестность Фон-Неймана клетки с манхэттеновской 
 * эвристической функцией оценки расстояния до цели
 * 
 * <h4>Требования:</h4>JVM version 6+
 * 
 * <h4>Примечание:</h4> в методе {@link AStar#searchRoute(char[][])} считается, что входные данные неизменны,
 * поэтому для массива результата создается копия всего массива map. 
 * 
 * @author Martynov Roman 2018.
 */

public class AStar implements Navigator{

	/**
	 * Узел графа
	 */
	private static class Node {

		// координаты
		int x;
		int y;

		// значения функций
		int g;
		int h;
		int f;

		// родительский узел
		Node parent;

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

			if (node.x == x && node.y == y)
				return true;
			else
				return false;
		}

		@Override
		public int hashCode() {
			int result = 1;
			int prime = 31;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
	}

	// карта узлов размерностью HxW

	private Node[][] lab;

	// openSet - коллекция узлов, у которых заполнены поля и которые могут быть
	// нам интересны в дальейшем; соседи не внесены в openSet
	// poll будет извлекать узел с наименьшим значением функции f;
	// offer за O(logn), contains - O(n), poll - O(1)

	private Queue<Node> openSet = new PriorityQueue<Node>(new Comparator<Node>() {

		@Override
		public int compare(Node one, Node two) {
			if (one.f < two.f)
				return -1;
			else if (one.f > two.f)
				return 1;
			return 0;
		}
	});

	// closedSet - коллекция узлов, которых мы исключили из дальнейшего просмотра;
	// все соседи внесены в OpenSet
	// add, contains за O(1)

	private Set<Node> closeSet = new HashSet<Node>();

	// смещение для соседних ячеек (правая, нижняя, левая, левая ячейки)
	private int dx[] = { 1, 0, -1, 0 };
	private int dy[] = { 0, -1, 0, 1 };

	// стоимость перехода в соседнюю ячейку
	private int v = 1;

	// координаты (x,y) стартового узла; координаты (x,y) конечного узла
	private int ax, ay, bx, by;

	// конечный узел (по нему будет восстанавливаться путь)
	private Node goal;

	/**
	 * Рассчет эвристической функции, дающей оценку расстояния от текущего узла до
	 * конечного
	 * 
	 * @param cx координата x текущего узла
	 * @param cy координата y текущего узла
	 */
	private int heuristic_cost_estimate(int cx, int cy) {

		return Math.abs(cx - bx) + Math.abs(cy - by);
	}

	/**
	 * 1 этап - Инициализация карты узлов lab
	 * 
	 * @param map карта города Мачу-Пикчу :)
	 */
	private void initialization(char[][] map) {

		lab = new Node[map.length][map[0].length];

		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[0].length; x++) {

				lab[y][x] = new Node(x, y);

				switch (map[y][x]) {

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
	 * 2 этап - алгоритм A*
	 * 
	 * @return true, если путь найден (+ устанавливается поле goal), false - не найден;
	 */
	private boolean algorithm() {

		Node start = lab[ay][ax];

		// инициализируем стартовый узел
		start.g = 0;
		start.h = heuristic_cost_estimate(ax, ay);
		start.f = start.h;

		openSet.offer(start);

		while (!openSet.isEmpty()) {
			// извлекаем узел с наименьшим значением F
			Node current = openSet.poll();

			// если текущий узел конечный, завершаем работу
			if (current.x == bx && current.y == by) {
				goal = current;
				return true;
			}

			closeSet.add(current);

			// по всем соседям
			for (int t = 0; t < 4; t++) {

				// абсолютные координаты соседа
				int neigx = current.x + dx[t];
				int neigy = current.y + dy[t];

				// если сосед не выходит за границы
				if (neigx >= 0 && neigx < lab[0].length && neigy >= 0 && neigy < lab.length) {

					Node neig = lab[neigy][neigx];

					// если сосед находится в закрытом списке => переходим к другому соседу
					if (closeSet.contains(neig))
						continue;

					// вычисляем g(x) для обрабатываемого соседа
					int tentative_g_score = current.g + v;

					// если соседа нет в openSet => добавляем его
					if (!openSet.contains(neig)) {
						neig.g = tentative_g_score;
						neig.h = heuristic_cost_estimate(neig.x, neig.y);
						neig.f = neig.g + neig.h;
						neig.parent = current;
						openSet.offer(neig);
					}
					else {
						if (tentative_g_score < neig.g) {
							// очередь с приоритетами не пересортировывает элементы
							openSet.remove(neig);
							neig.g = tentative_g_score;
							neig.f = neig.h + neig.g;
							neig.parent = current;
							openSet.offer(neig);
						}
					}

				}
			}
		}
		return false;
	}
	
	/**
	 * Этап 3 - восстановление пути
	 */
	private void reconstruct_path(char[][] map) {
		Node current = goal;
		
		while(current.parent != null) {
			current = current.parent;
			map[current.y][current.x] = Constants.PATH;
		}
		map[ay][ax] = Constants.START;	
	}
	
	/**
	 * Реализация метода интерфейса навигиатора в Мачу-Пикчу :)
	 * 
	 * @exception RuntimeException входная карта не удовлетворяет ограничениям
	 */
	@Override
	public char[][] searchRoute(char[][] map) { 
		
		if (map.length < 1 || map[0].length < 1 || map.length > 10_000 || map[0].length > 10_000)
			throw new RuntimeException("Не выполнено 1<=M,N<=10000");
		
		initialization(map);
		
		if (map[by][bx] == Constants.WALL || map[ay][ax] == Constants.WALL)
			return null;
		
		boolean flag = algorithm();
		
		// если путь найден
		if (flag){
			// создаем копию входного массива, чтобы он был unmodifiable
			char[][] tmp = new char[map.length][];
			for (int i = 0; i < map.length; i++) {
				tmp[i] = map[i].clone();
			}
			// отмечаем плюсиками путь
			reconstruct_path(tmp);
			return tmp;
		}
		return null;
	}
	
}
