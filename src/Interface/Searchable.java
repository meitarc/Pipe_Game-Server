package Interface;

import java.util.Collection;

import Classs.State;

public interface Searchable<T> {

	public State<T> getInitialState();
	//public State<T> getGoalState();
	public Collection<State<T>> getAllPossibleStates(State<T> s);
	public Boolean IsGoalState(State<T> s);
}
