package algorithms;

import java.util.*;

import Classs.*;
import Interface.*;

public class DepthFirstSearch<T> extends CommonSearcher<T>{
	protected void newSearch()
	{
		openList=new Stack<State<T>>();
		setEvaluatedNodes(0);
	}
	protected boolean addToOpenList(State<T> initialState) {

		((Stack<State<T>>) openList).push(initialState);
		return true;
		
	}
	protected State<T> popOpenList() {
		setEvaluatedNodes(getEvaluatedNodes() + 1);
		State<T> state=((Stack<State<T>>) openList).pop();
		return state;
	}

	@Override
	public Solution<T> Search(Searchable<T> s) {
		newSearch();
		addToOpenList(s.getInitialState());
		Set<State<T>> closedSet = new HashSet<State<T>>();
		while (!openList.isEmpty()) {
			State<T> n = popOpenList();// dequeue
			closedSet.add(n);
			if (s.IsGoalState(n)) {
				try {
					return backTrace(n, s.getInitialState());
				} catch (Exception e) {
				}
			}
			// private method, back traces through the parents
			Collection<State<T>> successors = s.getAllPossibleStates(n);//however it is implemented
			for (State<T> state : successors) {
				if (!closedSet.contains(state)) {
					if (!openList.contains(state)) {
						addToOpenList(state);
					}
				}
			}
		}
		return null;
	}
}
