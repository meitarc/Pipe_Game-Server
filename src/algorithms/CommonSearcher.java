package algorithms;

import java.util.*;
import java.util.function.Predicate;

import Classs.*;
import Interface.*;

public abstract class CommonSearcher<T> implements Searcher<T>{
	protected Collection<State<T>> openList;
	private int evaluatedNodes;
	public CommonSearcher() {
		newSearch();
	}
	
	protected abstract void newSearch();
	
	protected abstract boolean addToOpenList(State<T> initialState);
	protected abstract State<T> popOpenList();
	
	protected int getEvaluatedNodes() {
		return evaluatedNodes;
	}
	protected void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}
	@Override
	public int getNumberOfNodesEvaluated() {
		return getEvaluatedNodes();
	}


	protected Solution<T> backTrace(State<T> goalState, State<T> startState) throws Exception {
		ArrayList<State<T>> arrayList=new ArrayList<>();
		arrayList.add(goalState);
		///Maybe change arrayList.get(0)!=null to arrayList.get(0).getCameFrom()!=null
		//Or even change arrayList.get(0)!=startState   ~~~
		while(arrayList.get(0)!=null&&!arrayList.get(0).equals(startState)&&arrayList.get(0).getCameFrom()!=null)
		{
			arrayList.add(0,arrayList.get(0).getCameFrom());
		}
		if(arrayList.isEmpty())
		{
			throw new Exception("backTrace is empty");
		}
		return new Solution<T>(arrayList);
	}
	
	@Override
	public Solution<T> Search(Searchable<T> s) {
		newSearch();
		addToOpenList(s.getInitialState());
		Set<State<T>> closedSet=new HashSet<State<T>>();
		while(!openList.isEmpty()){
			State<T> n=popOpenList();// dequeue
			closedSet.add(n);
			if(s.IsGoalState(n))
			{
				try {
					return backTrace(n, s.getInitialState());
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
			// private method, back traces through the parents
			Collection<State<T>> successors=s.getAllPossibleStates(n);//however it is implemented
			for(State<T> state: successors){
				if(!closedSet.contains(state))
				{
					if(!openList.contains(state)){
						addToOpenList(state);
					}
					else 
					{
						if(openList.removeIf(new Predicate<State<T>>() 
						{
							@Override
							public boolean test(State<T> t) {
								return t.equals(state)&&t.getCost()>state.getCost();
							}
						}))
						{
							addToOpenList(state);
						}
					}
				}
				
			}
		}
		return null;
	}
}
