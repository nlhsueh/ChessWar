package ChessGame;

import java.awt.Point;
import java.io.Serializable;

/**
 * 創建一個虛擬的棋子座標軸對應實際畫面的位置
 */
public class ChessMap implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int D = 25; /* 移動允許的誤差值 */
	private int x;
	private int y;
	private Point[][] map;

	public ChessMap(Point first, int x, int y, int dx, int dy) {
		this.x = x;
		this.y = y;
		makeMap(first, x, y, dx, dy);
	}

	private void makeMap(Point first, int x, int y, int dx, int dy) {
		map = new Point[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				map[i][j] = new Point(first.x + dx * i, first.y + dy * j);
				// System.out.println(map[i][j].x + " " + map[i][j].y);
			}
		}
	}
	
	/**
	 * 取得實際棋子在畫面的位置
	 * @param x 棋盤座標x
	 * @param y 棋盤座標y
	 * @return Point
	 */
	public Point getChessLoc(int x, int y) {
		if ((x >= 0) && (x < this.x) && (y >= 0) && (y < this.y)) {
			return new Point(map[x][y]);
		}
		return null;
	}

	/**
	 * 取得實際棋子在畫面的位置
	 * @param p 棋盤座標Point
	 * @return Point
	 */
	public Point getChessLoc(Point p) {
		if ((p.x >= 0) && (p.x < this.x) && (p.y >= 0) && (p.y < this.y)) {
			return new Point(map[p.x][p.y]);
		}
		return null;
	}
	
	/**
	 * 找尋距離最近棋子能下的點
	 * @param c 欲進行尋找的棋子
	 * @return Point 回傳棋子最近能下得點
	 */
	public Point findNearChessLoc(Chess c) {

		for (int i = 0; i < this.x; i++) {

			if (Math.abs(c.getX() - map[i][0].x) < D) {

				for (int j = 0; j < this.y; j++) {

					if (Math.abs(c.getY() - map[i][j].y) < D) {

						return new Point(i, j);

					}

				}

			}

		}
		return null; /* Not found */
	}

}
