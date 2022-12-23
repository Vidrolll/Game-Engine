package com.tb2dge.main.math.noise;

import java.util.ArrayList;
import java.util.Random;

import com.tb2dge.main.util.ArrayUtils;
import com.tb2dge.main.util.json.JSONFile;
import com.tb2dge.main.util.json.JSONObject;

public class WFCGenerator {
	JSONFile file;
	JSONObject[] tiles;
	
	int[][] wfcTable;
	int[][][] possibleChoices;
	
	int startX,startY;
	boolean[] createdRows;
	
	Random r;
	
	public WFCGenerator(JSONFile file, long seed) {
		tiles = file.getObjects();
		this.file = file;
		r = new Random(seed);
	}
	public WFCGenerator(JSONFile file) {
		this(file,0);
	}
	
	public int[][][] getPossibleChoices() {
		return possibleChoices;
	}
	
	public int[][] generateWFC(int width, int height) {
		try {
			wfcTable = new int[width][height];
			possibleChoices = new int[width][height][tiles.length];
			createdRows = new boolean[height];
			setupChoices();
			int startingX = r.nextInt(width);
			int startingY = r.nextInt(height);
			startX = startingX;
			startY = startingY;
			wfcTable[startingX][startingY] = possibleChoices
					[startingX][startingY][randomTile(startingX,startingY)];
			updateNeighbors(startingX,startingY);
			fractalRight(startingX,startingY);
			createdRows = new boolean[height];
			fractalLeft(startingX,startingY);
			wfcTable[startingX][startingY] = -1;
			return wfcTable;
		} catch(Exception e) {
			Random random = new Random();
			return new WFCGenerator(file,random.nextLong()).generateWFC(width,height);
		}
	}
	
	public void fractalRight(int x, int y) {
		try {
			updateSurroundingNeighbors(x,y);
			if(inside(x+1,y)) {
				wfcTable[x+1][y] = possibleChoices
						[x+1][y][randomTile(x+1,y)];
				updateNeighbors(x+1,y);
				fractalRight(x+1,y);
			} else {
				createdRows[y] = true;
				if(inside(startX,y+1)&&!createdRows[y+1])fractalRight(startX,y+1);
				if(inside(startX,y-1)&&!createdRows[y-1])fractalRight(startX,y-1);
			}
		} catch(StackOverflowError e) {
			fractalRight(x,y);
		}
	}
	public void fractalLeft(int x, int y) {
		try {
			updateSurroundingNeighbors(x,y);
			if(inside(x,y)) {
				if(x==startX&&y==startY) fractalLeft(x-1,y);
				else {
					wfcTable[x][y] = possibleChoices
							[x][y][randomTile(x,y)];
					updateNeighbors(x,y);
					fractalLeft(x-1,y);
				}
			} else {
				createdRows[y] = true;
				if(inside(startX,y+1)&&!createdRows[y+1])fractalLeft(startX,y+1);
				if(inside(startX,y-1)&&!createdRows[y-1])fractalLeft(startX,y-1);
			}
		} catch(StackOverflowError e) {
			fractalLeft(x,y);
		}
	}
	
	public void updateSurroundingNeighbors(int x, int y) {
		if(inside(x+1,y)&&wfcTable[x+1][y]!=0) updateNeighbors(x+1,y);
		if(inside(x-1,y)&&wfcTable[x-1][y]!=0) updateNeighbors(x-1,y);
		if(inside(x,y+1)&&wfcTable[x][y+1]!=0) updateNeighbors(x,y+1);
		if(inside(x,y-1)&&wfcTable[x][y-1]!=0) updateNeighbors(x,y-1);
	}
	
	public void setupChoices() {
		for(int x = 0; x < possibleChoices.length; x++) {
			for(int y = 0; y < possibleChoices[0].length; y++) {
				for(int i = 0; i < possibleChoices[0][0].length; i++) {
					possibleChoices[x][y][i] = Integer.parseInt(tiles[i].get("id"));
				}
			}
		}
	}
	
