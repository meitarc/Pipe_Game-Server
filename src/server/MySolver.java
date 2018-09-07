package server;

import Classs.Solution;
import Interface.Searchable;
import Interface.Searcher;
import adapter.SearcherAdapter;

public class MySolver<T> implements Solver<T> {
	
	private Solver<T> solver;
	
	public MySolver(Searcher<T> searcher) {
		this.solver = new SearcherAdapter<>(searcher);
	}

	@Override
	public Solution<T> solve(Searchable<T> s) {
		return solver.solve(s);
	}

}
