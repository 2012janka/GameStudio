package sk.tsystems.gamestudio.game.stones.core;

import java.io.Serializable;

public class Field implements Serializable {
	private final Tile[][] tiles;

	private final int rowCount;

	private final int columnCount;

	private GameState state = GameState.PLAYING;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new Tile[rowCount][columnCount];

		generate();
	}

	public GameState getState() {
		return state;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public int getNumberOfTiles() {
		return columnCount * rowCount - 1;
	}

	public void setGameState(GameState state) {
		if (isSolved()) {
			this.state = GameState.SOLVED;
		} else {
			this.state = state;
		}
	}

	private void generate() {
		int i = 1;

		for (int row = 0; row < this.getRowCount(); row++) {
			for (int column = 0; column < this.getColumnCount(); column++) {
				tiles[row][column] = new Tile(i);
				i++;

			}
		}

		for (int row = 0; row < this.getRowCount(); row++) {
			for (int column = 0; column < this.getColumnCount(); column++) {
				shuffleColumns(column);
				shuffleRows(row);
			}
		}
	}
	
//	private void generate2(){
//		int count=rowCount*columnCount;
//		List<Integer> values = new Arraylist<>(count);
//		for(int i=1;i<count;i++){
//			values.add(i);
//		}
//		values.add(EMPTY_CELL);
//		
//		Collections.shuffle(values);
//		
//		int index=0;
//		for (int m = 0; m < this.getRowCount(); m++) {
//			for (int n = 0; n < this.getColumnCount(); n++) {
//				tiles[m][n] = values.get(m*columnCount+n);
//				tiles[m][n] = values.get(index);
//				index++;
//			}
//			}
//	}

	private void shuffleColumns(int column) {
		int size = getRowCount();
		for (int i = 0; i < size; i++) {
			int s = i + (int) (Math.random() * (size - i));

			Tile temp = tiles[s][column];
			tiles[s][column] = tiles[i][column];
			tiles[i][column] = temp;
		}
	}

	private void shuffleRows(int row) {
		int size = getColumnCount();
		for (int i = 0; i < size; i++) {
			int s = (int) (Math.random() * (size - i)) + i;

			Tile temp = tiles[row][s];
			tiles[row][s] = tiles[row][i];
			tiles[row][i] = temp;
		}
	}

	public int[] getEmptyTile() {
		int[] indexes = new int[2];
		for (int row = 0; row < this.getRowCount(); row++) {
			for (int column = 0; column < this.getColumnCount(); column++) {
				if (tiles[row][column].getValue() == getNumberOfTiles() + 1) {
					indexes[0] = row;
					indexes[1] = column;
					return indexes;
				}
			}
		}
		throw new RuntimeException("Wrong field");
	}

	public boolean[] borders() {
		boolean border[] = new boolean[4]; // 0 up, 1 down, 2 left, 3 right
		if (getEmptyTile()[0] != rowCount - 1) {
			border[0] = true;
		}
		if (getEmptyTile()[0] != 0) {
			border[1] = true;
		}
		if (getEmptyTile()[1] != columnCount - 1) {
			border[2] = true;
		}
		if (getEmptyTile()[1] != 0) {
			border[3] = true;
		}
		return border;
	}

	private boolean isSolved() {
		int i = 1;
		for (int row = 0; row < this.getRowCount(); row++) {
			for (int column = 0; column < this.getColumnCount(); column++) {
				if (tiles[row][column].getValue() != i) {
					return false;
				}
				i++;
			}
		}
		return true;
	}
}