	public int randomTile(int x, int y) {
		if(possibleChoices[x][y].length==0) return 0;
		return r.nextInt(possibleChoices[x][y].length);
	}
	
	public JSONObject getObject(int id) {
		for(JSONObject obj : tiles) {
			if(Integer.parseInt(obj.get("id"))==id)
				return obj;
		}
		return null;
	}
	
	public void updateNeighbors(int x, int y) {
		int tile = wfcTable[x][y];
		JSONObject tileObject = getObject(tile);
		int[] possibleNeighborsUp = ArrayUtils.parseInt(
				tileObject.getArray("up_neighbors").getData());
		int[] possibleNeighborsDown = ArrayUtils.parseInt(
				tileObject.getArray("down_neighbors").getData());
		int[] possibleNeighborsLeft = ArrayUtils.parseInt(
				tileObject.getArray("left_neighbors").getData());
		int[] possibleNeighborsRight = ArrayUtils.parseInt(
				tileObject.getArray("right_neighbors").getData());
		ArrayList<Integer> possible = new ArrayList<Integer>(); 
		if(inside(x,y+1)) {
			for(int i = 0; i < possibleChoices[x][y+1].length; i++) {
				if(ArrayUtils.indexOf(possibleNeighborsDown, possibleChoices[x][y+1][i])!=-1)
					possible.add(possibleChoices[x][y+1][i]);
			}
			possibleChoices[x][y+1] = ArrayUtils.parseInt(possible);
			if(possibleChoices[x][y+1].length==1&&wfcTable[x][y+1]==0) {
				wfcTable[x][y+1] = possibleChoices[x][y+1][0];
				updateNeighbors(x,y+1);
			}
		}
		possible.clear();
		if(inside(x,y-1)) {
			for(int i = 0; i < possibleChoices[x][y-1].length; i++) {
				if(ArrayUtils.indexOf(possibleNeighborsUp, possibleChoices[x][y-1][i])!=-1)
					possible.add(possibleChoices[x][y-1][i]);
			}
			possibleChoices[x][y-1] = ArrayUtils.parseInt(possible);
			if(possibleChoices[x][y-1].length==1&&wfcTable[x][y-1]==0) {
				wfcTable[x][y-1] = possibleChoices[x][y-1][0];
				updateNeighbors(x,y-1);
			}
		}
		possible.clear();
		if(inside(x+1,y)) {
			for(int i = 0; i < possibleChoices[x+1][y].length; i++) {
				if(ArrayUtils.indexOf(possibleNeighborsRight, possibleChoices[x+1][y][i])!=-1)
					possible.add(possibleChoices[x+1][y][i]);
			}
			possibleChoices[x+1][y] = ArrayUtils.parseInt(possible);
			if(possibleChoices[x+1][y].length==1&&wfcTable[x+1][y]==0) {
				wfcTable[x+1][y] = possibleChoices[x+1][y][0];
				updateNeighbors(x+1,y);
			}
		}
		possible.clear();
		if(inside(x-1,y)) {
			for(int i = 0; i < possibleChoices[x-1][y].length; i++) {
				if(ArrayUtils.indexOf(possibleNeighborsLeft, possibleChoices[x-1][y][i])!=-1)
					possible.add(possibleChoices[x-1][y][i]);
			}
			possibleChoices[x-1][y] = ArrayUtils.parseInt(possible);
			if(possibleChoices[x-1][y].length==1&&wfcTable[x-1][y]==0) {
				wfcTable[x-1][y] = possibleChoices[x-1][y][0];
				updateNeighbors(x-1,y);
			}
		}
	}
	public void printTable() {
		for(int y = 0; y < wfcTable[0].length; y++) {
			System.out.print(y+": ");
			for(int x = 0; x < wfcTable.length; x++) {
				System.out.print(wfcTable[x][y]+",");
			}
			System.out.println();
		}
	}
	public boolean inside(int x, int y) {
		return (x > -1 && x < wfcTable.length &&
				y > -1 && y < wfcTable[0].length);
	}
}