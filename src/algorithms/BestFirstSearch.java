package algorithms;

import java.util.*;
import Classs.*;

public class BestFirstSearch<T> extends CommonSearcher<T> {

	protected void newSearch()
	{
		openList=new PriorityQueue<State<T>>();
		setEvaluatedNodes(0);
	}
	
	protected boolean addToOpenList(State<T> initialState) {
		return openList.add(initialState);
		
	}
	protected State<T> popOpenList() {
		setEvaluatedNodes(getEvaluatedNodes() + 1);
		State<T> state=((PriorityQueue<State<T>>) openList).poll();
		return state;
	}	
}
