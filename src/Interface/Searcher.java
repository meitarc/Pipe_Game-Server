package Interface;

import Classs.Solution;

public interface Searcher<T>{
		// the search method
		public Solution<T> Search(Searchable<T> s);
		// get how many nodes were evaluated by the algorithm
		public int getNumberOfNodesEvaluated();
}
