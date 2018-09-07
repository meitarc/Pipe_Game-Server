package algorithms;


import java.util.*;

import Classs.*;

public class BFS<T> extends CommonSearcher<T>{
	public BFS() {
		newSearch();
	}
	
	protected void newSearch()
	{
		openList=new LinkedList<State<T>>();
		setEvaluatedNodes(0);
	}
	protected boolean addToOpenList(State<T> initialState) {
		return openList.add(initialState);
		
	}
	protected State<T> popOpenList() {
		setEvaluatedNodes(getEvaluatedNodes() + 1);
		State<T> state=((LinkedList<State<T>>) openList).poll();
		return state;
	}
}
