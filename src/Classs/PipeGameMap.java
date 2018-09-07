package Classs;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import Classs.Tile.Direction;

public class PipeGameMap implements Serializable {
	private static final long serialVersionUID = 1L;
	private Tile[][] map;
	private int rows;
	private int columns;
	
	public PipeGameMap() {
		super();
	}

	public PipeGameMap(Tile[][] map) {
		setMap(map);
	}
	
	
	public PipeGameMap(String map) {
		setMap(map);
	}
	public Tile[][] getMap() {
		return map;
	}
	public void setMap(Tile[][] map) {
		this.map = map;
		this.rows=map.length; // returns the number of rows
		this.columns=map[0].length; // returns the number of columns
	}
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}	
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}


	@Override
	public String toString() {
		String st="";
		for(int i=0;i<this.rows;i++)
		{
			for(int j=0;j<this.columns;j++)
			{
				try {
					st+=this.getTile(i, j).toString();
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
			st+="\n";
		}
		return st;
	}

	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}


	public void setMap(String stmap)
	{

		if(stmap==null||stmap.isEmpty()||stmap=="")
		{
			this.rows=0;
			this.columns=0;
			this.map=new Tile[this.rows][this.columns];
		}
		else
		{
			String[] strmap=stmap.split("\n");
			this.rows=strmap.length;
			char[][] alstrmap=new char[this.rows][];
			for(int i=0;i<this.rows;i++)
			{
				//System.out.println(strmap[i]);
				alstrmap[i]=strmap[i].replaceAll("[\n\r]", "").toCharArray();
			}
			
			this.columns=alstrmap[0].length;
			//System.out.println("Rows:"+this.rows);
			//System.out.println("Columns:"+this.columns)
			this.map=new Tile[this.rows][this.columns];
			for(int row=0;row<this.rows;row++)
			{
				for(int column=0;column<this.columns;column++)
				{
					this.map[row][column]=new Tile(alstrmap[row][column],row,column);
				}
			}
		}
	}
	
	public Tile rotateTile(Tile tile) throws Exception
	{
		return rotateTile(tile.getRow(),tile.getColumn());
	}
	public Tile rotateTile(int row,int column) throws Exception
	{
		return this.getTile(row, column).rotate();
	}

	public boolean isSolved()
	{
		Collection<Tile> arrayListgoaltills=getgoalsindex();
		boolean flag;
		for(Tile goal:arrayListgoaltills)
		{
			flag=isTilesConnected(goal, new Predicate<Tile>() {
				
				@Override
				public boolean test(Tile t) {
					return t.isStart();
				}
			});
			
			if(!flag)
			{
				//System.out.println(")-:");
				return false;
			}			
		}
		//System.out.println("(-:");
		return true;
	}
	
	
	public boolean isTileNeighborIsBlank(Tile tile)
	{
		Collection<Direction> directions=tile.getDirections();
		int row=tile.getRow();
		int col=tile.getColumn();
		Tile tile2=null;
		for(Direction d:directions)
		{
			try {
					tile2=null;
					switch (d) {
						case up:
						{
							tile2=(this.getTile(row-1, col));
							break;
						}
						case down:
						{
							tile2=(this.getTile(row+1, col));
							break;
						}
						case left:
						{
							tile2=(this.getTile(row, col-1));
							break;
						}
						case right:
						{
							tile2=(this.getTile(row, col+1));
							break;
						}
						default:
						{
							//tile2=null;
							break;
						}
					}
					if(tile2.isBlank())
					{
						return true;
					}
				}catch (Exception e) {
					//e.printStackTrace();
			}
		}
		return false;
	}
	
	public Collection<Tile> getNeighbors(final Tile tile)
	{
			Collection<Tile> arrayList=new ArrayList<>();
			int row=tile.getRow();
			int col=tile.getColumn();
			Tile tile2=null;
			Collection<Direction> directions=tile.getDirections();
			for(Direction d:directions)
			{
				try {
					tile2=null;
					switch (d) {
						case up:
						{
							tile2=(this.getTile(row-1, col));
							break;
						}
						case down:
						{
							tile2=(this.getTile(row+1, col));
							break;
						}
						case left:
						{
							tile2=(this.getTile(row, col-1));
							break;
						}
						case right:
						{
							tile2=(this.getTile(row, col+1));
							break;
						}
						default:
						{
							//tile2=null;
							break;
						}
					}
					if(tile2!=null)
					{
						if(isTilesNeighbors(tile,tile2))
						{
							arrayList.add(tile2);
						}
					}
				}catch (Exception e) {
					//e.printStackTrace();
			}
		}
		
		return arrayList;
	}
	
	public Collection<Tile> getstartsindex()
	{
		return getTilesindex(new Predicate<Tile>() {

			@Override
			public boolean test(Tile t) {
				return t.isStart();
			}
		});
	}
	
	public Collection<Tile> getgoalsindex()
	{
		return getTilesindex(new Predicate<Tile>() {

			@Override
			public boolean test(Tile t) {
				return t.isGoal();
			}
		});
	}
	public Collection<Tile> getTilesindex(Predicate<Tile> predicate)
	{
		Collection<Tile> arrayListgoals=new ArrayList<>();
		for(int row=0;row<this.rows;row++)
		{
			for(int column=0;column<this.columns;column++)
			{
				try {
					if(predicate.test(this.getTile(row, column)))
					{
						arrayListgoals.add(this.getTile(row, column));
					}
				} catch (Exception e) {

					//e.printStackTrace();
				}
			}
		}
		return arrayListgoals;
	}
	
	
	public PipeGameMap hashMap() {
		PipeGameMap gameMap=clone();
		for(int i=0;i<this.rows;i++)
		{
			for(int j=0;j<this.columns;j++)
			{
				//System.out.println("i,j");
				try {
					gameMap.getTile(i, j).setCh(gameMap.getTile(i, j).hashTile().getCh());
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
		return gameMap;
	}
	
	public Tile getTile(final int row,final int col) throws Exception
	{
		try {
			return this.map[row][col];
		}
		catch (Exception e) {
			throw new Exception("Out of bound");
		}
	}
	
	public boolean isTilesConnectedToStart(Tile t1)
	{
		return isTilesConnected(t1,new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isStart();
			}
		});
	}
	public boolean isTilesConnectedToGoal(Tile t1)
	{
		return isTilesConnected(t1,new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isGoal();
			}
		});
	}
	
	public boolean isTilesConnected(Tile t1,Tile t2)
	{
		return isTilesConnected(t1,new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.equals(t2);
			}
		});
	}
	public boolean isTilesConnected(Tile t1,Predicate<Tile> predicate)
	{
		Queue<Tile> queue=new LinkedList<Tile>();
		HashSet<Tile> closedSet=new HashSet<Tile>();
		queue.add(t1);
		while(!queue.isEmpty())
		{
			Tile n=queue.remove();// dequeue
			closedSet.add(n);
			try {
				if(predicate.test(n))
				{
					return true;
				}
				Collection<Tile> successors=getNeighbors(n);
				for(Tile tile:successors) {
					if(tile!=null&&!closedSet.contains(tile))
					{
						if(!queue.contains(tile)){
							queue.add(tile);
						}
					}
				}
			}catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return false;
		
	}
	
	public boolean isTilesNeighbors(Tile t1,Tile t2)
	{
		try {
			int row1=t1.getRow();
			int col1=t1.getColumn();
			int row2=t2.getRow();
			int col2=t2.getColumn();
		if(row2==row1-1&&col2==col1)
		{
			if(t1.getDirections().contains(Direction.up)
					&&t2.getDirections().contains(Direction.down))
			{
				return true;
			}
		}
		else if(row1==row2)
		{
			if(col2==col1-1)
			{
				if(t1.getDirections().contains(Direction.left)
						&&t2.getDirections().contains(Direction.right))
				{
					return true;
				}
			}
			else if(col2==col1)
			{
				throw new Exception("Error get the same tiles");
			}
			else if(col1+1==col2)
			{
				if(t1.getDirections().contains(Direction.right)
						&&t2.getDirections().contains(Direction.left))
				{
					return true;
				}
			}
			else 
			{
				return false;
			}
		}
		else if(row1+1==row2&&col2==col1)
		{
			if(t1.getDirections().contains(Direction.down)
					&&t2.getDirections().contains(Direction.up))
			{
				return true;
			}
		}
		}catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean isTileLinkToOutOfBounds(Tile tile)
	{
		int row=tile.getRow();
		int col=tile.getColumn();
		Collection<Direction> directions=tile.getDirections();
		for(Direction d:directions)
		{
			try {
				switch (d) {
					case up:
					{
						this.getTile(row-1, col);
						break;
					}
					case down:
					{
						this.getTile(row+1, col);
						break;
					}
					case left:
					{
						this.getTile(row, col-1);
						break;
					}
					case right:
					{
						this.getTile(row, col+1);
						break;
					}
					default:
					{
						break;
					}
				}
				
			}
			catch (Exception e) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public PipeGameMap clone() {
		PipeGameMap gameMap=new PipeGameMap();
		gameMap.rows=this.rows;
		gameMap.columns=this.columns;
		gameMap.map=new Tile[rows][columns];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				try {
					gameMap.map[i][j]=new Tile(this.getTile(i, j).getCh(),i,j);
				}catch (Exception e) {}
			}
		}
		return gameMap;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PipeGameMap))
			return false;	
		if (obj == this)
			return true;
		return this.equals(((PipeGameMap) obj));
	}
	
	public boolean equals(PipeGameMap pipeGameMap) {
		return this.toString().equals(pipeGameMap.toString());
	}
	
	
	public int getAllPossibleExits(Tile tile)
	{
		int i=0;
		int row=tile.getRow();
		int col=tile.getColumn();
		Tile tile2=null;
		Direction[] directions= {Tile.Direction.up,Tile.Direction.down,Tile.Direction.right,Tile.Direction.left};
		for(Direction d:directions)
		{
			try {
				tile2=null;
				switch (d) {
					case up:
					{
						tile2=(this.getTile(row-1, col));
						break;
					}
					case down:
					{
						tile2=(this.getTile(row+1, col));
						break;
					}
					case left:
					{
						tile2=(this.getTile(row, col-1));
						break;
					}
					case right:
					{
						tile2=(this.getTile(row, col+1));
						break;
					}
					default:
					{
						//tile2=null;
						break;
					}
				}
				if(!tile2.isBlank())
				{
					i++;
				}
			}catch (Exception e) {
			}
		}
		return i;
	}
	
	
	private int countAllTiles(Predicate<Tile> predicate)
	{
		int count=0;
		for(int i=0;i<this.rows;i++)
		{
			for(int j=0;j<this.columns;j++)
			{
				try {
					if(predicate.test(getTile(i, j)))
					{
						count++;
					}
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}
		return count;
	}
	
	public int countAllStarts()
	{
		return countAllTiles(new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isStart();
			}
		});
	}
	public int countAllGoals()
	{
		return countAllTiles(new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isGoal();
			}
		});
	}
	public int countAllBlanks()
	{
		return countAllTiles(new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isBlank();
			}
		});
	}
	public int countAllStraightLines()
	{
		return countAllTiles(new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isStraightLine();
			}
		});
	}
	public int countAllRightAngles()
	{
		return countAllTiles(new Predicate<Tile>() {
			
			@Override
			public boolean test(Tile t) {
				return t.isRightAngle();
			}
		});
	}
	
	public int totalTiles()
	{
		return this.columns*this.rows;
	}
	
	
	public ArrayList<Tile> getAllPathsfromTile(Tile t)
	{
		Queue<Tile> queue=new LinkedList<Tile>();
		ArrayList<Tile> closedSet=new ArrayList<Tile>();
		queue.add(t);
		while(!queue.isEmpty())
		{
			Tile n=queue.remove();// dequeue
			closedSet.add(n);
			try {
				/*if(n.equals(t2))
				{
					return closedSet;
				}*/
				Collection<Tile> successors=getNeighbors(n);
				for(Tile tile:successors) {
					if(tile!=null&&!closedSet.contains(tile))
					{
						if(!queue.contains(tile)){
							queue.add(tile);
						}
					}
				}
			}catch (Exception e) {
				//e.printStackTrace();
			}
		}
		return closedSet;
	}
}